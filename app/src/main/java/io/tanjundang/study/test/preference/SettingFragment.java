package io.tanjundang.study.test.preference;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanjundang.study.MainActivity;
import io.tanjundang.study.R;
import io.tanjundang.study.base.Initial;
import io.tanjundang.study.common.tools.Functions;


public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    Preference clearCache;
    Preference network;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        clearCache = findPreference("clearCache");
        clearCache.setOnPreferenceClickListener(this);
        network = findPreference("network");
        network.setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.equals(clearCache)) {
            clearCache.setSummary("0MB");
            Functions.toast("清理缓存成功");
        } else if (preference.equals(network)) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return false;
    }

}
