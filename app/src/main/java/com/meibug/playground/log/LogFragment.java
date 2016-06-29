package com.meibug.playground.log;

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
public class LogFragment extends BaseFragment implements LogContract.View {
    private LogContract.Presenter presenter;
    private TextView tvMsg;
    private Button btHi;

    public static LogFragment newInstance() {
        Bundle args = new Bundle();
        LogFragment fragment = new LogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LogPresenter(this);
        View view = inflater.inflate(R.layout.fragment_log, container, false);
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
    public void setPresenter(LogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMsg(String msg) {
        tvMsg.setText(msg);
    }
}
