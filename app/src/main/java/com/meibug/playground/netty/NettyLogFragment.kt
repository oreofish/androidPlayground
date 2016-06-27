package com.meibug.playground.netty

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meibug.playground.BaseFragment

import com.meibug.playground.R
import com.meibug.playground.events.ClientGetEvent
import com.meibug.playground.rx.RxBus
import com.trello.rxlifecycle.RxLifecycle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_netty_log.*
import rx.android.schedulers.AndroidSchedulers

/**
 * A placeholder fragment containing a simple view.
 */
class NettyLogFragment : BaseFragment(), NettyContract.LogView {
    override lateinit var presenter: NettyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // String title = getArguments().getString(ARG_TITLE);
        NettyPresenter.logView = this

        RxBus.toObserverable()
                .bindToLifecycle(this)
                .filterByType(LogEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            appendLog(it.msg[0])
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_netty_log, container, false)
        return view
    }

    override fun appendLog(log: String) {
        tvLog.append("\n${log}")
    }

    companion object {
        val ARG_TITLE = "ARG_TITLE"

        fun newInstance(title: String): NettyLogFragment {
            val args = Bundle()
            args.putCharSequence(ARG_TITLE, title)
            val fragment = NettyLogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
