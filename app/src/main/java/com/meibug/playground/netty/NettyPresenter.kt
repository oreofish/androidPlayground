package com.meibug.playground.netty

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

    override fun search(port: Int) {
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