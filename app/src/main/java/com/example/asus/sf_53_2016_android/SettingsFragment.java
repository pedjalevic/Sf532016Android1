package com.example.asus.sf_53_2016_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import customPreferences.DatePreference;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        // OVO JE FRANKENSTAJN VARIJANTA < OPASNO
        final DatePreference dp= (DatePreference) findPreference("dpShowFrom");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String dateString = sharedPref.getString("dpValue", "2010-10-10");
        dp.setSummary(dateString);

        dp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                //your code to change values.
                dp.setSummary((String) newValue);

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("dpValue"  ,  (String) newValue);
                editor.commit();

                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    public Preference getPref(String prefKey){
        return findPreference(prefKey);
    }
}
