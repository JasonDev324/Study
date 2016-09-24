package io.tanjundang.study;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.parallaxpager.ParallaxContainer;

import io.tanjundang.study.common.tools.Functions;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParallaxFragment extends Fragment implements ViewPager.OnPageChangeListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parallax, container, false);

        // find the parallax container
        ParallaxContainer parallaxContainer = (ParallaxContainer) view.findViewById(R.id.parallax_container);

        // specify whether pager will loop
        //是否开始循环
        parallaxContainer.setLooping(false);

        // wrap the inflater and inflate children with custom attributes
        parallaxContainer.setupChildren(inflater,
                R.layout.parallax_view_1,
                R.layout.parallax_view_2,
                R.layout.parallax_view_3

        );

        parallaxContainer.setOnPageChangeListener(this);

        view.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.toast("开始体验");
            }
        });
        return view;
    }

    @Override
    public void onPageScrolled(int position, float offset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Functions.toast(position + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
