package io.tanjundang.study.knowledge.preference;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;

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
    Preference clearCache;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_usually);
        mActivity = (PreSettingActivity) getActivity();
        mActivity.getSupportActionBar().setTitle("常用设置");

        clearCache = findPreference("clearCache");
        clearCache.setSummary(String.format("%.2fMB", Functions.getCacheSize()));
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Functions.clearCache();
                clearCache.setSummary(String.format("%.2fMB", Functions.getCacheSize()));
                return false;
            }
        });
    }

}
