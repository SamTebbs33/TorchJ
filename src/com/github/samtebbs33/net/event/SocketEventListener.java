package com.github.samtebbs33.net.event;

import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * Created by samtebbs on 29/01/2016.
 */
public interface SocketEventListener {

    /**
     * Called when a packet is received from a socket
     *
     * @param event
     */
    public void onPacketReceived(SocketEvent.SocketPacketEvent event);

    /**
     * Called when a socket timeout occurs
     *
     * @param socket
     */
    @Ignore
    public void onTimeout(SocketEvent socket);

    /**
     * Called when a socket disconnection occurs
     *
     * @param event
     */
    public void onDisconnection(SocketEvent.SocketExceptionEvent event);
}
