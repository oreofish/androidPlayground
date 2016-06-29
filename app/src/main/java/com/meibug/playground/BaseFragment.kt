package com.meibug.playground

import com.trello.rxlifecycle.components.support.RxFragment
import rx.Observable


/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : RxFragment() {
    companion object {
        var fragments = arrayListOf<BaseFragment>()
    }
}
