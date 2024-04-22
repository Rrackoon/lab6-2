package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.SGParser;
import org.example.utils.IOProvider;

public class AddCommand extends Command {
    public AddCommand() {
        super("add", "добавить новый элемент в коллекцию");
    }
    public AddCommand(IOProvider provider, CollectionManager collection,String[] parametersAdvices) {
        super("add", "добавить новый элемент в коллекцию", provider, collection,parametersAdvices);
    }

    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        collection.push(studyGroup);
        return new Response(new String[0]);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}

}
