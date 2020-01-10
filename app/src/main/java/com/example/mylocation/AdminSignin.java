package com.example.mylocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class AdminSignin extends AppCompatActivity {
    private EditText username,password;
    private LinearLayout signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signin);

        username=findViewById(R.id.usernamead);
        password=findViewById(R.id.passwordad);

         signin =findViewById(R.id.signinadmin);

                TextView signup = (TextView) findViewById(R.id.usersign);
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent user = new Intent(AdminSignin.this, Signin.class);
                        startActivity(user);
                    }
                });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String USERNAME = username.getText().toString();
                final String PASSWORD = password.getText().toString();
                final Api api = Url.getInstance().create(Api.class);
                final User2 user2 = new User2(USERNAME, PASSWORD);

                Call<LoginResponse> call = api.getAdminResponse(user2);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.isStatus()) {
                            Toast.makeText(AdminSignin.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AllFutsal.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AdminSignin.this, "Invalid id or pw", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(AdminSignin.this, "fail " + t, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }
}
