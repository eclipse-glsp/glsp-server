package org.eclipse.glsp.server.command;

import org.eclipse.emf.common.command.CommandStack;

public interface CommandStackFactory {
    CommandStack createCommandStack();
}
