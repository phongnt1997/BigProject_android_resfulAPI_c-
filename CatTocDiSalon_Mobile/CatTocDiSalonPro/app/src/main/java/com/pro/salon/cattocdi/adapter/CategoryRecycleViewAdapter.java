package com.pro.salon.cattocdi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.CategoryViewHolder>{
    private Context context;
    private List<Category> categoryList;
    private RecyclerView serviceRv;
    private TextView previousClick = null;

    public CategoryRecycleViewAdapter(Context context, List<Category> categoryList, RecyclerView serviceRv) {
        this.context = context;
        this.categoryList = categoryList;
        this.serviceRv = serviceRv;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_signup_recycle_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        holder.tvCategory.setText(categoryList.get(position).getName());
        //holder.tvCategory.setText(categoryList.get(position).getName());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(previousClick != null){
                    previousClick.setTextColor(Color.parseColor("#808080"));
                }
                holder.tvCategory.setTextColor(Color.parseColor("#8d6aa1"));
                previousClick = holder.tvCategory;
                serviceRv.setAdapter(new ServiceSignupRecycleViewAdapter(context, categoryList.get(position).getServices()));
            }

        });

    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(categoryList == null) return 0;
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public View item;
        public Button btnEditService;
        public TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.category_signup_title);

            item = itemView;
        }
    }
}
