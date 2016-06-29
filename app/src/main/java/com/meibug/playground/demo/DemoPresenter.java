package com.meibug.playground.demo;

import com.meibug.playground.BaseFragment;

/**
 * Created by xing on 16/6/29.
 */
public class DemoPresenter implements DemoContract.Presenter {
    private DemoContract.View view = null;

    public DemoPresenter(DemoContract.View v) {
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
