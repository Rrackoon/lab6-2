package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.interfaces.Printer;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.ScriptReader;
import org.example.models.StudyGroup;
import org.example.parser.CommandParser;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private final int recDepth;
    public ExecuteScriptCommand() {
        super("execute_script {file_name}", "считать и исполнить скрипт из указанного файла");
        recDepth = 1;
    }


    public ExecuteScriptCommand(IOProvider provider, CollectionManager collection, int recDepth,String[] parametersAdvices) {
        super("execute_script {file_name}", "считать и исполнить скрипт из указанного файла", provider, collection,parametersAdvices);
        this.recDepth = recDepth;
    }

    @Override
    public Response execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection) {
        LinkedList<CommandShallow> commandsList = ScriptReader.readCommands(args[1], commandmanager);
        Response response = new Response(new String[0]);
        for (CommandShallow command : commandsList) {
            response.addLines(command.getCommand().execute(command.getArguments(), ++stacksize, command.getStudyGroup(), commandmanager, collection).getMessage());
        }
        return response;
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {}
}