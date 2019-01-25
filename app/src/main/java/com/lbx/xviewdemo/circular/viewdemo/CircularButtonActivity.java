package com.lbx.xviewdemo.circular.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lbx.xviewdemo.R;

import lbx.xview.views.circular_reveal.CircularRevealUtils;

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
 * @date 2019/1/23.
 */
public class CircularButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_circular_button);

        FloatingActionButton floatBtn = findViewById(R.id.floatBtn);
        final View secondView = findViewById(R.id.second);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (secondView.getVisibility() == View.INVISIBLE) {
                    CircularRevealUtils.ViewCircularRevealUtil().showFloatingViewByClickView(v, secondView);
                } else {
                    CircularRevealUtils.ViewCircularRevealUtil().goneFloatingViewByClickView(v, secondView);
                }
            }
        });
    }
}
