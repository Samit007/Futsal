package com.example.mylocation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import Api.Api;
import Model.Futsal1;
import Model.RegisterResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFutsal extends AppCompatActivity {
    private EditText flong,flat,fdist,fprov;
    private TextView fname,delete;
    String nam,lon,lati,dis,pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_futsal);
        fname=findViewById(R.id.upname);
        flong=findViewById(R.id.uplong);
        flat=findViewById(R.id.uplat);
        fdist=findViewById(R.id.updist);
        fprov=findViewById(R.id.upprov);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nam = bundle.getString("name");
            lon = (bundle.getString("long"));
            lati = (bundle.getString("lat"));
            dis = (bundle.getString("dist"));
            pro = (bundle.getString("prov"));
        }
        fname.setText(nam);
        flat.setText(lati);
        flong.setText(lon);
        fprov.setText(pro);
        fdist.setText(dis);

        TableLayout delete = findViewById(R.id.futsal_cancel_up);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateFutsal.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure want to Delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Api api= Url.getInstance().create(Api.class);
                        String name=nam;
                        Call<Void> call=api.deletebook(name);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(UpdateFutsal.this, "deleted", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(UpdateFutsal.this,AllFutsal.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(UpdateFutsal.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UpdateFutsal.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        TableLayout save = findViewById(R.id.futsal_up);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });
    }
    private void Update() {
        Api api = Url.getInstance().create(Api.class);
        String name = fname.getText().toString();
        Float longitude = Float.valueOf(flong.getText().toString());
        Float latitude = Float.valueOf(flat.getText().toString());
        String district = fdist.getText().toString();
        int province = Integer.parseInt(fprov.getText().toString());

        Futsal1 futsal= new Futsal1(name, longitude, latitude, district, province);
        Call<RegisterResponse> listCall = api.upFutsal(futsal);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AllFutsal.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
