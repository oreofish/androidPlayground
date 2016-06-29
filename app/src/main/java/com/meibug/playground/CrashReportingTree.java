package com.meibug.playground;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by xing on 16/6/29.
 */
public class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        if (t != null) {
            if (priority == Log.ERROR) {
                // address ERROR
            } else if (priority == Log.WARN) {
                // address WARNING
            }
        }
    }
}
