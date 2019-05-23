package com.example.jing.grandwordremember;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
    private EditTextPreference mEtPreference;
    private ListPreference mListPreference;
    private CheckBoxPreference mCheckBoxPreference;

    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        addPreferencesFromResource(R.xml.preferences);
        initPreferences();
        Bundle bundle=this.getArguments();
        String content = bundle.getString("data");
    }

    private void initPreferences(){
        mEtPreference = (EditTextPreference)findPreference("word_num");
        mListPreference = (ListPreference)findPreference("color");
        mCheckBoxPreference = (CheckBoxPreference)findPreference("save_stat");
    }

    @Override
    public void onResume() {
        super.onResume();

        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        mListPreference.setSummary(sharedPreferences.getString("color", ""));
        mEtPreference.setSummary(sharedPreferences.getString("word_num", "linc"));

        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("word_num")) {
            mEtPreference.setSummary(
                    sharedPreferences.getString(key, "10"));
        } else if(key.equals("color")) {
            mListPreference.setSummary(sharedPreferences.getString(key, "Red"));
        }
        else if(key.equals("save_stat")){
            mCheckBoxPreference.setSummary(mCheckBoxPreference.isChecked()?"是":"否");
        }
    }
}