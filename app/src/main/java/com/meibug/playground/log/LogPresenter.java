package com.meibug.playground.log;

import com.meibug.playground.BaseFragment;

/**
 * Created by xing on 16/6/29.
 */
public class LogPresenter implements LogContract.Presenter {
    private LogContract.View view = null;

    public LogPresenter(LogContract.View v) {
        view = v;
        view.setPresenter(this);
    }

    @Override
    public void sayHi() {
        view.showMsg("Hi");
    }

    @Override
    public void start() {

    }
}
