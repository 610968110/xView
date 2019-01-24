package com.lbx.xviewdemo.circular;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lbx.xviewdemo.R;
import com.lbx.xviewdemo.circular.activitydemo.FirstActivity;
import com.lbx.xviewdemo.circular.materialdemo.MaterialActivity;
import com.lbx.xviewdemo.circular.viewdemo.CircularButtonActivity;

import lbx.xview.views.circular_reveal.CircularButton;


/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2019/1/22.
 */
public class CircularFragment extends Fragment {

    private boolean isButton = true;

    public static CircularFragment newInstance() {
        Bundle args = new Bundle();
        CircularFragment fragment = new CircularFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circular, container, false);
        Button button1 = view.findViewById(R.id.btn_circular1);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FirstActivity.class);
            startActivity(intent);
        });
        Button button2 = view.findViewById(R.id.btn_circular2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CircularButtonActivity.class);
            startActivity(intent);
        });
        Button button3 = view.findViewById(R.id.btn_circular3);
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MaterialActivity.class);
            startActivity(intent);
        });
        CircularButton circularButton = view.findViewById(R.id.cb_main);
        circularButton.setOnClickListener(v -> {
            circularButton.change(isButton ?
                    CircularButton.CircularButtonStyle.TYPE_PROGRESS :
                    CircularButton.CircularButtonStyle.TYPE_BUTTON);
            isButton = !isButton;
        });
        return view;
    }
}
