package org.example.client.core;


import java.nio.*;
import java.io.*;
import java.net.*;


public class UDPConnector {
    private DatagramSocket datagramSocket;
    private InetAddress serverAddress;
    private int serverPort;
    private String host;

    public UDPConnector( String host, int port ) {
        this.datagramSocket = null;
        this.serverAddress = null;
        this.serverPort = port;
        this.host = host;
    }

    public  boolean Connect(String host,int serverPort) {
        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            datagramSocket = new DatagramSocket(serverPort);
            datagramSocket.connect(serverAddress, serverPort);
        }
        catch (IOException e) {
            System.out.println("Could not connect to " + serverAddress);
            return false;
        }
        return true;
    }

    public int getServerPort() {
        return serverPort;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

}
