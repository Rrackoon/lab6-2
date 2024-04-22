package org.example.client.core;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

public class UDPSender {
    private final DatagramSocket datagramSocket;
    private final InetAddress hostAddress;
    private int port;
    public UDPSender(DatagramSocket datagramSocket, InetAddress hostAddress, int port) {
        this.datagramSocket = datagramSocket;
        this.hostAddress = hostAddress;
        this.port = port;
    }

    public void send(byte[] arr) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(arr, arr.length, hostAddress, port);
        datagramSocket.send(datagramPacket);
    }
}

