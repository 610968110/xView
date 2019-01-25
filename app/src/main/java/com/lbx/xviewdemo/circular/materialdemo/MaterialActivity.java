package com.lbx.xviewdemo.circular.materialdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lbx.xviewdemo.R;

import lbx.xview.views.circular_reveal.CircularRevealUtils;
import lbx.xview.views.circular_reveal.IMaterial;
import lbx.xview.views.circular_reveal.MaterialUtil;

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
public class MaterialActivity extends AppCompatActivity implements IMaterial {

    ImageView imageView;
    ImageButton imageButton;
    Button closeButton;
    ViewGroup revealView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        imageView = findViewById(R.id.imageView);
        imageButton = findViewById(R.id.launchTwitterAnimation);
        closeButton = findViewById(R.id.btn_close);
        revealView = findViewById(R.id.linearView);

        final MaterialUtil materialUtil = CircularRevealUtils.MaterialUtil(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialUtil.launchTwitter(MaterialActivity.this);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialUtil.closeTwitter(MaterialActivity.this);
            }
        });
    }


    @Override
    public View getFloatingButton() {
        return imageButton;
    }

    @Override
    public View getTopView() {
        return revealView;
    }

    @Override
    public View[] getTopChildView() {
        return new View[]{closeButton};
    }

    @Override
    public View getBottomView() {
        return imageView;
    }
}
