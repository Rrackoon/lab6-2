package org.example.client.core;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.net.*;

import org.example.exception.CommandIOException;
import org.example.managers.*;
import org.example.command.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console implements Serializable {
    private Scanner scanner;
    private boolean active;
    private CommandManager commandmanager;
    private LinkedList<String> commandsStack;
    private int stacksize;
    private UDPSender sender;
    private UDPReader reader;

    public Console(CommandManager commandmanager, UDPSender sender, UDPReader reader)
    {
        this.scanner = new Scanner(System.in);
        this.active = true;
        this.commandmanager = commandmanager;
        this.commandsStack = new LinkedList<String>();
        this.stacksize = 0;
        this.sender = sender;
        this.reader = reader;
    }

    public void start(UDPConnector connector) {
        boolean con = false;
        String host = "";
        int port = 0;
        Pattern pattern = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
        //connector.Connect(host,port);
        connector.Connect("localhost",9999);
        sender = new UDPSender(connector.getDatagramSocket(), connector.getServerAddress(),connector.getServerPort());
        reader =  new UDPReader(connector.getDatagramSocket());
        int[] parametersptr = {-1};
        CommandShallow shallow = new CommandShallow();
        String[] parameters = new String[0];
        while (this.active) {
            readCommand();
        }
    }

    public void print(String line) {
        if (line.equals(null)) {
            return;
        }
        System.out.print(line);
    }

    public void println(String line) {
        if (line.equals(null)){
            System.out.println();
            return;
        }
        System.out.println(line);
    }

    public void readCommand() {
        String[] com;
        stacksize = 0;
        com = scanner.nextLine().split(" ");
        if (com.length > 0) {
            Command command	= null;
            try {
                command = commandmanager.getCommand(com[0]);
                if (command != null && command.getName().equals("save")) {
                    System.out.println("Клиент не может сохранять данные");
                    return;
                }
            }
            catch (CommandIOException e) {
                System.out.println(e.getMessage());
            }

            try {
                CommandShallow shallow = new CommandShallow(command,com);
                System.out.println("command= "+command.getName());
                if (command.getName().equals("add {element}") || command.getName().equals("update id {element}")) {
                    String[] advices = command.getParameterAdvices();
                    String[] parameters = new String[advices.length];
                    for (int i = 0; i < advices.length; ++i) {
                        this.print(advices[i]);
                        boolean ok = false;
                        while (!ok) {
                            parameters[i] = scanner.nextLine();
                            ok = true;
                            if (parameters[i].split(" ").length > 1 && i != 0) {
                                System.out.println("Требуется ввести только одно значение!");
                                ok = false;
                            }
                            if (i != 0) {
                                parameters[i] = parameters[i].split(" ")[0];
                            }
                            if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 7) && (parameters[i].equals(""))) {
                                System.out.println("Переменная не может иметь значение null!");
                                ok = false;
                            }
                            if (ok) {
                                try {
                                    switch(i) {
                                        case 1: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= -32) {
                                                ok = false;
                                                System.out.println("X должен быть больше -522");
                                            }
                                        }
                                        break;
                                        case 2:
                                            Float.parseFloat(parameters[i]);
                                            break;
                                        case 3: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= 0) {
                                                ok = false;
                                                System.out.println("Количество студентов должно быть больше 0");
                                            }
                                        }
                                        break;
                                        case 4:
                                            if (!parameters[i].equals("") && !parameters[i].equals("GREEN") && !parameters[i].equals("YELLOW") && !parameters[i].equals("ORANGE") && !parameters[i].equals("WHITE")&& !parameters[i].equals("BLACK")&& !parameters[i].equals("BLUE")) {
                                                ok = false;
                                                System.out.println("Введено неверное значение");
                                            }
                                            break;
                                        case 5:
                                            if (!parameters[i].equals("") && !parameters[i].equals("DISTANCE_EDUCATION") && !parameters[i].equals("FULL_TIME_EDUCATION") && !parameters[i].equals("EVENING_CLASSES")) {
                                                ok = false;
                                                System.out.println("Введено неверное значение");
                                            }
                                            break;
                                        case 6:
                                            Double.parseDouble(parameters[i]);
                                            break;
                                        case 7: {
                                            float x = Float.parseFloat(parameters[i]);
                                            if (x <= 0) {
                                                System.out.println("Значение должно быть больше 0");
                                                ok = false;
                                            }
                                        }
                                    }
                                }
                                catch (Exception e) {
                                    System.out.println("Введено неверное значение");
                                    ok = false;
                                }


                            }
                        }
                    }
                    try {
                        shallow.setStudyGroup(parameters);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject((Object) shallow);
                    System.out.println("before send 2 :" + shallow.getCommand().getName());
                }
                catch (Exception e){
                    System.out.println("Error oos"+e.getMessage());
                }
                byte[] arr = baos.toByteArray();
                System.out.println("before send "+arr.toString());
                sender.send(arr);
                Response response = null;
                System.out.println("before readResp1");
                try {
                    response = reader.readResponse();
                }
                catch (IOException e){
                    System.out.println("server is not avaliable: "+ e.getMessage());
                }
                catch (ClassNotFoundException e){
                    System.out.println("smth went wrong...: "+ e.getMessage());
                }
                for (String s: response.getMessage()) {
                    if (s.equals("exit")) {
                        this.stop();
                        break;
                    }
                    System.out.println(s);
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        active = false;
    }

}