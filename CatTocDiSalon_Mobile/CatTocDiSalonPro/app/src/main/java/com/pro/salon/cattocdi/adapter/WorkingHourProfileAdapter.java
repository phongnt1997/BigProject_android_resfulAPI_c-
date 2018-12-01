package com.pro.salon.cattocdi.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.WorkingHour;

import java.util.Calendar;
import java.util.List;

public class WorkingHourProfileAdapter extends RecyclerView.Adapter<WorkingHourProfileAdapter.WorkingHourViewHolder> {
    private Context context;
    private boolean isCheck = false;
    private List<WorkingHour> workingHourList;


    public WorkingHourProfileAdapter(Context context) {
        this.context = context;
    }

    public WorkingHourProfileAdapter(Context context, List<WorkingHour> list) {
        this.context = context;
        this.workingHourList = list;

    }



    @Override
    public WorkingHourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_working_hour_item_detail, parent, false);
        return new WorkingHourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkingHourViewHolder holder, final int position) {

        if(workingHourList.get(position).getDate() == 0){
            holder.dayOfWeek.setText("Chủ Nhật");
        }else{
            holder.dayOfWeek.setText("Thứ " + (workingHourList.get(position).getDate() +1) + "");
        }
        holder.fromHour.setText(workingHourList.get(position).getStartTime().toString());
        holder.toHour.setText(workingHourList.get(position).getEndTime().toString());


       }





    @Override
    public int getItemCount() {
        if(workingHourList == null) return 0;
        return workingHourList.size();
    }

    public class WorkingHourViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView dayOfWeek;
        public TextView fromHour;
        public TextView toHour;

        public WorkingHourViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.dayOfWeek = itemView.findViewById(R.id.working_hour_item_date);
            this.fromHour = itemView.findViewById(R.id.working_hour_item_from);
            this.toHour = itemView.findViewById(R.id.working_hour_item_to);
        }
    }
}
