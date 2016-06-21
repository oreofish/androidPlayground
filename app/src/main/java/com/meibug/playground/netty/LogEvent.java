package com.meibug.playground.netty;

import java.net.InetSocketAddress;

/**
 * Created by xing on 16/6/21.
 */
public final class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile, String msg) { //1
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {  //2
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() { //3
        return source;
    }

    public String getLogfile() { //4
        return logfile;
    }

    public String getMsg() {  //5
        return msg;
    }

    public long getReceivedTimestamp() {  //6
        return received;
    }
}
