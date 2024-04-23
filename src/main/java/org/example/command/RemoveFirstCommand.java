package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

public class RemoveFirstCommand extends Command {
    public RemoveFirstCommand() {
        super("remove_first", "удалить первый элемент из коллекции");
    }
    public RemoveFirstCommand(IOProvider provider, CollectionManager collection,String[] parametersAdvices) {
        super("remove_first", "удалить первый элемент из коллекции", provider, collection,parametersAdvices);
    }

    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        try {
            Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            System.out.println("Error! Argument is not a number");
            return new Response(new String[0]);
        }
        collection.remove(Integer.parseInt(args[1]));
        return new Response(new String[0]);
    }
    @Override
    public void execute(String[] args) throws InvalidArgsException {

    }
}
