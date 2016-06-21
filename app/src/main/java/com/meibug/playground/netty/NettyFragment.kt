package com.meibug.playground.netty

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.meibug.playground.R

/**
 * A placeholder fragment containing a simple view.
 */
class NettyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // String title = getArguments().getString(ARG_TITLE);
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_netty, container, false)
    }

    companion object {
        val ARG_TITLE = "ARG_TITLE"

        fun newInstance(title: String): NettyFragment {
            val args = Bundle()
            args.putCharSequence(ARG_TITLE, title)
            val fragment = NettyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
