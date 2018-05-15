package com.example.asus.sf_53_2016_android;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.UserService;

public class LoginActivity extends AppCompatActivity {

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void btnStartsPostsActivity(View view) {

        UserService userService = UserService.retrofit.create(UserService.class);
        Call<JsonObject> call = userService.getUsers();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> users, Response<JsonObject> response) {
                String result = response.body().toString();

                JsonObject jsonObject = response.body();

                JsonArray usersJsonArray = jsonObject.get("users").getAsJsonArray();

                EditText userField = (EditText)findViewById(R.id.userNameInput);
                EditText passField = (EditText)findViewById(R.id.passwordInput);

                String usernameForValidation = userField.getText().toString();
                String passwordForValidation = passField.getText().toString();

                String userFound = "Not Found";

                for (JsonElement jsonElement : usersJsonArray){
                    JsonObject user = jsonElement.getAsJsonObject();
                    String username = user.get("username").getAsString();
                    String password = user.get("password").getAsString();

                    if(username.equals(usernameForValidation) && password.equals(passwordForValidation)){
                        userFound = "User Found";

                        Toast.makeText(getApplicationContext(), userFound, Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(), PostsActivity.class );


                        startActivity(i);

                        //ovaj view iz kojeg idem se nece moci dobiti sa back
                        finish();
                        break;
                    }else{
                        Toast.makeText(getApplicationContext(), "Losi podaci", Toast.LENGTH_LONG).show();
                    }


                }




            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                String result = t.getMessage();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });



    }
}
