package client;

import client.servises.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.NoSuchElementException;

public class ClientStarter {
    public static void main(String[] args) {
        try(DatagramChannel datagramChannel = DatagramChannel.open()) {
            int port = Integer.parseInt(args[1]);
            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
            String clientVersion = args[2];
            IValidator objectDataValidatorCon = new ObjectDataValidatorConsole();
            IObjectCreator objectCreatorConsole = new ObjectCreator(objectDataValidatorCon);
            ArgumentValidateManager argumentValidateManager = new ArgumentValidateManager();
            ICommandProducerManager validator = new CommandProduceManager(objectCreatorConsole, argumentValidateManager);
            IReader reader = new Reader(validator);
            IAnswerHandler answerHandler = new AnswerHandler();
            ICommandCreator commandCreator = new CommandCreator(reader);
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
            ConsoleClient consoleClient = new ConsoleClient(commandCreator, ByteBuffer.allocate(256 * 1024),
                    socketAddress, answerHandler);
            GuiClient client = new GuiClient(ByteBuffer.allocate(256 * 1024), socketAddress);
            if (clientVersion.equals("cli")) {
                consoleClient.process(datagramChannel);
            } else if (clientVersion.equals("gui")){
                client.process(datagramChannel);
            } else {
                System.out.println("INCORRECT CLIENT APP TYPE\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
            }

        } catch (IOException e) {
            System.out.println("ERROR CONNECTING TO THE SERVER");
            System.exit(0);
        } catch (NoSuchElementException e) {
            System.out.println("INCORRECT INPUT\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("SPECIFY THE HOST AND PORT OF THE SERVER\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
        } catch (NumberFormatException e) {
            System.out.println("INCORRECT SERVER PORT\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
        } catch (UnresolvedAddressException e) {
            System.out.println("INCORRECT SERVER HOST\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
        } catch (IllegalArgumentException e) {
            System.out.println("INCORRECT INPUT\nSAMPLE: java -jar Client.jar [hostname] [port] [gui/cli]");
        }
    }
}