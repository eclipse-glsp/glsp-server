#!/usr/bin/env bash
#********************************************************************************
# Copyright (c) 2026 EclipseSource and others.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0 which is available at
# https://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the Eclipse
# Public License v. 2.0 are satisfied: GNU General Public License, version 2
# with the GNU Classpath Exception which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#********************************************************************************
#
# Builds and maintains the *nightly* p2 composite update site for GLSP.
#
# Replaces the former 'packaging-p2composite.ant' + 'rsync.ant' tasks, which
# required a full Eclipse runtime (tycho-eclipserun-plugin) just to rewrite two
# tiny composite metadata files. This script regenerates the composite metadata
# from a plain directory scan instead - no Eclipse/p2 tooling required.
#
# The nightly site is a two-level composite:
#
#   nightly/                          <- composite, children = <major.minor> dirs
#   ├── compositeContent.xml
#   ├── compositeArtifacts.xml
#   ├── p2.index
#   └── <major.minor>/                <- composite, children = <full.version> dirs
#       ├── compositeContent.xml
#       ├── compositeArtifacts.xml
#       ├── p2.index
#       └── <full.version>/           <- the actual (simple) p2 repository
#
# 'releases' and 'staging' live next to 'nightly' but are independent (non
# composite) update sites and are deliberately NOT touched by this script.
#
# Usage:
#   p2-composite.sh add   --unqualified-version <v> --build-qualifier <q> \
#                         --source-repo <dir> --local <p2dir> --remote <p2dir> \
#                         [--site-label <label>] [--dry-run]
#
#   p2-composite.sh prune --local <p2dir> --remote <p2dir> \
#                         [--site-label <label>] [--apply] <token> [<token>...]
#
# Where <token> is a 'major' (e.g. '2') or 'major.minor' (e.g. '2.7') version
# line. 'prune' removes the matching nightly version line(s) entirely. It is
# DRY-RUN by default and only mutates the remote when '--apply' is given.
#
# '--local'/'--remote' point at the 'server/p2' parent that contains 'nightly';
# the script operates exclusively on the 'nightly' subtree.

set -euo pipefail

readonly DEFAULT_SITE_LABEL="Eclipse GLSP Nightly Updatesite"
# feature whose jar in '<source-repo>/features' carries the Tycho build qualifier;
# used to auto-detect the version when --unqualified-version/--build-qualifier are
# omitted. Change this single constant if the lead feature is ever renamed.
readonly VERSION_FEATURE_ID="org.eclipse.glsp.feature"

log() { printf '%s\n' "$*" >&2; }
die() { printf 'error: %s\n' "$*" >&2; exit 1; }

usage() {
   cat >&2 <<'EOF'
Build and maintain the nightly p2 composite update site for GLSP.

Usage:
  p2-composite.sh add   --source-repo <dir> --local <p2dir> --remote <p2dir> \
                        [--unqualified-version <v>] [--build-qualifier <q>] \
                        [--site-label <label>] [--dry-run]

  p2-composite.sh prune --local <p2dir> --remote <p2dir> \
                        [--site-label <label>] [--apply] <token> [<token>...]

By default 'add' detects the version from the built feature jar in <source-repo>;
pass --unqualified-version/--build-qualifier only to override.
<token> is a 'major' (e.g. '2') or 'major.minor' (e.g. '2.7') version line.
'prune' is DRY-RUN by default; pass --apply to actually delete remotely.
'--local'/'--remote' point at the 'server/p2' parent that contains 'nightly'.
EOF
   exit "${1:-0}"
}

# -----------------------------------------------------------------------------
# Composite metadata generation
# -----------------------------------------------------------------------------

