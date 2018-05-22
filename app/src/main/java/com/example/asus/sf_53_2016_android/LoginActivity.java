package com.example.asus.sf_53_2016_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.UserService;
import util.UtilService;

public class LoginActivity extends AppCompatActivity {

    UtilService serviceUtils;
    UserService userService;
    User u;
    String username;
    String password;
    SharedPreferences sharedPreferences;

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void btnStartsPostsActivity(View view) {
        EditText usernameText=(EditText)findViewById(R.id.userNameInput);
        EditText passwordtext=(EditText)findViewById(R.id.passwordInput);

        username =usernameText.getText().toString();
        password=passwordtext.getText().toString();
        login(username,password);




    }
    public void login(final String username, final String password){
        userService= UtilService.userService;
        Call<User> call=userService.getUserByUsername(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                u=response.body();
                if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                    SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", u.getId());
                    editor.putString("username", u.getUsername());
                    editor.commit();
                    Intent intent=new Intent(LoginActivity.this,PostsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
