package com.github.samtebbs33.net;

import com.github.samtebbs33.net.event.SocketEvent;
import com.github.samtebbs33.net.packet.ServerConnectionPacket;

import java.io.IOException;

/**
 * Created by samtebbs on 30/01/2016.
 */
public class ClientTest extends Client {

    public ClientTest() throws IOException {
        super("localhost", 4444);
    }

    public static void main(String[] args) throws IOException {
        ClientTest client = new ClientTest();
    }

    @Override
    protected void onConnected() {
        System.out.println("Connected");
        try {
            send(new ServerConnectionPacket("SamTebbs33"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        System.out.println("Disconnected, soz");
    }

    @Override
    public void onConnectionRefused(SocketEvent event) {
        System.err.println("Connection refused");
    }


}
