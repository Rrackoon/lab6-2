package org.example.command;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.utils.IOProvider;

import java.util.List;

public class PrintAscendingCommand extends Command {
    public PrintAscendingCommand() {
        super("print_ascending", "вывести все элементы коллекции по возрастанию");
    }
    public PrintAscendingCommand(IOProvider provider, CollectionManager collection, String[] parametersAdvices) {
        super("print_ascending", "вывести все элементы коллекции по возрастанию", provider, collection, parametersAdvices);
    }


    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        String[] response = collection.getCollection().stream().map(StudyGroup :: getStudentsCount).sorted().map(ch -> ch.toString()).toArray(String[]::new);
        return new Response(response);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}
}
