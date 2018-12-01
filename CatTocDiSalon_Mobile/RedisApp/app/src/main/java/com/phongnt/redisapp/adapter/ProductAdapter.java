package com.phongnt.redisapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phongnt.redisapp.DetailActivity;
import com.phongnt.redisapp.R;
import com.phongnt.redisapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHodel> {
private List<Product> list;
private Context context;

    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_layout, viewGroup, false);
        return new ProductViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHodel viewHodel, final int i) {
        viewHodel.name.setText(list.get(i).getName().toString());
        viewHodel.price.setText(list.get(i).getPrice() + "");
        viewHodel.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHodel extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private View item;
        public ProductViewHodel(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            price = itemView.findViewById(R.id.txtPrice);
            item = itemView;
        }
    }
}
