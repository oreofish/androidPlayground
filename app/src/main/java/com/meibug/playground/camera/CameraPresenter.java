package com.meibug.playground.camera;

/**
 * Created by xing on 16/6/29.
 */
public class CameraPresenter implements CameraContract.Presenter {
    private CameraContract.View view = null;

    public CameraPresenter(CameraContract.View v) {
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
