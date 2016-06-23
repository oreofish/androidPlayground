package com.meibug.playground.netty

import android.util.Log
import java.net.InetSocketAddress

/**
 * Created by xing on 16/6/21.
 */
object NettyPresenter: NettyContract.Presenter {
    var logView: NettyContract.LogView? = null
      set(value) {
          field = value
          value?.presenter = this
      }
    var actionView: NettyContract.ActionView? = null
        set(value) {
            field = value
            value?.presenter = this
        }

    override fun start() {
        // do nothing
    }

    override fun echo() {
        Log.d("NettyPresenter", "start echo")
        Thread(EchoServer(1234)).start()
        Thread(EchoClient("localhost", 1234)).start()
    }

    override fun receive(port: Int) {
        logView?.appendLog("Search")
        Thread(Runnable {
            LogEventMonitor.main(port)
/*
            try {
                EchoServer.main(8888)
            } catch (e: Exception) {
                e.printStackTrace()
            }
*/

        }).start()
    }
}