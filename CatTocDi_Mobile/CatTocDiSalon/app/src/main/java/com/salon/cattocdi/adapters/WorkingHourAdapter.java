package com.salon.cattocdi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Salon;

import org.w3c.dom.Text;

import java.util.List;

public class WorkingHourAdapter extends RecyclerView.Adapter<WorkingHourAdapter.WorkingHourViewHolder>{

    private List<Salon.DayWorkingHour> workingHourList;

    public WorkingHourAdapter(List<Salon.DayWorkingHour> workingHourList) {
        this.workingHourList = workingHourList;
    }

    @NonNull
    @Override
    public WorkingHourViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.working_hour_item, viewGroup, false);
        return new WorkingHourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkingHourViewHolder holder, int i) {
        if(workingHourList.get(i).getDayInWeek() == 0){
            holder.tvDay.setText("Chủ nhật");
        }else{
            holder.tvDay.setText("Thứ " + (workingHourList.get(i).getDayInWeek() + 1) + "");
        }

        if(workingHourList.get(i).isClose()){
            holder.tvTime.setText("Đóng cửa");
        }else{
            holder.tvTime.setText(workingHourList.get(i).getStartHour() + " - " + workingHourList.get(i).getEndHour());
        }
    }

    @Override
    public int getItemCount() {
        return workingHourList != null ? workingHourList.size() : 0;
    }

    public class WorkingHourViewHolder extends RecyclerView.ViewHolder {
        public View item;
        public TextView tvDay, tvTime;


        public WorkingHourViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView;
            tvDay = itemView.findViewById(R.id.working_hour_item_day);
            tvTime = itemView.findViewById(R.id.working_hour_item_time);
        }
    }
}
