package com.lbx.xviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import lbx.xview.views.flood.XFloodLayout;

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

public class FloodFragment extends Fragment {

    public static FloodFragment newInstance() {
        Bundle args = new Bundle();
        FloodFragment fragment = new FloodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flood, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        View foregroundView = View.inflate(getContext(), R.layout.view_test, null);
        ImageView backgroundView = new ImageView(getContext());
        backgroundView.setBackgroundResource(R.drawable.bg);

        XFloodLayout floodLayout = view.findViewById(R.id.floodView);
        floodLayout.init(foregroundView, backgroundView);

        view.findViewById(R.id.btn_flood).setOnClickListener(v -> {
            if (!floodLayout.isFlood()) {
                //打开
                floodLayout.flood(500, new AccelerateInterpolator());
            } else {
                //关闭
                floodLayout.unFlood(500, new DecelerateInterpolator());
            }
        });

        floodLayout.setOnFloodUpdateListener(new XFloodLayout.OnFloodUpdateListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onUpdate(float percent, boolean isFlood) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
