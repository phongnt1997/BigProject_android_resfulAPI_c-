package com.salon.cattocdi;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.salon.cattocdi.adapters.FragementAppointmentTestAdapter;
import com.salon.cattocdi.fragements.AppointmentFragment;
import com.salon.cattocdi.models.Appointment;
import com.salon.cattocdi.models.AppointmentCreateModel;
import com.salon.cattocdi.models.DateSlot;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.AppointmentApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAppointmentActivity extends AppCompatActivity {
    private Button btnBook;
    private Salon salon;
    private List<DateSlot.Slot> slots;
    private List<Service> services;
    private DateSlot.Slot chooseSlot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_appointment);

        salon = (Salon) getIntent().getSerializableExtra("salon");
        slots = (List<DateSlot.Slot>) getIntent().getSerializableExtra("date_slots");
        services = (List<Service>) getIntent().getSerializableExtra("services");
        chooseSlot = (DateSlot.Slot) getIntent().getSerializableExtra("slot_choose");

        loadDataToTable();


        btnBook = findViewById(R.id.review_appointment_activity);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    private void loadDataToTable() {
        TextView tvName = findViewById(R.id.appointment_item_expand_name_tv);
        tvName.setText(salon.getName());

        TextView tvDate = findViewById(R.id.appointment_item_expand_date_tv);
        tvDate.setText(chooseSlot.getSlotDate());

        TextView tvTime = findViewById(R.id.appointment_item_expand_time_tv);
        tvTime.setText(chooseSlot.getTimeSlotStr());

        TableLayout table = findViewById(R.id.tbl_service_bill);
        float subTotal = 0;
        for (int i = 0; i < services.size(); i++) {
            Service s = services.get(i);
            TableRow row = (TableRow) LayoutInflater.from(ReviewAppointmentActivity.this).inflate(R.layout.service_table_row, table, false);
            TextView tvServiceName = row.findViewById(R.id.table_row_service_name);
            tvServiceName.setText(s.getName());
            TextView tvServicePrice = row.findViewById(R.id.table_row_service_price);
            tvServicePrice.setText(NumberFormat.getNumberInstance(Locale.US).format(s.getPrice()));

            table.addView(row, i);
            subTotal += s.getPrice();
        }

        TextView tvSubtotal = findViewById(R.id.appointment_item_expand_sub_total_tv);
        tvSubtotal.setText(NumberFormat.getNumberInstance(Locale.US).format(subTotal));

        TextView tvDiscount = findViewById(R.id.appointment_item_expand_discount_tv);

        TextView tvTotal = findViewById(R.id.appointment_item_expand_total_tv);
        float total = subTotal;
        tvTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(total));

    }

    private void bookAppointment(){
        int totalSlot = getSlotNumber(getTotalDuration());
        List<Integer> indexes = new ArrayList<>();
        for (int i = chooseSlot.getIndex(); i < chooseSlot.getIndex() + totalSlot; i++){
            indexes.add(i);
        }

        List<Integer> servicesId = new ArrayList<>();
        for (int i = 0; i < services.size(); i++){
            servicesId.add(services.get(i).getId());
        }

        AppointmentCreateModel model = new AppointmentCreateModel();
        model.setSalonId(salon.getSalonId());
        model.setDuration(getTotalDuration());
        model.setIndexes(indexes);
        model.setServicesId(servicesId);
        model.setSlotId(chooseSlot.getId());
        try {
            long t = new SimpleDateFormat("dd/MM/yyyy").parse(chooseSlot.getSlotDate()).getTime();
            String[] unit = chooseSlot.getTimeSlotStr().split(":");
            int hour = Integer.parseInt(unit[0]);
            int minute = Integer.parseInt(unit[1]);
            t += hour*60*60*1000 + minute*60*1000;
            model.setStartTime(new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new Timestamp(t)));
//            model.setStartTime(new Timestamp(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ApiClient.getInstance().create(AppointmentApi.class)
                .addAppointment("Bearer " + MyContants.TOKEN, model)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200){
                            Intent intent = new Intent(ReviewAppointmentActivity.this, MainActivity.class);
                            intent.putExtra("done", "done");
                            Toast.makeText(ReviewAppointmentActivity.this,"Bạn đã đặt lịch thành công",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        AlertError.showDialogLoginFail(ReviewAppointmentActivity.this, "Fail");
                    }
                });
    }

    private int getTotalDuration() {
        int total = 0;
        if (services != null) {
            for (Service service :
                    services) {
                total += service.getMinutes();
            }
        }
        return total;
    }

    private int getSlotNumber(int duration) {
        int slotTmp = duration / 15;
        if (duration > slotTmp * 15) {
            slotTmp++;
        }
        return slotTmp;
    }
}
