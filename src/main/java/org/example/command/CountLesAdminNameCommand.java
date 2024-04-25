package org.example.command;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.utils.IOProvider;

public class CountLesAdminNameCommand extends Command {
    public CountLesAdminNameCommand() {
        super("count_less_than_group_admin {name}",
                "вывести количество элементов, значение поля groupAdmin которых меньше заданного");
    }


    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {


        String name = args[0]; // Получение имени
        long count = collection.getCollection().stream()
                .filter(d -> d.getGroupAdmin() != null && name.compareTo(d.getGroupAdmin().getName()) > 0)
                .count();

        String[] response = { "Количество элементов, значение поля groupAdmin которых меньше заданного (" + name + "): " + count };
        return new Response(response);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {
    }
}
