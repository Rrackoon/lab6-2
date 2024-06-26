package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand extends Command {
    private final Map<String, Command> commands;

    public HelpCommand() {
        super("help", "вывести справку по командам");
        commands = new HashMap<String, Command>();
    }


    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        String[] response = {commandmanager.getCommands()};
        return new Response(response);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {

    }
}
