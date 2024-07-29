package com.gorgeous.preference.display;

import android.os.Bundle;

import gorgeous.preference.PreferenceFragmentCompat;

import com.gorgeous.preference.R;

public class DisplayFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_display, rootKey);
    }
}