package com.meibug.playground.log;

import android.animation.ObjectAnimator;
import android.content.res.ObbInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.meibug.playground.BaseFragment;
import com.meibug.playground.R;
import com.meibug.playground.rx.RxBus;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogFragment extends BaseFragment implements LogContract.View, View.OnClickListener {
    private LogContract.Presenter presenter;
    private TextView tvMsg;
    private Button btTimber;
    private Button btLogger;
    private Button btAnLog;
    private Button btTest;

    public static LogFragment newInstance() {
        Bundle args = new Bundle();
        LogFragment fragment = new LogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new LogPresenter(this);

        View view = inflater.inflate(R.layout.fragment_log, container, false);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        tvMsg.setMovementMethod(new ScrollingMovementMethod());
        btTimber = (Button) view.findViewById(R.id.btTimber);
        btTimber.setOnClickListener(this);
        btLogger = (Button) view.findViewById(R.id.btLogger);
        btLogger.setOnClickListener(this);
        btAnLog = (Button) view.findViewById(R.id.btAnLog);
        btAnLog.setOnClickListener(this);
        btTest = (Button) view.findViewById(R.id.btTest);
        btTest.setOnClickListener(this);

        initBus();

        return view;
    }

    void initBus() {
        RxBus.INSTANCE.toObserverable(LogEvent.class)
                .compose(this.<LogEvent>bindToLifecycle())
                .subscribe(new Action1<LogEvent>() {
            @Override
            public void call(LogEvent event) {
                addMsg(event.getMessage());
            }
        });
    }

    @Override
    public void setPresenter(LogContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.start();
    }

    @Override
    public void clearMsg() {
        tvMsg.setText("");
    }

    @Override
    public void addMsg(String msg) {
        tvMsg.append("\n" + msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btTimber:
                Timber.w("onClick btTimber");
                presenter.doTimber();
                Timber.e("done btTimber");
                break;
            case R.id.btLogger:
                Logger.w("onClick btLogger");
                presenter.doLogger();
                Logger.e("done btLogger");
                break;
            case R.id.btAnLog:
                presenter.doAnLog();
                break;
            case R.id.btTest:
                presenter.doTest();
                break;
        }
    }
}
