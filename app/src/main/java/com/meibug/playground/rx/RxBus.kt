package com.meibug.playground.rx

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

object RxBus {
    private val bus: Subject<Any, Any> = SerializedSubject(PublishSubject.create())

    fun post(o: Any) {
        bus.onNext(o)
    }

    fun <T> toObserverable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType) // ofType = filter + cast
    }
}
