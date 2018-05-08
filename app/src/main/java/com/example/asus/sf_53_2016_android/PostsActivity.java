package com.example.asus.sf_53_2016_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adapters.PostAdapter;
import model.Data;
import model.Post;

public class PostsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ArrayList<Post> posts;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(PostsActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        setNavigationViewListener();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_list);

        // Preuzimamo dummy listu iz klase Data
        posts = Data.posts;

        // Pozivamo funkciju za sortiranje
        sortPostsByPreference(posts);

        //Collections.sort(posts);

        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);


    }

    @Override
    protected void onResume(){
        super.onResume();

        posts = Data.posts;
        sortPostsByPreference(posts);
        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.createPostActivity: {
                Intent intent = new Intent(PostsActivity.this, CreatePostActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.readPostActivity: {
                Intent intent = new Intent(PostsActivity.this, ReadPostActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.postsActivity: {
                Intent intent = new Intent(PostsActivity.this, PostsActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    private void setNavigationViewListener(){
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);
    }

    public void sortPostsByPreference(ArrayList<Post> posts){
        // PO cemu da sortiramo ???  ?? ? ?
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPostsBy = sp.getString("lpSortPostsBy", "default123");

        // Sortiramo
        if(sortPostsBy.equals("Date")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post1, Post post2) {
                    return post1.getDate().compareTo(post2.getDate());
                }
            });
        } else if (sortPostsBy.equals("Popularity")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post2, Post post1) {
                    if(post1.getPopularity() > post2.getPopularity()){
                        return 1;
                    } else if (post1.getPopularity() < post2.getPopularity()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(this, "Sorting went wrong, posts unsorted!\n" + sortPostsBy, Toast.LENGTH_LONG).show();
        }
    }

}
