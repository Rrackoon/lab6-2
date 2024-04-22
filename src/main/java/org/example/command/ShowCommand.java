package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show", "вывести все элементы коллекции в строковом представлении");
    }
    public ShowCommand(IOProvider provider, CollectionManager collection, String[] parametersAdvices) {
        super("show", "вывести все элементы коллекции в строковом представлении", provider, collection, parametersAdvices);
    }

    @Override
    public  Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection)  {
       /* super.check(args.length);
        if (stacksize > 10000) {
            return new Response(new String[0]);
        }

        */
        //String[] response = {collectiondata.dragonsString()};
        String[] response = collection.getCollection().stream().map(dr -> dr.toString()).toArray(String[]::new);
        return new Response(response);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {

    }
}
