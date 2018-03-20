package com.example.asus.sf_53_2016_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        (findViewById(R.id.btnCreate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostsActivity.this, CreatePostActivity.class));
            }
        });

        (findViewById(R.id.btnRead)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostsActivity.this, ReadPostActivity.class));
            }
        });

        (findViewById(R.id.btnSettings)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostsActivity.this, SettingsActivity.class));
            }
        });
    }
}
