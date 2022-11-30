package client;

import client.servises.*;
import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.command.LogCommand;
import library.command.RegCommand;
import library.serialization.SerializationManager;
import library.model.Organization;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Collectors;

public class ConsoleClient {
    private final ICommandCreator commandCreator;
    private final ByteBuffer buffer;
    private final SocketAddress address;
    private final IAnswerHandler answerHandler;
    private MessageService messageService;
    private UserData sessionUser = null;

    public ConsoleClient(ICommandCreator commandCreator, ByteBuffer buffer,
                         SocketAddress address, IAnswerHandler answerHandler) {
        this.commandCreator = commandCreator;
        this.buffer = buffer;
        this.address = address;
        this.answerHandler = answerHandler;
    }

    /**
     * @param datagramChannel - канал
     * @throws IOException - в случае ошибки подключения
     */
    public void process(DatagramChannel datagramChannel) throws IOException {
        Selector selector = Selector.open();
        messageService = new MessageService(selector);
        datagramChannel.register(selector, SelectionKey.OP_READ);
        ServerWriter serverWriter = new ServerWriter(commandCreator, messageService, selector, new ScriptManager(commandCreator));
        Thread writeUserThread = new Thread(serverWriter);
        writeUserThread.start();
        while (true) {
            if (!messageService.isEmpty()) {
                SelectionKey key = datagramChannel.keyFor(selector);
                key.interestOps(SelectionKey.OP_WRITE);
            }
            selector.select();  //блокирующий
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove(); //ВНИМАТЕЛЬНО!!!
                if (!selectionKey.isValid()) {
                    continue;
                }
                if (selectionKey.isReadable()) {
                    Object response = read(selectionKey, buffer);
                    answerHandler.answerHandle(response);
                    if (response != null) {
                        responseHandle(serverWriter, response);
                    }// getting response
                } else if (selectionKey.isWritable()) {
                    write(selectionKey);

                }
            }
        }

    }

    private void responseHandle(ServerWriter serverWriter, Object response) {
        if (response instanceof SpecialSignals) {
            SpecialSignals ss = (SpecialSignals) response;
            if (ss == SpecialSignals.AUTHORIZATION_TRUE || ss == SpecialSignals.REG_TRUE) {
                serverWriter.setSessionUser(sessionUser);
                synchronized (messageService) {
                    messageService.notify();
                }
            } else if (ss == SpecialSignals.AUTHORIZATION_FALSE || ss == SpecialSignals.REG_FALSE) {
                synchronized (messageService) {
                    messageService.notify();
                }
            }
        } else if (response.getClass() == ArrayDeque.class) {
            Deque<Organization> mapObjects = ((Deque<?>) response).stream().map(o -> (Organization) o).collect(Collectors.toCollection(ArrayDeque::new));
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
        if (command.getClass() == LogCommand.class || command.getClass() == RegCommand.class) {
            sessionUser = command.getUserData();
        }
        ByteBuffer answer = ByteBuffer.wrap(SerializationManager.objectSerial(command));
        DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
        datagramChannel.send(answer, address);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

}