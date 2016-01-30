package com.github.samtebbs33.net;

import com.github.samtebbs33.net.event.SocketEvent;
import com.github.samtebbs33.net.event.SocketEventListener;
import com.github.samtebbs33.net.event.SocketEventManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by samtebbs on 30/01/2016.
 */
public abstract class Client implements SocketEventListener {

    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream outStream;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        try {
            this.socket = new Socket(ip, port);
            this.outStream = new ObjectOutputStream(this.socket.getOutputStream());
            SocketEventManager eventManager = new SocketEventManager(socket);
            eventManager.addListener(this);
            eventManager.start();
            onConnected();
        } catch (ConnectException e) {
            onConnectionRefused(new SocketEvent(socket));
        } catch (SocketException e) {
            onDisconnection(new SocketEvent.SocketExceptionEvent(socket, e));
        }
    }

    /**
     * Send a packet to the connected server
     *
     * @param packet
     * @throws IOException
     */
    public void send(Serializable packet) throws IOException {
        try {
            outStream.writeObject(packet);
        } catch (SocketException e) {
            onDisconnection(new SocketEvent.SocketExceptionEvent(socket, e));
        }
    }

    /**
     * Called when a server connection is refused
     *
     * @param event
     */
    public abstract void onConnectionRefused(SocketEvent event);

    /**
     * Called when a server connection is established
     */
    protected abstract void onConnected();

}