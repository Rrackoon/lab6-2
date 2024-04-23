package org.example.server;

import com.google.gson.JsonParseException;
import org.example.CLIPrinter;
import org.example.command.*;
import org.example.exception.CommandIOException;
import org.example.exception.InputArgumentException;
import org.example.interfaces.Printer;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.parser.CommandParser;
import org.example.utils.IOProvider;
import org.example.server.core.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.logging.log4j.core.appender.rewrite.MapRewritePolicy.Mode.Add;

public class Main {
    public static void main(String[] args) throws InputArgumentException, IOException {
        final  String FILENAME = System.getenv("FILENAME");
        CollectionManager collection = CollectionManager.fromFile(FILENAME);
        Logger logger = Logger.getLogger(Main.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.setLevel(Level.ALL);handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        if (args.length != 0) {
            throw new InputArgumentException("Error! Got " + Integer.valueOf(args.length) + " arguments when 0 required");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nВыключаем клиент");
        }));
        CommandManager commandmanager = new CommandManager();
        System.out.println("after commandmanager");
        UDPConnector connector = new UDPConnector();
        System.out.println("after UDPconnector");
        UDPReader reader = null;
        UDPSender sender = null;
        try {
            reader = new UDPReader(connector.getChannel(), connector.getSelector());
            sender = new UDPSender(connector.getChannel());
        }
        catch (Exception e) {
            System.out.println("can't open UDP reader");
        }


        String[] comnames = {"help", "info", "show", "add", "update", "remove_by_id", "clear", "save", "execute_script", "exit", "remove_at", "sort", "history", "sum_of_age", "print_field_ascending_character", "print_field_descending_character"};
        Command[] coms = {new HelpCommand(), new InfoCommand(), new ShowCommand(), new AddCommand(), new RemoveByIdCommand(), new ClearCommand(), new SaveCommand(), new ExecuteScriptCommand(), new ExitCommand(), new RemoveFirstCommand(),new AddIfMinCommand(), new CountLesAdminNameCommand(), new UpdateCommand()};
        for (int i = 0; i < coms.length; ++i)
        {
            try {
                commandmanager.createCommand(comnames[i], coms[i]);
            }
            catch (CommandIOException e) {
                System.out.println(e.getMessage());
            }
        }
        while(true){
            if(connector.getSelector().select()>0) {
                System.out.println("in read");
                SocketAddress client = reader.receive();
                Response response = reader.getShallow().getCommand().execute(reader.getShallow().getArguments(), 0, reader.getShallow().getStudyGroup(), commandmanager, collection);
                sender.send(response, reader.getClient(),9999,logger);
            }
        }
    }
}


