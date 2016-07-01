package com.meibug.playground.log;

/**
 * Created by xing on 16/6/30.
 */
public class LogEvent {
    private int priority;
    private String tag;
    private String message;
    private Throwable t;

    LogEvent(int priority, String tag, String message, Throwable t) {
        this.priority = priority;
        this.tag = tag;
        this.message = message;
        this.t = t;
    }

    public String getTag() {
        return tag;
    }

    public int getPriority() {
        return priority;
    }

    public String getMessage() {
        if (t != null) {
            message = t.getMessage();
        }
        return message;
    }
}
