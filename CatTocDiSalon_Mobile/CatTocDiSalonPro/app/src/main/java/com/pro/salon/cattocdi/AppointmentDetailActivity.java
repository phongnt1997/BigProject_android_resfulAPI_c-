package com.pro.salon.cattocdi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.salon.cattocdi.models.Appointment;
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.models.enums.AppointmentStatus;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.pro.salon.cattocdi.R.layout.dialog_cancel_appointment;
import static com.pro.salon.cattocdi.utils.MyContants.CLIENT_PAGE;
import static com.pro.salon.cattocdi.utils.MyContants.HOME_PAGE;
import static com.pro.salon.cattocdi.utils.MyContants.SCHEDULE_PAGE;

public class AppointmentDetailActivity extends AppCompatActivity {

    private TextView tvOK, tvname, tvDate, tvTime;
    private Button btnCancel, btnArrived;
    private Customer customer;
    private Appointment appointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        Intent intent = getIntent();


        final int fromPage = intent.getIntExtra("from_page", -1);
        customer = (Customer) intent.getSerializableExtra("customer");
        appointment = (Appointment) intent.getSerializableExtra("appointment");

        loadDataToTable(appointment);

        TextView tvReason = findViewById(R.id.cancel_reason);


        tvOK = findViewById(R.id.appointment_detail_save_tv);
        tvname = findViewById(R.id.appointment_item_expand_name_tv);
        tvname.setText(customer.getFullName());

        tvDate = findViewById(R.id.appointment_item_expand_date_tv);
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(appointment.getstart()));

        tvTime = findViewById(R.id.appointment_item_expand_time_tv);
        tvTime.setText(new SimpleDateFormat("hh:mm a").format(appointment.getstart()) + " - " + new SimpleDateFormat("hh:mm a").format(appointment.getend()));

        btnCancel = findViewById(R.id.appointment_detail_cancel_btn);
        btnArrived = findViewById(R.id.appointment_detail_arrived_btn);
        final Dialog dialog = new Dialog(this);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPrevious(fromPage);
            }
        });

        if(appointment.getStatus() == AppointmentStatus.NOT_APPROVED.getStatus()) {
            btnArrived.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnArrived.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            if(appointment.getStatus() == AppointmentStatus.CANCEL.getStatus()){
                if(appointment.getCancelReason() != null){
                    tvReason.setText(appointment.getCancelReason());
                }else{
                    tvReason.setText("Khách hàng đã hủy");
                }
            }
        }



        btnArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getInstance().create(SalonClient.class)
                        .approveAppointment("Bearer " + MyContants.TOKEN, appointment.getAppointmentId())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200){
                                    backToPrevious(fromPage);
                                    Toast.makeText(AppointmentDetailActivity.this, "Xác nhận thành công", Toast.LENGTH_LONG);
                                }else{
                                    AlertError.showDialogLoginFail(AppointmentDetailActivity.this, "Có lỗi xảy ra vui lòng thử lại");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                AlertError.showDialogLoginFail(AppointmentDetailActivity.this, "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                            }
                        });

            }
        });
        if (appointment.getStatus() == AppointmentStatus.CANCEL.getStatus()) {
            btnCancel.setBackground(getDrawable(R.drawable.ripple_circle_outline_error_disable));
            btnCancel.setEnabled(false);
        } else {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btnOk, btnCancel;
                    final EditText etReason;
                    dialog.setContentView(R.layout.dialog_cancel_appointment);
                    btnOk = dialog.findViewById(R.id.dialog_ok);
                    btnCancel = dialog.findViewById(R.id.dialog_cancel);
                    etReason = dialog.findViewById(R.id.edt_reason_cancel_appoinment);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ApiClient.getInstance().create(SalonClient.class)
                                    .cancelAppointment("Bearer " + MyContants.TOKEN, appointment.getAppointmentId(), etReason.getText().toString())
                                    .enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if(response.code() == 200){
                                                backToPrevious(fromPage);
                                            }else{
                                                AlertError.showDialogLoginFail(AppointmentDetailActivity.this, "Không thể hủy lịch đặt này");
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            AlertError.showDialogLoginFail(AppointmentDetailActivity.this, "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                                        }
                                    });

                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });
        }


    }

    private void goToScheduleFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_schedule_item);
        startActivity(intent);
    }

    private void goToHomeFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToContactDetail() {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
    }

    private void backToPrevious(int fromPage) {

        switch (fromPage) {
            case HOME_PAGE:
                goToHomeFragment();
                break;
            case SCHEDULE_PAGE:
                goToScheduleFragment();
                break;
            case CLIENT_PAGE:
                goToContactDetail();
                break;
            default:
                goToHomeFragment();

        }
    }

    private void loadDataToTable(Appointment appointment) {

        TableLayout table = findViewById(R.id.tbl_service_bill);
        float subTotal = 0;
        for (int i = 0; i < appointment.getServices().size(); i++) {
            Service s = appointment.getServices().get(i);
            TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.service_table_row, table, false);
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
        tvDiscount.setText(appointment.getDiscount() + "%");

        TextView tvTotal = findViewById(R.id.appointment_item_expand_total_tv);
        float total = subTotal * (1 - (float) appointment.getDiscount() / 100);
        tvTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(total));


    }

    public void clickToSendSMS(View view) {
        Uri uri = Uri.parse("smsto:" + customer.getPhone());
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Xin chào " + customer.getFirstname() + ", ");
        startActivity(it);
    }

    public void clickToCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + customer.getPhone()));
        startActivity(intent);
    }
}

