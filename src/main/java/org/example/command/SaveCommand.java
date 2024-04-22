package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
    }
    public SaveCommand(IOProvider provider, CollectionManager collection, String[] parametersAdvices) {
        super("save", "сохранить коллекцию в файл", provider, collection,parametersAdvices);
    }

    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        collection.save();
        return new Response(new String[0]);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}

}
