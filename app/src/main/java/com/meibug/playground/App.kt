package com.meibug.playground

import android.app.Application
import org.jetbrains.anko.toast

/**
 * Created by xing on 16/6/23.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }

}