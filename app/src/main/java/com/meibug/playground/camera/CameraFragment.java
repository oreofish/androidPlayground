package com.meibug.playground.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.meibug.playground.BaseFragment;
import com.meibug.playground.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CameraFragment extends BaseFragment implements CameraContract.View {
    private CameraContract.Presenter presenter;
    private TextView tvMsg;
    private Button btHi;

    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CameraFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new CameraPresenter(this);
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        btHi = (Button) view.findViewById(R.id.btHi);
        btHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sayHi();
            }
        });
        return view;
    }

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMsg(String msg) {
        tvMsg.setText(msg);
    }
}
