package org.example.server.core;
import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.*;
public class UDPConnector {
    DatagramChannel channel;
    DatagramSocket socket;
    Selector selector;
    SocketAddress client;
    public UDPConnector() {
        try {
            channel = DatagramChannel.open();
            selector = Selector.open();
            socket = channel.socket();
            SocketAddress address = new InetSocketAddress(9999);
            socket.bind(address);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("connected!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DatagramChannel getChannel()
    {
        return this.channel;
    }
    public DatagramSocket getSocket()
    {
        return this.socket;
    }
    public Selector getSelector()
    {
        return this.selector;
    }
}