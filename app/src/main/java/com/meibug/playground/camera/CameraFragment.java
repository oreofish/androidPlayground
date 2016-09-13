package com.meibug.playground.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
public class CameraFragment extends BaseFragment implements View.OnClickListener, CameraContract.View {
    private CameraContract.Presenter presenter;
    private TextView tvMsg;
    public static final int REQUEST_CROP = 127;
    public static final int REQUEST_CAMERA = 128;

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
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        view.findViewById(R.id.btFileCamera).setOnClickListener(this);
        view.findViewById(R.id.btContentCamera).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btFileCamera:
                //presenter.fileCamera();
                Uri uri = Uri.fromFile(Environment.getExternalStorageDirectory())
                        .buildUpon()
                        .appendPath("tmp_picture.jpg")
                        .build();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CAMERA);
                break;
            case R.id.btContentCamera:
                presenter.contentCamera();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CROP:
            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = buildCropFromUriIntent(handler.getCropParams());
                    Activity context = handler.getContext();
                    if (context != null) {
                        context.startActivityForResult(intent, REQUEST_CROP);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
                    }
                }
                break;
        }
    }
}
