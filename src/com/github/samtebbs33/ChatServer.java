package com.github.samtebbs33;

import com.github.samtebbs33.net.Server;
import com.github.samtebbs33.net.event.SocketEvent;
import com.github.samtebbs33.net.packet.ServerConnectionPacket;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by samtebbs on 30/01/2016.
 */
public class ChatServer extends Server {

    HashMap<String, Socket> clients = new HashMap<>();

    public ChatServer(int port, int maxClients) throws IOException {
        super(port, maxClients);
        start();
    }

    public static void main(String[] args) throws IOException {
        Server server = new ChatServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    @Override
    public int getNumClients() {
        return clients.size();
    }

    @Override
    public void broadcast(Serializable packet) {
        clients.forEach((username, socket) -> {
            try {
                send(packet, socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPacketReceived(SocketEvent.SocketPacketEvent event) {
        if(event.packet instanceof MessagePacket) {
            MessagePacket packet = (MessagePacket) event.packet;
            broadcast(packet);
        } else {
            System.out.println("Got connection packet");
            ServerConnectionPacket packet = (ServerConnectionPacket) event.packet;
            clients.put(packet.username, event.socket);
        }
    }

    @Override
    public void onTimeout(SocketEvent socket) {

    }

    @Override
    public void onDisconnection(SocketEvent.SocketExceptionEvent event) {
        System.err.println("Client has disconnected");
    }
}
