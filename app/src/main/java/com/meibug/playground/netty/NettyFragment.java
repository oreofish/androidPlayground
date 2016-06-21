package com.meibug.playground.netty;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meibug.playground.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NettyFragment extends Fragment {
    public static final String ARG_TITLE = "ARG_TITLE";

    public static NettyFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        NettyFragment fragment = new NettyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NettyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // String title = getArguments().getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_netty, container, false);
    }
}
