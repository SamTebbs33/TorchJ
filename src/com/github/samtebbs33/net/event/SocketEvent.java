package com.github.samtebbs33.net.event;

import java.net.Socket;

/**
 * Created by samtebbs on 29/01/2016.
 */
public class SocketEvent {

    public Socket socket;

    public SocketEvent(Socket socket) {
        this.socket = socket;
    }

    public static class SocketExceptionEvent extends SocketEvent {

        public Exception e;

        public SocketExceptionEvent(Socket socket, Exception e) {
            super(socket);
            this.e = e;
        }
    }

    public static class SocketPacketEvent extends SocketEvent {

        public Object packet;

        public SocketPacketEvent(Socket socket, Object packet) {
            super(socket);
            this.packet = packet;
        }
    }

}
