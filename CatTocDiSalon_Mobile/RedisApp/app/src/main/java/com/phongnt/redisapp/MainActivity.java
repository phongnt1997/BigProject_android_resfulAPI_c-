package com.phongnt.redisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.phongnt.redisapp.adapter.ProductAdapter;
import com.phongnt.redisapp.model.Product;
import com.phongnt.redisapp.requests.ApiClient;
import com.phongnt.redisapp.requests.ProductServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private RecyclerView rcv;
private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv = findViewById(R.id.rcv);

    }

    public void clickToShowlist(View view) {
        ApiClient.getInstance().create(ProductServices.class)
                .getAllProduct()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        products = response.body();
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        ProductAdapter adapter = new ProductAdapter(products, MainActivity.this);
                        rcv.setAdapter(adapter);
                        rcv.setLayoutManager(mLayoutManager);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.d("xxx", "asdasdasd");
                    }
                });
    }
}
