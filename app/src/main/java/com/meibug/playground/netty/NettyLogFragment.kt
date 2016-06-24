package com.meibug.playground.netty

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.meibug.playground.R
import com.meibug.playground.events.ClientGetEvent
import com.meibug.playground.rx.RxBus
import kotlinx.android.synthetic.main.fragment_netty_log.*

/**
 * A placeholder fragment containing a simple view.
 */
class NettyLogFragment : Fragment(), NettyContract.LogView {
    override lateinit var presenter: NettyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // String title = getArguments().getString(ARG_TITLE);
        NettyPresenter.logView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_netty_log, container, false)
        RxBus.toObserverable().subscribe {
            if (it is ClientGetEvent) {
                appendLog(it.msg)
            }
        }
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
