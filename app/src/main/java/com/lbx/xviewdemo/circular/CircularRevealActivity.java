package com.lbx.xviewdemo.circular;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.lbx.xviewdemo.R;

import lbx.xview.views.circular_reveal.CircularRevealUtil;
import lbx.xview.views.circular_reveal.ICircularReveal;

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
 * @date 2019/1/22
 */
public class CircularRevealActivity extends AppCompatActivity implements ICircularReveal, View.OnTouchListener {

    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        content = findViewById(R.id.reveal_content);
        CircularRevealUtil.getInstance().setCircularRevealAnim(this, false);
        content.setOnTouchListener(this);
    }


    @Override
    public void onBackPressed() {
        CircularRevealUtil.getInstance().onBackPressed(this, 500, 500);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getRootView() {
        return content;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        CircularRevealUtil.getInstance().setCircularRevealAnim(
                this, (int) event.getX(),
                (int) event.getY(), false);
        return false;
    }
}
