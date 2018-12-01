package com.phongnt.redisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.phongnt.redisapp.adapter.ProductAdapter;
import com.phongnt.redisapp.model.Product;
import com.phongnt.redisapp.requests.ApiClient;
import com.phongnt.redisapp.requests.ProductServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
private TextView txtName;
private TextView txtPrice;
private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        ApiClient.getInstance().create(ProductServices.class)
                .getProductById(id)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        product = response.body();
                        txtName.setText(product.getName());
                        txtPrice.setText(product.getPrice() + "");
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });

    }
}
