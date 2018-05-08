package com.example.asus.sf_53_2016_android;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    SettingsFragment sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);

        sf = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, sf)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String adminName = sp.getString("adminName", "defaultValue");

        //Toast.makeText(this, adminName, Toast.LENGTH_LONG).show();

        EditTextPreference etp = (EditTextPreference) sf.getPref("adminName");

        etp.setSummary(adminName);

    }
}