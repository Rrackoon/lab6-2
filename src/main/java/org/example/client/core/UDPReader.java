package org.example.client.core;

import java.io.*;
import java.nio.*;
import java.net.*;
import java.nio.channels.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.logging.*;
import org.example.command.*;
import org.example.managers.*;
public class UDPReader {

        private final DatagramSocket datagramSocket;

        public UDPReader(DatagramSocket datagramSocket) {
            this.datagramSocket = datagramSocket;
        }

        public DatagramSocket getDatagramSocket() {
            return datagramSocket;
        }

        public Response readResponse() throws IOException, ClassNotFoundException {
            byte[] buffer = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);

            byte[] data = packet.getData();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Response) ois.readObject();
        }
}
