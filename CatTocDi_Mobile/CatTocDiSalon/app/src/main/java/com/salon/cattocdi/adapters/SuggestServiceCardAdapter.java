package com.salon.cattocdi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Service;

import java.util.ArrayList;
import java.util.List;

public class SuggestServiceCardAdapter extends RecyclerView.Adapter<SuggestServiceCardAdapter.ViewHolder> {

    private List<Service> items, checkedList, currentCheckedList = new ArrayList<>();


    public SuggestServiceCardAdapter() {
    }

    public SuggestServiceCardAdapter(List<Service> items) {
        this.items = items;
    }

    public SuggestServiceCardAdapter(List<Service> items, List<Service> checkedList) {
        this.items = items;
        this.checkedList = checkedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForItem = R.layout.recycle_view_service;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutForItem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.bind(position);
        final Service service = items.get(position);
        holder.mCheckedTextView.setText(service.getName());
        if(checkedList == null){
            checkedList = new ArrayList<>();
        }
        for (Service checked :
                checkedList) {
            if(checked.getServiceId() == service.getServiceId()){
                holder.mCheckedTextView.setChecked(true);

                currentCheckedList.add(service);
            }
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCheckedTextView.setChecked(!holder.mCheckedTextView.isChecked());
                if (holder.mCheckedTextView.isChecked()) {

                    currentCheckedList.add(service);
                } else {
                    currentCheckedList.remove(service);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public List<Service> getCheckedList() {
        if(currentCheckedList == null){
            return new ArrayList<>();
        }
        return currentCheckedList;
    }

    //    public void loadItems(List<Model> listServices) {
//        this.items = listServices;
//        notifyDataSetChanged();
//    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView mCheckedTextView;
        public LinearLayout item;

        ViewHolder(View itemView) {
            super(itemView);
            mCheckedTextView = itemView.findViewById(R.id.checked_text_view);
            this.item = (LinearLayout) itemView;
        }

//        void bind(int position) {
//            // check the state of the model
//            if (!items.get(position).getChecked()) {
//                mCheckedTextView.setChecked(false);}
//            else {
//                mCheckedTextView.setChecked(true);
//            }
//            //mCheckedTextView.setText(String.valueOf(items.get(position).getItem()));
//
//            mCheckedTextView.setText(items.get(position).getItem());
//        }
//
//        @Override
//        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            if (!items.get(adapterPosition).getChecked()) {
//                mCheckedTextView.setChecked(true);
//                items.get(adapterPosition).setChecked(true);
//            }
//            else  {
//                mCheckedTextView.setChecked(false);
//                items.get(adapterPosition).setChecked(false);
//            }
//        }

    }
}
