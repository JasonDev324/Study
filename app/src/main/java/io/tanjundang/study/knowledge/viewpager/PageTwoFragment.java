package io.tanjundang.study.knowledge.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanjundang.study.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageTwoFragment extends Fragment {


    public PageTwoFragment() {
        // Required empty public constructor
    }

    public static PageTwoFragment newInstance() {
        return new PageTwoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_two, container, false);
    }

}
