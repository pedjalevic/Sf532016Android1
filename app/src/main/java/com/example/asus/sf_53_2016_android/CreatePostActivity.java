package com.example.asus.sf_53_2016_android;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.PostService;
import util.UtilService;


public class CreatePostActivity extends AppCompatActivity {

    Toolbar toolbar;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 420;

    private boolean permissionGranted = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location deviceLocation;

    // progress dialog to be shown during shit
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getLocationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnConfirm) {
            createPost();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method called on createPost button click
     */
    private void createPost() {
        TextView tvTitle = findViewById(R.id.etTitle);
        TextView tvDescription = findViewById(R.id.etDescription);

        String title = tvTitle.getEditableText().toString();
        String description = tvDescription.getEditableText().toString();

        if(title.isEmpty() || description.isEmpty()){
            Gadgets.showSimpleOkDialog(this, "All fields are mandatory!!!");
            return;
        }

        final Post post = new Post();
        User user = new User();
        user.setId(getSharedPreferences("sp", MODE_PRIVATE).getInt("userId", 1));
        post.setTitle(title);
        post.setDescription(description);
        post.setAuthor(user);



        // Krecemo sa dobavljanjem lokacije
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CreatePostActivity.this);
        try {
            if(permissionGranted){
                progressDialog = Gadgets.getProgressDialog(this, "Locating device... ");
                Task<Location> location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // ako je lokacija pronadjena , pisemo je u post i saljemo na server
                        if(task.isSuccessful()){
                            Toast.makeText(CreatePostActivity.this, "Device located" , Toast.LENGTH_SHORT).show();
                            deviceLocation = (Location) task.getResult();
                            post.setLocation(deviceLocation);
                            sendPostToServer(post);
                            // ako lokaciju nismo mogli naci, jebiga, ne pisemo je nigde al saljemo na server;
                        } else {
                            Toast.makeText(CreatePostActivity.this, "can't locate, sorry brah", Toast.LENGTH_SHORT).show();
                            sendPostToServer(post);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Toast.makeText(CreatePostActivity.this, "security exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPostToServer(Post post){
        PostService postService = UtilService.retrofit.create(PostService.class);
        Call<Post> call = postService.createPost(post);
        // ProgressDialog
        progressDialog.setMessage("Uploading... ");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                progressDialog.dismiss();
                Post post = response.body();
                if (post == null) {
                    Toast.makeText(getApplicationContext(), "nesto je poslo po zluu!!! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Post created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), PostsActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "greska prilikom kreiranja posta ", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Function to be called when clicked on "photo" button
    public void btnOpenCamera(View view) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Checking if there's at least one camera app
        if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCameraIntent, 1);
        }
    }

    public void btnOpenGallery(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (openGalleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openGalleryIntent, 1);
        }
    }

    private void getLocationPermission(){
        String [] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "location permission granted", Toast.LENGTH_SHORT).show();
            permissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE : {
                // ako je grantResults veci od 0 znaci da je nesto dozvoljeno, nesto je 'grant-ovano'
                if(grantResults.length > 0){
                    // prolazimo kroz niz permisija, proveravamo da li su dozvoljeni
                    for(int i=0; i < grantResults.length; i++){
                        // ako korisnik ne dozvoli i jednu permisiju (mora sve da dozvoli) permission nije granted i ne dobijamo lokaciju
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                }
            }
        }
    }

}