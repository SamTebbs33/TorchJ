package com.github.samtebbs33;

import com.github.samtebbs33.net.Client;
import com.github.samtebbs33.net.event.SocketEvent;
import com.github.samtebbs33.net.packet.ServerConnectionPacket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by samtebbs on 30/01/2016.
 */
public class ChatClient extends Client {

    String username;

    public ChatClient(String ip, String username, int port) throws IOException {
        super(ip, port);
        this.username = username;
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println("Sending message packet");
            send(new MessagePacket(line, username));
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new ChatClient(args[0], args[1], Integer.parseInt(args[2]));
    }

    @Override
    public void onConnectionRefused(SocketEvent event) {
        System.err.println("Connection refused");
    }

    @Override
    protected void onConnected() {
        System.out.println("Connection established");
        try {
            send(new ServerConnectionPacket(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPacketReceived(SocketEvent.SocketPacketEvent event) {
        if(event.packet instanceof MessagePacket) {
            MessagePacket packet = (MessagePacket) event.packet;
            System.out.printf("%s: %s%n", packet.sender, packet.sender);
        }
    }

    @Override
    public void onTimeout(SocketEvent socket) {

    }

    @Override
    public void onDisconnection(SocketEvent.SocketExceptionEvent event) {
        System.err.println("Connection terminated");
    }
}
