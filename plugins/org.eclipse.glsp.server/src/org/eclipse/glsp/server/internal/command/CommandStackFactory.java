package org.eclipse.glsp.server.internal.command;

import org.eclipse.emf.common.command.CommandStack;

public interface CommandStackFactory {
    CommandStack createCommandStack();
}
