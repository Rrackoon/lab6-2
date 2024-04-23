package org.example.client.core;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPSender {
    private static final Logger logger = Logger.getLogger(UDPSender.class.getName());
    private final DatagramSocket datagramSocket;
    private final SocketAddress hostAddress;
    private int port;
    public UDPSender(DatagramSocket datagramSocket, SocketAddress hostAddress, int port) {
        this.datagramSocket = datagramSocket;
        this.hostAddress = hostAddress;
        this.port = port;
    }

    public void send(byte[] arr) throws IOException {
        logger.info("Sending data to " + hostAddress + " on port " + port);
       try {
           DatagramPacket datagramPacket = new DatagramPacket(arr, arr.length);
           datagramSocket.send(datagramPacket);
       }
       catch (Exception e){
           logger.log(Level.SEVERE, "Error sending data: " + e.getMessage(), e);
       }
    }
}

