package com.github.samtebbs33.net;

import com.github.samtebbs33.net.event.SocketEvent;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by samtebbs on 30/01/2016.
 */
public class ServerTest extends Server {

    ArrayList<Socket> clients = new ArrayList<>();
    public static final int MAX_CLIENTS = 2;

    public ServerTest() throws IOException {
        super(4444, MAX_CLIENTS);
    }

    public static void main(String[] args) throws IOException {
        ServerTest server = new ServerTest();
        server.start();
    }

    @Override
    public int getNumClients() {
        return clients.size();
    }

    @Override
    public void broadcast(Serializable packet) {
        clients.forEach(client -> {
            try {
                send(packet, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onClientConnected(Socket clientSocket) throws IOException {
        super.onClientConnected(clientSocket);
        broadcast("A new client connected!");
        clients.add(clientSocket);
    }

    @Override
    public void onPacketReceived(SocketEvent.SocketPacketEvent event) {
        System.out.println("Received: " + event.packet);
    }

    @Override
    public void onTimeout(SocketEvent socket) {

    }

    @Override
    public void onDisconnection(SocketEvent.SocketExceptionEvent event) {
        System.out.println("Disconnected");
    }

    @Override
    public void close() {
        super.close();
        clients.forEach(client -> {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
