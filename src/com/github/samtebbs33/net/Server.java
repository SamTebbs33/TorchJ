package com.github.samtebbs33.net;

import com.github.samtebbs33.event.Startable;
import com.github.samtebbs33.net.event.SocketEventListener;
import com.github.samtebbs33.net.event.SocketEventManager;
import com.github.samtebbs33.net.packet.ClientConnectionPacket;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by samtebbs on 30/01/2016.
 */
public abstract class Server implements SocketEventListener, Startable, Closeable {

    private int port;
    protected ServerSocket socket;
    private Thread clientConnectionThread;
    private Random rand = new Random();

    public Server(int port, int maxClients) throws IOException {
        this.port = port;
        this.socket = new ServerSocket(port);
        clientConnectionThread = new Thread(() -> {
            while (true) {
                Socket socket = null;
                try {
                    socket = this.socket.accept();
                    if (getNumClients() < maxClients) {
                        onClientConnected(socket);
                    } else {
                        onClientRefused(socket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Returns the number of clients connected to the server
     *
     * @return
     */
    public abstract int getNumClients();

    /**
     * Send a packet to a client socket
     *
     * @param packet
     * @param client
     * @throws IOException
     */
    public void send(Serializable packet, Socket client) throws IOException {
        ObjectOutputStream outStream = new ObjectOutputStream(client.getOutputStream());
        outStream.writeObject(packet);
    }

    /**
     * Broadcast a packet to all connected clients
     *
     * @param packet
     */
    public abstract void broadcast(Serializable packet);

    /**
     * Stop the slient acceptance thread
     */
    public void stopAcceptingClients() {
        this.clientConnectionThread.interrupt();
    }

    /**
     * Restart the client acceptance thread after it has been stopped
     */
    public void restartAcceptingClients() {
        if (!this.clientConnectionThread.isAlive()) this.clientConnectionThread.start();
    }

    /**
     * Called when a client connects to the server
     *
     * @param clientSocket
     * @throws IOException
     */
    protected void onClientConnected(Socket clientSocket) throws IOException {
        SocketEventManager manager = new SocketEventManager(clientSocket);
        manager.addListener(this);
        manager.start();
        send(new ClientConnectionPacket(rand.nextLong()), clientSocket);
    }

    /**
     * Called when a client connection is refused
     *
     * @param clientSocket
     */
    protected void onClientRefused(Socket clientSocket) {
        try {
            send("Number of allowed clients has been reached", clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the server socket and stop the client acceptance thread
     */
    @Override
    public void close() {
        if (clientConnectionThread.isAlive()) clientConnectionThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the server
     */
    @Override
    public void start() {
        clientConnectionThread.start();
    }

}
