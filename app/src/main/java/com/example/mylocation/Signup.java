package com.example.mylocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import Api.Api;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    private EditText Rusername, Rpassword, Rname, Rrepassword, Remail, Rphone;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Rusername = findViewById(R.id.username);
        Rname = findViewById(R.id.name);
        Rpassword = findViewById(R.id.password);
        Rrepassword = findViewById(R.id.repassword);
        Remail = findViewById(R.id.email);
        getLocationPermission();
        Rphone = findViewById(R.id.phone);


        FrameLayout cancel = (FrameLayout) findViewById(R.id.button1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(getApplicationContext(), Signin.class);
                startActivity(cancel);
            }
        });

        FrameLayout save = (FrameLayout) findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }

            private void login() {
                String pass1 = Rpassword.getText().toString();
                String pass2 = Rrepassword.getText().toString();
                if (pass1.equals(pass2)) {
                    addUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Password and confirm password doesn't matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUser() {
        Api api = Url.getInstance().create(Api.class);
        String name = Rname.getText().toString();
        String username = Rusername.getText().toString();
        String email = Remail.getText().toString();
        String password = Rpassword.getText().toString();
        String phone = Rphone.getText().toString();

        User user = new User(name, username, password, email, phone);
        Call<RegisterResponse> listCall = api.addUsers(user);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Signin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Username already registered", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}

