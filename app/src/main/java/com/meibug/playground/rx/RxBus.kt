package com.meibug.playground.rx

import android.os.Looper
import com.meibug.playground.bus.TestEvent
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
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

    fun <T> toObserverable(eventType: Class<T>, mode: ThreadMode): Observable<T> {
        when (mode) {
            ThreadMode.MAIN -> {
                return bus.ofType(eventType).onBackpressureBuffer().observeOn(AndroidSchedulers.mainThread())
            }
            ThreadMode.ASYNC -> {
                return bus.ofType(eventType).onBackpressureBuffer().observeOn(Schedulers.io())
            }
            ThreadMode.BACKGROUND -> {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return bus.ofType(eventType).onBackpressureBuffer().observeOn(Schedulers.io());
                } else {
                    return bus.ofType(eventType).onBackpressureBuffer().onBackpressureBuffer();
                }
            }
            else -> {
                return bus.ofType(eventType).onBackpressureBuffer()
            }
        }
    }

    enum class ThreadMode {
        POSTING,
        MAIN,
        BACKGROUND,
        ASYNC
    }
}