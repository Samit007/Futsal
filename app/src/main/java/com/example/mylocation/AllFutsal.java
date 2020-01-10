package com.example.mylocation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import Adapter.DetailAdapter;
import Api.Api;
import Model.Futsal2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllFutsal extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout AddFutsal;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AllFutsal.this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure want to Log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AllFutsal.this, Signin.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AllFutsal.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_futsal);
        recyclerView = findViewById(R.id.recyclerView);
        AddFutsal=findViewById(R.id.AddFutsal);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh5);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showFutsal();
                pullToRefresh.setRefreshing(false);
            }
        });
        AddFutsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllFutsal.this, AddFutsal.class);
                startActivity(intent);
                finish();
            }
        });
        showFutsal();
    }
    private void showFutsal(){

        Retrofit retrofit= Url.getInstance();
        Api api = retrofit.create(Api.class);
        Call<List<Futsal2>> listCall= api.getFutsal();
        listCall.enqueue(new Callback<List<Futsal2>>() {
            @Override
            public void onResponse(Call<List<Futsal2>> call, Response<List<Futsal2>> response) {
                List<Futsal2> futsal = response.body();
                DetailAdapter detailAdapter = new DetailAdapter(AllFutsal.this, futsal);
                recyclerView.setAdapter(detailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AllFutsal.this));
            }

            @Override
            public void onFailure(Call<List<Futsal2>> call, Throwable t) {
                Toast.makeText(AllFutsal.this, "failed "+ t, Toast.LENGTH_SHORT).show();

                Log.d("My error ", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}


