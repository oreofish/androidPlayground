package com.meibug.playground

/**
 * Created by xing on 16/6/21.
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
}
