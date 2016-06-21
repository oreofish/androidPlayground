package com.meibug.playground.netty

import com.meibug.playground.BasePresenter
import com.meibug.playground.BaseView

/**
 * Created by xing on 16/6/21.
 */
interface NettyContract {
    interface LogView: BaseView<Presenter> {
        fun appendLog(log: String)
    }

    interface ActionView: BaseView<Presenter> {
    }

    interface Presenter: BasePresenter {
        fun search()
    }
}