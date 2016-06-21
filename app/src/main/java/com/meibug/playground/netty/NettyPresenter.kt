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
    val discovery: LogEventBroadcaster = LogEventBroadcaster(InetSocketAddress("255.255.255.255", 8888))

    override fun start() {
        // do nothing
    }

    override fun search() {
        logView?.appendLog("Search")
        discovery.search()
    }
}