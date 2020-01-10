package com.example.mylocation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Api.Api;
import Model.LoginResponse;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin extends AppCompatActivity {
    private EditText username,password;
    private LinearLayout signin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username=findViewById(R.id.usernamelog);
        password=findViewById(R.id.passwordlog);
        signin=findViewById(R.id.signinuser);




                ImageView facebook = (ImageView) findViewById(R.id.facebook_round);
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Signin.this, "facebook not available now", Toast.LENGTH_SHORT).show();

                    }
                });


                ImageView twitter = (ImageView) findViewById(R.id.twitter_round);
                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(Signin.this, "twitter not available now", Toast.LENGTH_SHORT).show();

                    }
                });

                TextView signup = (TextView) findViewById(R.id.Signup);
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent signup = new Intent(Signin.this, Signup.class);
                        startActivity(signup);
                    }
                });
                TextView signupad = (TextView) findViewById(R.id.Admin);
                signupad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent admin = new Intent(Signin.this, AdminSignin.class);
                        startActivity(admin);
                    }
                });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String USERNAME = username.getText().toString();
                final String PASSWORD = password.getText().toString();
                final Api api = Url.getInstance().create(Api.class);
                final User2 user2 = new User2(USERNAME, PASSWORD);
                Call<LoginResponse> call = api.getResponse(user2);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.isStatus()) {
                            String uname = USERNAME;
                            SharedPreferences sharedPreferences =getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", uname);
                            editor.apply();
                            Toast.makeText(Signin.this, "Welcome ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), UpdateProfile.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid id or pw", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(Signin.this, "fail " + t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
