package org.example.server.core;

import org.example.command.Command;
import org.example.command.CommandShallow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UDPReader {
    public static String in_string;
    ByteBuffer in_buffer;
    DatagramChannel channel;
    SocketAddress client;
    Selector selector;
    CommandShallow shallow ;
    Scanner scanner;

    public UDPReader(DatagramChannel channel, Selector selector) throws Exception {

        in_string= "";
        in_buffer = ByteBuffer.allocateDirect(1024);
        this.channel=channel;
        this.selector=selector;
        this.scanner = new Scanner(System.in);

    }

    public CommandShallow getShallow() {
        return shallow;
    }

    public SocketAddress getClient()
    {return this.client;}
    public void execute() throws IOException {
        // Selector selector = Selector.open();
        if (true) {

            try {

                if (selector.select() > 0) {

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {

                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            client = receive();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("exec err" + e.getMessage());

            }
        }
    }

    public SocketAddress  receive() {
        // SocketAddress client;
        try {
            //channel = (DatagramChannel) key.channel();
            in_buffer = ByteBuffer.allocate(65507);
            client = channel.receive(in_buffer);
            ByteArrayInputStream bis = new ByteArrayInputStream(in_buffer.array());
            ObjectInput in = new ObjectInputStream(bis);
            shallow = (CommandShallow)in.readObject();
            in_buffer.clear();


        } catch (Exception e){
            System.out.println("resive error: "+e.getMessage());
        }
        return client;


    }

}