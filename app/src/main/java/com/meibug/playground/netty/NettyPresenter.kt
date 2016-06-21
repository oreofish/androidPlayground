package com.meibug.playground.netty

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
    }
}