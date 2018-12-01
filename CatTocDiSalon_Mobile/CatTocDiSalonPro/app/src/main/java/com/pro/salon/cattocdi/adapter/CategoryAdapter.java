package com.pro.salon.cattocdi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.utils.MyContants;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private List<Category> categoryList;
    private RecyclerView serviceRv;
    private TextView previousClick = null;
    private Salon salon;
    private List<Service> checkedList;

    public CategoryAdapter(Context context, List<Category> categoryList,Salon salon) {
        this.context = context;
        this.categoryList = categoryList;
        this.salon = salon;
    }

    public CategoryAdapter(Context context, List<Category> categoryList, List<Service> checkedList) {
        this.context = context;
        this.categoryList = categoryList;
        this.checkedList = checkedList;
    }



    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_service_recycle_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvCategory.setText(category.getName());
        holder.icExpand.setImageResource(R.drawable.ic_collapse);
        holder.rvService.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
       // holder.rvService.setAdapter(new ServiceRecycleViewAdapter(context, MyContants.MANAGER_SERVICE_PAGE ,category.getServices(), salon));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
   /* public List<Service> getCheckedList() {
        List<Service> services = new ArrayList<>();
        if(adapterList != null){
            for (SuggestServiceCardAdapter adapter :
                    adapterList) {
                if(adapter !=  null){
                    services.addAll(adapter.getCheckedList());
                }
            }
        }
        return services;
    }*/

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout item, lnCategory;
        public TextView tvCategory;
        public ImageView icExpand;
        public RecyclerView rvService;
        public ExpandableLayout elService;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = (LinearLayout) itemView;
            this.tvCategory = itemView.findViewById(R.id.salon_service_recycle_view_category_tv);
            this.icExpand = itemView.findViewById(R.id.salon_service_expand_icon);
            this.rvService = itemView.findViewById(R.id.salon_servce_expand_list_rv);
            this.elService = itemView.findViewById(R.id.salon_service_expand_list);
            this.lnCategory = itemView.findViewById(R.id.salon_service_category_ln);
        }
    }
}
