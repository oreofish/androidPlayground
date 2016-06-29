package com.meibug.playground

import android.app.Application
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by xing on 16/6/23.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        } else {
            Timber.plant(CrashReportingTree());
        }
    }

    companion object {
        lateinit var instance: App
    }
}