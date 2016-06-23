package com.meibug.playground.netty;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xing on 16/6/21.
 */
public final class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;
    private final List<String> msg;
    private final long received;

    public LogEvent(String logfile, String msg) { //1
        this(null, -1, logfile);
    }

    public LogEvent(InetSocketAddress source, long received, String msg) {  //2
        this.source = source;
        this.msg = new ArrayList<>(Arrays.asList(msg.split("\r\n")));
        this.received = received;
    }

    public InetSocketAddress getSource() { //3
        return source;
    }

    public List<String> getMsg() {  //5
        return msg;
    }

    public long getReceivedTimestamp() {  //6
        return received;
    }
}
