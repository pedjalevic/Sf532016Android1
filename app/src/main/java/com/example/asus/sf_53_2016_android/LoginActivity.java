package com.example.asus.sf_53_2016_android;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void btnStartsPostsActivity (View view) {
        String userName = ((EditText) findViewById(R.id.userNameInput)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString().trim();


        Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
        startActivity(intent);
    }
}
