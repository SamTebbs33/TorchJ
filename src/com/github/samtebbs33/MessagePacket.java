package com.github.samtebbs33;

import java.io.Serializable;

/**
 * Created by samtebbs on 30/01/2016.
 */
public class MessagePacket implements Serializable {

    public String message, recipient, sender;

    public MessagePacket(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }
}
