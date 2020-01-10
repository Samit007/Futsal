package com.example.mylocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Api.Api;
import Model.Futsal1;
import Model.RegisterResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFutsal extends AppCompatActivity {
    private EditText fname,flong,flat,fdist,fprov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_futsal);
        fname=findViewById(R.id.fname);
        flong=findViewById(R.id.flong);
        flat=findViewById(R.id.flat);
        fdist=findViewById(R.id.fdist);
        fprov=findViewById(R.id.fprov);

        TableLayout cancel = findViewById(R.id.futsal_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(getApplicationContext(), AllFutsal.class);
                startActivity(cancel);
                finish();
            }
        });


        TableLayout save = findViewById(R.id.futsal_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
    }

    private void Save() {
        Api api = Url.getInstance().create(Api.class);
        String name = fname.getText().toString();
        Float longitude = Float.valueOf(flong.getText().toString());
        Float latitude = Float.valueOf(flat.getText().toString());
        String district = fdist.getText().toString();
        int province = Integer.parseInt(fprov.getText().toString());

        Futsal1 futsal= new Futsal1(name, longitude, latitude, district, province);
        Call<RegisterResponse> listCall = api.addFutsal(futsal);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AllFutsal.class);
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
}
