package com.meibug.playground.events;

public class ClientGetEvent {
    private String msg;

    public ClientGetEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
