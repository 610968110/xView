package com.lbx.xviewdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lbx.xviewdemo.circular.CircularFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lbx
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Fragment> list = new ArrayList<>();
        list.add(CircularFragment.newInstance());
        list.add(ProgressFragment.newInstance());
        list.add(WaveFragment.newInstance());
        list.add(PraiseFragment.newInstance());
        list.add(SnowFragment.newInstance());
        list.add(FloodFragment.newInstance());

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String name = list.get(position).getClass().getName();
                return name.substring(name.lastIndexOf(".") + 1, name.length());
            }
        });
        tabLayout.setupWithViewPager(pager);
    }
}
