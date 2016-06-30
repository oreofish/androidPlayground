package com.meibug.playground.log;

import com.meibug.playground.BaseFragment;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

/**
 * Created by xing on 16/6/29.
 */
public class LogPresenter implements LogContract.Presenter {
    private LogContract.View view = null;
    private List<String> logstrs;
    private String nullString = null;

    public LogPresenter(LogContract.View v) {
        view = v;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        // prepare some sample logs
        String[] logs = new String[]{
                "=============================================",
                "aaaaa",
                "aaaaa aaaaa",
                "aaaaa aaaaa",
                "aaaaa aaaaa",
                "aaaaa aaaaa",
                "aaaaa aaaaa",
                "aaaaa aaaaa",
                "aaaaa",
                "aaaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "aaaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa aaaa",
                "{\"one\":true,\"three\":[\"red\",\"yellow\",[\"blue\",\"azure\",\"cobalt\",\"teal\"],\"orange\"],\"two\":19.5}",
                "=============================================",
        };
        logstrs = Arrays.asList(logs);

        // init Timber
        Timber.uprootAll();
        Timber.plant(new Timber.DebugTree());
        Timber.plant(new ViewTree());

        // init Logger
        Logger.init()                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                // .hideThreadInfo()               // default shown
                // .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
        // .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }

    @Override
    public void doTimber() {
        view.clearMsg();
        Timber.i("start doTimber()");
        for (String log : logstrs) {
            Timber.d(log);
        }
        Timber.i("end doTimber()");
        try {
            nullString.substring(0);
        } catch (Exception e) {
            Timber.e(e.fillInStackTrace(), "Null");
        }
        view.addMsg("Done");
    }

    @Override
    public void doLogger() {
        view.clearMsg();
        Logger.i("start doLogger()");
        for (String log : logstrs) {
            Logger.i(log);
        }
        Logger.json(logstrs.get(logstrs.size() - 1));
        try {
            nullString.substring(0);
        } catch (Exception e) {
            Logger.e(e.fillInStackTrace(), "Null");
        }
        Logger.i("end doLogger()");
        view.addMsg("Done");
    }

    @Override
    public void doAnLog() {
        view.clearMsg();
        view.addMsg("Pending");
    }

    @Override
    public void doTest() {
        view.clearMsg();
        view.addMsg("Pending");
    }
}
