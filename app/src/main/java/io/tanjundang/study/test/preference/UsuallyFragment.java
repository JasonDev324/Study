package io.tanjundang.study.test.preference;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import io.tanjundang.study.R;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/9/7
 */

/**
 * 常用设置Fragment
 */
public class UsuallyFragment extends PreferenceFragment {

    PreSettingActivity mActivity;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_usually);
        mActivity = (PreSettingActivity) getActivity();
        mActivity.getSupportActionBar().setTitle("常用设置");
    }

}
