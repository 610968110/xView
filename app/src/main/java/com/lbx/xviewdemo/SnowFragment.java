package com.lbx.xviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;

import lbx.xview.views.snow.XSnowLayout;

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
 * @date 2018/7/12.
 */

public class SnowFragment extends Fragment {

    public static SnowFragment newInstance() {
        Bundle args = new Bundle();
        SnowFragment fragment = new SnowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snow, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final XSnowLayout snowLayout = view.findViewById(R.id.snowLayout);
        snowLayout.setResources(R.drawable.red_heart, R.drawable.green_heart, R.drawable.white_heart);

        //设置插值器  每个❤会随机抽取其中一个
        snowLayout.setInterpolators(new LinearInterpolator(), new DecelerateInterpolator(), new AccelerateDecelerateInterpolator());

        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snowLayout.startAnimation();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snowLayout.cancelAnimation();
            }
        });
        AppCompatSeekBar alphaS = view.findViewById(R.id.bar_alpha_s);
        AppCompatSeekBar alpha = view.findViewById(R.id.bar_alpha);
        AppCompatSeekBar size = view.findViewById(R.id.bar_size);
        AppCompatSeekBar accumulation = view.findViewById(R.id.bar_accumulation);
        AppCompatSeekBar speed = view.findViewById(R.id.bar_speed);
        AppCompatSeekBar duration = view.findViewById(R.id.bar_duration);

        alphaS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //起始透明度 0~255
                snowLayout.setAlphaFrom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //终点透明度 0~255
                snowLayout.setAlphaTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //设置大小
                snowLayout.setImageSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        accumulation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //设置堆积量
                snowLayout.setAccumulation(seekBar.getProgress());
            }
        });
        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //设置发射速度,毫秒,如100,则100毫秒发射一片雪花
                snowLayout.setSpeed(seekBar.getProgress());
            }
        });
        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //设置飘落速度,毫秒,如100,则从顶部飘到底部的时间是100毫秒
                snowLayout.setDuration(seekBar.getProgress());
            }
        });
    }
}
