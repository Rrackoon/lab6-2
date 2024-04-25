package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand() {
        super("remove_by_id {id}", "удалить элемент из коллекции по его id");
    }


    @Override
    public void validateArgs(String[] args, int length) throws InvalidArgsException {
        try {
            super.validateArgs(args, length);
            long id = Long.parseLong(args[0]);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidArgsException();
        }
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
        collection.removeById(Integer.parseInt(args[1]));
        return new Response(new String[0]);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}
}
