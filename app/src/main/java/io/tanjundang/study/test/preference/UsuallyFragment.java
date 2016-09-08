package io.tanjundang.study.test.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    PreSettingActivity MM;

    /**
     * 为毛不执行onAttatch
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        if (context instanceof PreSettingActivity) {
            mActivity = (PreSettingActivity) context;
        }
        super.onAttach(context);
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_usually);
        mActivity = (PreSettingActivity) getActivity();
        mActivity.getSupportActionBar().setTitle("常用设置");
    }

}
