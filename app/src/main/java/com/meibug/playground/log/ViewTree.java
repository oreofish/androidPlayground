package com.meibug.playground.log;

import android.util.Log;

import com.meibug.playground.rx.RxBus;

import timber.log.Timber;

/**
 * Created by xing on 16/6/29.
 */
public class ViewTree extends Timber.Tree {

    @Override protected void log(int priority, String tag, String message, Throwable t) {
        RxBus.INSTANCE.post(new LogEvent(priority, tag, message, t));
    }
}
