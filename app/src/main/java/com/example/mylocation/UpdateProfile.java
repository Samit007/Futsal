package com.example.mylocation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Api.Api;
import Model.RegisterResponse;
import Model.User;
import Model.User3;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    private EditText Rpassword, Rname, Remail, Rphone;
    private TextView Rusername;
    String n, e, p, un;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure want to Log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(UpdateProfile.this, Signin.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateProfile.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Rusername = findViewById(R.id.upusername);
        Rname = findViewById(R.id.uptname);
        Rpassword = findViewById(R.id.uppassword);
        Remail = findViewById(R.id.upemail);
        Rphone = findViewById(R.id.upphone);

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", "");
        Toast.makeText(this, "" + username, Toast.LENGTH_SHORT).show();
        Api api = Url.getInstance().create(Api.class);
        Call<List<User3>> userCall = api.getuser(username);
        userCall.enqueue(new Callback<List<User3>>() {
            @Override
            public void onResponse(Call<List<User3>> call, Response<List<User3>> response) {
                List<User3> list = response.body();
                for (User3 user : list) {
                    n = user.getNAME();
                    un = user.getUSERNAME();
                    e = user.getEMAIL();
                    p = user.getPHONE();
                    Rusername.setText(un);
                    Rname.setText(n);
                    Remail.setText(e);
                    Rphone.setText(p);
                }
            }

            @Override
            public void onFailure(Call<List<User3>> call, Throwable t) {
            }
        });
        FrameLayout show = (FrameLayout) findViewById(R.id.btshow);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        FrameLayout save = (FrameLayout) findViewById(R.id.up_user);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void update() {
        Api api = Url.getInstance().create(Api.class);
        String name = Rname.getText().toString();
        String username = Rusername.getText().toString();
        String email = Remail.getText().toString();
        String phone = Rphone.getText().toString();
        String password = Rpassword.getText().toString();

        User user = new User(name, username, password, email, phone);
        Call<RegisterResponse> listCall = api.upuser(user);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Signin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}