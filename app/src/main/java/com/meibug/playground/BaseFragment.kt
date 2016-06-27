package com.meibug.playground

import com.trello.rxlifecycle.components.support.RxFragment
import rx.Observable


/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : RxFragment() {

    fun <T> Observable<Any>.filterByType(classType: Class<T>): Observable<T> {
        return this.filter {
            if (!classType.isInstance(it)) {
                return@filter false
            }
            true
        } as Observable<T>
    }
}
