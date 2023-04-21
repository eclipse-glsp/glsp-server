package org.eclipse.glsp.server.internal.command;

import org.eclipse.emf.common.command.CommandStack;

import java.util.List;

public interface CommandStackManager {
    CommandStack getOrCreateCommandStack(String subclientId);

    List<CommandStack> getAllCommandStack();

    void setCommandStack(CommandStack commandStack, String subclientId);
}
