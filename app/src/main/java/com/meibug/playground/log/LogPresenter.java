package com.meibug.playground.log;

import android.os.AsyncTask;
import android.os.Handler;

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
                "aaaaa",
                "aaaaa aaaaa",
                "aaaaa",
                "aaaaa aaaaa",
                "aaaaa",
                "aaaaa aaaaa",
                "aaaaa",
                "aaaaa aaaaa",
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
        // init Timber
        Timber.uprootAll();
        Timber.plant(new Timber.DebugTree());
        Timber.plant(new ViewTree());

        view.clearMsg();
        Timber.i("start doTimber()");
        for (String log : logstrs) {
            Timber.d(log);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Timber.w("write log in thread");
            }
        }).start();

        try {
            nullString.substring(0);
        } catch (Exception e) {
            Timber.e(e.fillInStackTrace(), "Null");
        }

        Timber.i("end doTimber()");
        view.addMsg("Done");
    }

    @Override
    public void doLogger() {
        view.clearMsg();
        Logger.i("start doLogger()");
        for (String log : logstrs) {
            Logger.i(log);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.w("write log in thread");
            }
        }).start();

        try {
            nullString.substring(0);
        } catch (Exception e) {
            Logger.e(e.fillInStackTrace(), "Null");
        }
        Logger.json(logstrs.get(logstrs.size() - 2));

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
        view.addMsg("Testing...");
        final int COUNT = 50; // logstrs size is 20

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // test Timber
                Timber.uprootAll();
                Timber.plant(new Timber.DebugTree());

                Timber.i("start Timber test");
                long startAt = System.currentTimeMillis();
                for(int i = 0;i < COUNT; i++) {
                    for (String log : logstrs) {
                        Timber.d(log);
                    }
                }
                long endAt = System.currentTimeMillis();
                Timber.e("10000 Timber logs spend %d", endAt - startAt);
                view.addMsg("Timber spends " + Long.toString(endAt - startAt) + "ms.");


                // test Logger
                Logger.i("start Logger test");
                startAt = System.currentTimeMillis();
                for(int i = 0;i < COUNT; i++) {
                    for (String log : logstrs) {
                        Logger.d(log);
                    }
                }
                endAt = System.currentTimeMillis();
                Logger.e("10000 Logger logs spend %d", endAt - startAt);
                view.addMsg("Logger spends " + Long.toString(endAt - startAt) + "ms.");
                view.addMsg("Test Done");
            }
        });
    }
}
