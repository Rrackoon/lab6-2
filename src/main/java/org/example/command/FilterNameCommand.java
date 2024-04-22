package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;

import org.example.utils.IOProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FilterNameCommand extends Command {
    public FilterNameCommand() {
        super("filter_contains_name {name}",
                "вывести элементы, значение поля name которых cодержит заданную подстроку");
    }
    public FilterNameCommand(IOProvider provider, CollectionManager collection,String[] parametersAdvices) {
        super("filter_contains_name {name}",
                "вывести элементы, значение поля name которых cодержит заданную подстроку",
                provider, collection,parametersAdvices);
    }



    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        try {
            validateArgs(args, 1);
            String name = args[0];
            List<StudyGroup> studyGroups = collection.getCollection()
                    .stream()
                    .filter(studyGroup1 -> studyGroup1.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("Collection filtered by name:\n");
            String line = "-".repeat(60) + "\n";
            responseBuilder.append(line);
            for (StudyGroup group : studyGroups) {
                responseBuilder.append(group.toString()).append("\n").append(line);
            }
            String[] responseArray = {responseBuilder.toString()};
            return new Response(responseArray);
        }
        catch (InvalidArgsException e) {return new Response(new String[] {"error"});}
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {

    }
}