# Lists the immediate child *directories* of a composite dir (sorted, no dotfiles).
list_child_dirs() {
   local dir="$1" path base
   shopt -s nullglob
   for path in "$dir"/*/; do
      base=$(basename "$path")
      [[ $base == .* ]] && continue
      printf '%s\n' "$base"
   done
   shopt -u nullglob
   return 0
}

# Writes a single composite metadata file (content or artifacts).
_write_repo_file() {
   local file="$1" pi="$2" type="$3" name="$4" ts="$5" count="$6" children_xml="$7"
   {
      printf "%s\n" "<?xml version='1.0' encoding='UTF-8'?>"
      printf "%s\n" "<?${pi} version='1.0.0'?>"
      printf "%s\n" "<repository name='${name}' type='${type}' version='1.0.0'>"
      printf "%s\n" "  <properties size='2'>"
      printf "%s\n" "    <property name='p2.timestamp' value='${ts}'/>"
      printf "%s\n" "    <property name='p2.atomic.composite.loading' value='true'/>"
      printf "%s\n" "  </properties>"
      printf "%s\n" "  <children size='${count}'>"
      printf "%s" "$children_xml"
      printf "%s\n" "  </children>"
      printf "%s\n" "</repository>"
   } >"$file"
}

# Regenerates compositeContent.xml, compositeArtifacts.xml and p2.index for the
# given composite directory from whatever child directories currently exist.
regenerate_composite() {
   local dir="$1" name="$2"
   local -a children=()
   local c
   # read children into a sorted array
   while IFS= read -r c; do
      [[ -n $c ]] && children+=("$c")
   done < <(list_child_dirs "$dir" | sort)

   local count=${#children[@]}
   # epoch millis without GNU-only 'date +%N' (portable to macOS bash 3.2)
   local ts=$(( $(date +%s) * 1000 ))
   local children_xml=""
   for c in "${children[@]}"; do
      children_xml+="    <child location='${c}'/>"$'\n'
   done

   _write_repo_file "$dir/compositeContent.xml" compositeMetadataRepository \
      "org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" \
      "$name" "$ts" "$count" "$children_xml"
   _write_repo_file "$dir/compositeArtifacts.xml" compositeArtifactRepository \
      "org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" \
      "$name" "$ts" "$count" "$children_xml"
   printf '%s\n' \
      'version=1' \
      'metadata.repository.factory.order=compositeContent.xml,\!' \
      'artifact.repository.factory.order=compositeArtifacts.xml,\!' >"$dir/p2.index"

   log "  regenerated composite '${name}' (${count} children) at ${dir}"
}

# -----------------------------------------------------------------------------
# Rsync (mirror the nightly subtree down, mutate locally, push back up)
# -----------------------------------------------------------------------------

# Probes whether a (possibly remote) directory exists. Return codes:
#   0 = exists, 1 = confirmed missing, 2 = could not determine (e.g. ssh error)
remote_dir_exists() {
   local target="$1"
   if [[ $target == *:* ]]; then
      local host="${target%%:*}" path="${target#*:}" rc
      ssh "$host" "test -d '${path}'" && return 0
      rc=$?
      [[ $rc -eq 1 ]] && return 1 # remote shell ran, dir is absent
      return 2                    # ssh itself failed -> state unknown
   else
      [[ -d $target ]] && return 0 || return 1
   fi
}

rsync_pull() {
   mkdir -p "$LOCAL_NIGHTLY"
   # Only a *confirmed* missing remote counts as a first run. On any other
   # failure we must NOT continue: rsync_push runs with --delete, so pushing a
   # partial/empty mirror could wipe existing nightly versions from production.
   local rc
   if remote_dir_exists "$REMOTE_NIGHTLY"; then
      log "Pulling ${REMOTE_NIGHTLY}/ -> ${LOCAL_NIGHTLY}/"
      # a failure here aborts the run (set -e) rather than risking a destructive push
      rsync -azvc --exclude='.*' -e ssh "$REMOTE_NIGHTLY/" "$LOCAL_NIGHTLY/"
   else
      rc=$?
      [[ $rc -eq 1 ]] || die "cannot determine remote nightly state (${REMOTE_NIGHTLY}); aborting to avoid a destructive push"
      log "Remote nightly site does not exist yet (first run); starting from an empty mirror"
   fi
}

# Ensures the remote 'nightly' dir (incl. parents) exists; rsync only creates the
# final path component, so a fresh remote would otherwise fail on push.
ensure_remote_dir() {
   if [[ $REMOTE_NIGHTLY == *:* ]]; then
      local host="${REMOTE_NIGHTLY%%:*}" path="${REMOTE_NIGHTLY#*:}"
      ssh "$host" "mkdir -p '${path}'"
   else
      mkdir -p "$REMOTE_NIGHTLY"
   fi
}

rsync_push() {
   local -a opts=(-azvc --exclude='.*' -e ssh --delete)
   if [[ $DRY_RUN == true ]]; then
      opts+=(-n)
      log "DRY-RUN: previewing push ${LOCAL_NIGHTLY}/ -> ${REMOTE_NIGHTLY}/ (no changes)"
   else
      # only touch the remote for real runs; keep dry-run side-effect free
      ensure_remote_dir
      log "Pushing ${LOCAL_NIGHTLY}/ -> ${REMOTE_NIGHTLY}/"
   fi
   rsync "${opts[@]}" "$LOCAL_NIGHTLY/" "$REMOTE_NIGHTLY/"
}

# -----------------------------------------------------------------------------
# Subcommand: add
# -----------------------------------------------------------------------------

# Derives UNQUALIFIED_VERSION + BUILD_QUALIFIER from the lead feature jar that
# Tycho stamped into '<source-repo>/features'. Reading the version back from the
# actual build output guarantees the child dir name matches the published bundles.
detect_version() {
   local jar ver
   shopt -s nullglob
   for jar in "$SOURCE_REPO"/features/"${VERSION_FEATURE_ID}"_*.jar; do
      [[ $jar == *".source_"* ]] && continue
      ver=$(basename "$jar" .jar)
      ver="${ver#"${VERSION_FEATURE_ID}"_}" # -> <major>.<minor>.<micro>.<qualifier>
      UNQUALIFIED_VERSION="${ver%.*}"
      BUILD_QUALIFIER="${ver##*.}"
      break
   done
   shopt -u nullglob
   [[ -n $UNQUALIFIED_VERSION && -n $BUILD_QUALIFIER ]] \
      || die "cannot detect version: no '${VERSION_FEATURE_ID}_*.jar' in ${SOURCE_REPO}/features (pass --unqualified-version/--build-qualifier to override)"
   log "Detected version ${UNQUALIFIED_VERSION}.${BUILD_QUALIFIER} from build output"
}

cmd_add() {
   [[ -n $SOURCE_REPO ]] || die "--source-repo is required"
   [[ -d $SOURCE_REPO ]] || die "source repository not found: ${SOURCE_REPO}"
   [[ -n $(ls -A "$SOURCE_REPO") ]] || die "source repository is empty: ${SOURCE_REPO}"
   # auto-detect from build output unless both were passed explicitly
   [[ -n $UNQUALIFIED_VERSION && -n $BUILD_QUALIFIER ]] || detect_version

   local major_minor="${UNQUALIFIED_VERSION%.*}"
   local full_version="${UNQUALIFIED_VERSION}.${BUILD_QUALIFIER}"
   local minor_dir="$LOCAL_NIGHTLY/$major_minor"
   local child_dir="$minor_dir/$full_version"

   rsync_pull

   log "Adding child repository ${major_minor}/${full_version}"
   # clean copy so a re-run of the same qualifier never leaves stale artifacts
   rm -rf "$child_dir"
   mkdir -p "$child_dir"
   cp -R "$SOURCE_REPO/." "$child_dir/"

   regenerate_composite "$minor_dir" "${SITE_LABEL} ${major_minor}"
   regenerate_composite "$LOCAL_NIGHTLY" "${SITE_LABEL} All Versions"

   rsync_push
   log "Done."
}

# -----------------------------------------------------------------------------
# Subcommand: prune
# -----------------------------------------------------------------------------

# Resolves a token to the matching <major.minor> child dirs under nightly.
resolve_targets() {
   local token="$1" base
   while IFS= read -r base; do
      if [[ $token == *.* ]]; then
         [[ $base == "$token" ]] && printf '%s\n' "$base"
      else
         # a bare 'major' matches every '<major>.<minor>' line
         [[ $base == "$token".* ]] && printf '%s\n' "$base"
      fi
   done < <(list_child_dirs "$LOCAL_NIGHTLY")
   return 0
}

cmd_prune() {
   [[ ${#TOKENS[@]} -gt 0 ]] || die "prune requires at least one <major>|<major.minor> token"

   local t
   for t in "${TOKENS[@]}"; do
      [[ $t =~ ^[0-9]+(\.[0-9]+)?$ ]] || die "invalid version token: '${t}' (expected <major> or <major.minor>)"
   done

   rsync_pull

   # resolve tokens -> concrete target dirs (validated against what exists)
   local -a targets=()
   local d found
   for t in "${TOKENS[@]}"; do
      found=false
      while IFS= read -r d; do
         [[ -z $d ]] && continue
         targets+=("$d")
         found=true
      done < <(resolve_targets "$t")
      [[ $found == true ]] || die "no nightly version line matches token '${t}'"
   done
   # de-duplicate (overlapping tokens, e.g. '2' and '2.7')
   local -a unique_targets=()
   while IFS= read -r d; do
      [[ -n $d ]] && unique_targets+=("$d")
   done < <(printf '%s\n' "${targets[@]}" | sort -u)
   targets=("${unique_targets[@]}")

   # guard: never empty the whole nightly composite
   local -a all=()
   while IFS= read -r d; do [[ -n $d ]] && all+=("$d"); done < <(list_child_dirs "$LOCAL_NIGHTLY")
   if [[ ${#targets[@]} -ge ${#all[@]} ]]; then
      die "refusing to prune: that would remove ALL ${#all[@]} nightly version lines"
   fi

   log "The following nightly version lines will be removed:"
   for d in "${targets[@]}"; do
      log "  - ${d}"
   done

   for d in "${targets[@]}"; do
      rm -rf "${LOCAL_NIGHTLY:?}/${d}"
   done
   regenerate_composite "$LOCAL_NIGHTLY" "${SITE_LABEL} All Versions"

   if [[ $DRY_RUN == true ]]; then
      log "DRY-RUN: no changes pushed. Re-run with --apply to delete remotely."
   fi
   rsync_push
   log "Done."
}

# -----------------------------------------------------------------------------
# Argument parsing & dispatch
# -----------------------------------------------------------------------------

main() {
   [[ $# -ge 1 ]] || usage 1
   local command="$1"; shift

   SITE_LABEL="$DEFAULT_SITE_LABEL"
   UNQUALIFIED_VERSION=""
   BUILD_QUALIFIER=""
   SOURCE_REPO=""
   LOCAL_ROOT=""
   REMOTE_ROOT=""
   TOKENS=()
   # 'add' commits by default; 'prune' is dry-run unless --apply is given
   case "$command" in
      add) DRY_RUN=false ;;
      prune) DRY_RUN=true ;;
      -h | --help | help) usage 0 ;;
      *) die "unknown command: '${command}' (expected 'add' or 'prune')" ;;
   esac

   while [[ $# -gt 0 ]]; do
      case "$1" in
         --site-label) SITE_LABEL="$2"; shift 2 ;;
         --unqualified-version) UNQUALIFIED_VERSION="$2"; shift 2 ;;
         --build-qualifier) BUILD_QUALIFIER="$2"; shift 2 ;;
         --source-repo) SOURCE_REPO="$2"; shift 2 ;;
         --local) LOCAL_ROOT="$2"; shift 2 ;;
         --remote) REMOTE_ROOT="$2"; shift 2 ;;
         --dry-run) DRY_RUN=true; shift ;;
         --apply) DRY_RUN=false; shift ;;
         -h | --help) usage 0 ;;
         --) shift; while [[ $# -gt 0 ]]; do TOKENS+=("$1"); shift; done ;;
         -*) die "unknown option: ${1}" ;;
         *) TOKENS+=("$1"); shift ;;
      esac
   done

   [[ -n $LOCAL_ROOT ]] || die "--local is required"
   [[ -n $REMOTE_ROOT ]] || die "--remote is required"
   # operate strictly on the 'nightly' subtree of the given p2 roots
   LOCAL_NIGHTLY="${LOCAL_ROOT%/}/nightly"
   REMOTE_NIGHTLY="${REMOTE_ROOT%/}/nightly"

   case "$command" in
      add) cmd_add ;;
      prune) cmd_prune ;;
   esac
}

main "$@"
