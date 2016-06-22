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

    override fun search() {
        logView?.appendLog("Search")
        Thread(Runnable {
            // val discovery: LogEventBroadcaster = LogEventBroadcaster(InetSocketAddress("255.255.255.255", 8888))
            // discovery.search()
            EchoServer.main(8888)
        }).start()
    }
}