package com.meibug.playground.camera;

import android.content.Intent;
import android.provider.MediaStore;

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

    @Override
    public void fileCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void contentCamera() {

    }
}
