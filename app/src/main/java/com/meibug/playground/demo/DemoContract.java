package com.meibug.playground.demo;

import com.meibug.playground.BasePresenter;
import com.meibug.playground.BaseView;

/**
 * Created by xing on 16/6/29.
 */
public interface DemoContract {
    interface View extends BaseView<Presenter> {
        void showMsg(String msg);
    }

    interface Presenter extends BasePresenter {
        void sayHi();
    }
}
