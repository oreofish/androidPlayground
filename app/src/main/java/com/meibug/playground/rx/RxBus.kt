package com.meibug.playground.rx

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

object RxBus {
    private val bus: Subject<Any, Any> = SerializedSubject(PublishSubject.create())

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }
}

private fun <T> Observable<Any>.filterByType(classType: Class<T>): Observable<T> {
    return this.filter {
        if (!classType.isInstance(it)) {
            return@filter false
        }
        true
    } as Observable<T>
}