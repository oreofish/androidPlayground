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

    public NettyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_netty, container, false);
    }
}
