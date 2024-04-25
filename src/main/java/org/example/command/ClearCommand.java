package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "очистить коллекцию");
    }


    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        collection.clear();
        return new Response(new String[0]);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}
}
