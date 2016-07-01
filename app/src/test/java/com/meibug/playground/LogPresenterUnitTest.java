package com.meibug.playground;


import com.meibug.playground.log.LogContract;
import com.meibug.playground.log.LogEvent;
import com.meibug.playground.log.LogFragment;
import com.meibug.playground.log.LogPresenter;
import com.meibug.playground.rx.RxBus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogPresenterUnitTest {
    private LogPresenter presenter;
    private LogContract.View view;

    @Before
    public void setup() {
        view = new View();
        presenter = new LogPresenter(view);
    }

    @Test
    public void testTimber() throws Exception {
        presenter.doTimber();
        // assertEquals(4, 2 + 2);
    }

    @After
    public void clean() {

    }

    public class View implements LogContract.View {
        private LogContract.Presenter presenter;

        View() {
            initBus();
        }

        @Override
        public void clearMsg() {

        }

        @Override
        public void addMsg(String msg) {
        }

        @Override
        public void setPresenter(LogContract.Presenter presenter) {
            this.presenter = presenter;
            presenter.start();
        }

        void initBus() {
            RxBus.INSTANCE.toObserverable(LogEvent.class)
                    // .compose(this.<LogEvent>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<LogEvent>() {
                        @Override
                        public void call(LogEvent event) {
                            addMsg(event.getMessage());
                        }
                    });
        }
    }
}