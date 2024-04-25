package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.SGParser;
import org.example.utils.IOProvider;

public class AddIfMinCommand extends Command {
    public AddIfMinCommand() {
        super("add_if_max",
                "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
    }

    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        collection.push(studyGroup);
        return new Response(new String[0]);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}

}



