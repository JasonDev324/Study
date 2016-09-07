package io.tanjundang.study.test.preference;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_usually);
    }
}
