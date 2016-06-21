package com.meibug.playground.netty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meibug.playground.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NettyLogFragment extends Fragment {
    public static final String ARG_TITLE = "ARG_TITLE";

    public static NettyLogFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        NettyLogFragment fragment = new NettyLogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NettyLogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // String title = getArguments().getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_netty_log, container, false);
    }
}
