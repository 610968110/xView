package com.lbx.xviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class WaveFragment extends Fragment {

    public static WaveFragment newInstance() {
        Bundle args = new Bundle();
        WaveFragment fragment = new WaveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int progress = XWaveView.getProgress();
                    progress += 20;
                    if (progress > 100) {
                        progress = 0;
                    }
                    /**
                     * 设置进度
                     *
                     * @param progress 进度
                     * @param duration 动画时间
                     * @param delay 延时
                     */
                    XWaveView.setWaveProgress(progress, 1000, 0);
                    handler.sendEmptyMessageDelayed(0, 3000);
                    break;
                default:
                    break;
            }
        }
    };
    private lbx.xview.views.wave.XWaveView XWaveView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wave, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        XWaveView = view.findViewById(R.id.waveView);
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
