package org.eclipse.glsp.server.internal.command;

import com.google.inject.Inject;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.glsp.server.utils.CollaborationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultCommandStackManager implements CommandStackManager {

    @Inject
    CommandStackFactory factory;

    // subclientId, CommandStack
    protected Map<String, CommandStack> commandStackMap = new HashMap<>();

    @Override
    public CommandStack getOrCreateCommandStack(final String subclientId) {
        String subclientIdOrFallback = getSubclientIdOrFallback(subclientId);
        if (commandStackMap.containsKey(subclientIdOrFallback)) {
            return commandStackMap.get(getSubclientIdOrFallback(subclientIdOrFallback));
        }

        CommandStack commandStack = factory.createCommandStack();
        commandStackMap.put(subclientIdOrFallback, commandStack);
        return commandStack;
    }

    @Override
    public List<CommandStack> getAllCommandStack() {
        return new ArrayList<>(commandStackMap.values());
    }

    @Override
    public void setCommandStack(final CommandStack commandStack, final String subclientId) {
        String subclientIdOrFallback = getSubclientIdOrFallback(subclientId);
        if (commandStackMap.containsKey(subclientIdOrFallback)) {
            commandStackMap.get(subclientIdOrFallback).flush();
        }
        commandStackMap.put(subclientIdOrFallback, commandStack);
    }

    private String getSubclientIdOrFallback(final String subclientId) {
        if (subclientId != null) {
            return subclientId;
        }
        return CollaborationUtil.FALLBACK_SUBCLIENT_ID;
    }

}
