package client;

import client.servises.*;
import gui.GuiAnswerHandler;
import library.command.Command;
import library.serialization.SerializationManager;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class GuiClient {
    private final ByteBuffer buffer;
    private final SocketAddress address;
    private MessageService messageService;
    private FrontendInit frontendInit;
    private GuiAnswerHandler guiAnswerHandler;


    public GuiClient(ByteBuffer buffer, SocketAddress address) {
        this.buffer = buffer;
        this.address = address;
    }

    /**
     * @param datagramChannel - канал
     * @throws IOException - в случае ошибки подключения
     */
    public void process(DatagramChannel datagramChannel) throws IOException {
        Selector selector = Selector.open();
        messageService = new MessageService(selector);
        SwingUtilities.invokeLater(() -> {
            frontendInit = new FrontendInit(messageService, new ArgumentValidateManager());
            guiAnswerHandler = new GuiAnswerHandler(frontendInit.getControllers());
        });

        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            if (!messageService.isEmpty()) {
                SelectionKey key = datagramChannel.keyFor(selector);
                key.interestOps(SelectionKey.OP_WRITE);
            }
            selector.select();  //блокирующий
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if (!selectionKey.isValid()) {
                    continue;
                }
                if (selectionKey.isReadable()) {
                    Object response = read(selectionKey, buffer);
                    guiAnswerHandler.answerHandle(response); // getting response
                } else if (selectionKey.isWritable()) {
                    write(selectionKey);

                }
            }
        }

    }

    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {    //пробрасываем исключения и обрабатываем их в NioClient
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) selectionKey.channel();
        channel.receive(buffer);
        return SerializationManager.objectDeserial(buffer.array());
    }

    private void write(SelectionKey selectionKey) throws IOException {
        Command command;
        command = messageService.getFromRequestQueue();
        ByteBuffer answer = ByteBuffer.wrap(SerializationManager.objectSerial(command));
        DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
        datagramChannel.send(answer, address);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
