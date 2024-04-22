package org.example.server.core;
import org.example.command.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class UDPSender {
    private final DatagramChannel datagramChannel;
    private final Selector selector;

    public UDPSender(DatagramChannel datagramChannel) throws IOException {
        this.datagramChannel = datagramChannel;
        this.datagramChannel.configureBlocking(false);

        // Открываем селектор и регистрируем канал на запись
        this.selector = Selector.open();
        this.datagramChannel.register(selector, SelectionKey.OP_WRITE);
    }

    public void send(Response response, InetAddress address, int port, Logger logger) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//для записи объекта в массив байтов
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            byte[] arr = baos.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(arr);//обертка массива байтов

            // Ожидаем готовности канала на запись
            selector.select();//блокирует текущий поток до тех пор, пока не будет готов как минимум один канал из зарегистрированных в селекторе. В данном случае мы ждем готовности канала на запись.
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isWritable()) {
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    channel.send(buffer, new InetSocketAddress(address, port));
                    logger.fine("Sent " + arr.length + " bytes");
                }
                keyIterator.remove();
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}