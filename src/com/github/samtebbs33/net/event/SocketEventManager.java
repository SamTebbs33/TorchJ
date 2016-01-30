package com.github.samtebbs33.net.event;

import com.github.samtebbs33.event.EventManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by samtebbs on 29/01/2016.
 */
public class SocketEventManager extends EventManager<SocketEventListener> implements Runnable {

    Socket socket;

    public SocketEventManager(Socket socket) throws IOException {
        this.socket = socket;
    }

    /**
     * Start the event manager and begin listening for socket events
     */
    @Override
    public void start() {
        try {
            super.start(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by the event manager thread
     */
    @Override
    public void run() {
        try {
            final ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            Object obj;
            while ((obj = reader.readObject()) != null) {
                final Object obj2 = obj;
                notify(listener -> listener.onPacketReceived(new SocketEvent.SocketPacketEvent(socket, obj2)));
            }
        } catch (SocketException | EOFException e) {
            notify(listener -> listener.onDisconnection(new SocketEvent.SocketExceptionEvent(socket, e)));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
