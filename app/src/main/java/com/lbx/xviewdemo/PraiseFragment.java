package com.lbx.xviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import lbx.xview.views.snow.XPraiseLayout;

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

public class PraiseFragment extends Fragment {

    public static PraiseFragment newInstance() {
        Bundle args = new Bundle();
        PraiseFragment fragment = new PraiseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_praise, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        XPraiseLayout praiseLayout = view.findViewById(R.id.praiseView);

        //0~255
        praiseLayout.setAlphaFrom(255);
        //0~255
        praiseLayout.setAlphaTo(100);

        //设置动画插值器 随机抽取
        praiseLayout.setInterpolators(new LinearInterpolator(), new DecelerateInterpolator());
        //设置显示的图片 随机抽取 setBitmaps setDrawables
        praiseLayout.setResources(R.drawable.green_heart, R.drawable.red_heart, R.drawable.white_heart);
        view.findViewById(R.id.btn_praise).setOnClickListener(v -> praiseLayout.praise());
    }
}
