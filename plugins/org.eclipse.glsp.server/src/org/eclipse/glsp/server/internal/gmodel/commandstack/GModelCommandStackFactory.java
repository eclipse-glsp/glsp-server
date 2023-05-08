package org.eclipse.glsp.server.internal.gmodel.commandstack;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.glsp.server.command.CommandStackFactory;

public class GModelCommandStackFactory implements CommandStackFactory  {
    @Override
    public CommandStack createCommandStack() {
        return new GModelCommandStack();
    }
}
