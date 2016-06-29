package com.meibug.playground.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.meibug.playground.BaseFragment;
import com.meibug.playground.R;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class DemoFragment extends BaseFragment implements DemoContract.View {
    private DemoContract.Presenter presenter;
    private TextView tvMsg;
    private Button btHi;

    public static DemoFragment newInstance() {
        Bundle args = new Bundle();
        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DemoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new DemoPresenter(this);
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
    public void setPresenter(DemoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMsg(String msg) {
        tvMsg.setText(msg);
    }
}
