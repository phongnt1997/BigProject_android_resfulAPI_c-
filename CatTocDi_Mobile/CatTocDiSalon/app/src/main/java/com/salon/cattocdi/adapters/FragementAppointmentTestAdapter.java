package com.salon.cattocdi.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Appointment;
import com.salon.cattocdi.models.Comment;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.models.enums.AppointmentStatus;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.AppointmentApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragementAppointmentTestAdapter extends RecyclerView.Adapter<FragementAppointmentTestAdapter.AppointmentCardViewHolder> {
    private Context context;
    private Location curLocation;
    private List<Appointment> appointments;
    private Salon salon;


    public FragementAppointmentTestAdapter(Context context, Location curLocation, List<Appointment> appointments) {
        this.context = context;
        this.curLocation = curLocation;
        this.appointments = appointments;
    }

    public FragementAppointmentTestAdapter() {
    }


    @NonNull
    @Override
    public AppointmentCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_my_appointment, viewGroup, false);

        return new AppointmentCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentCardViewHolder viewHolder, final int i) {
        final Appointment appointment = appointments.get(i);
        salon = MyContants.SalonList.get(appointment.getSalonId());
        viewHolder.tvSalonName.setText(salon != null ? salon.getName() : "");

        viewHolder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(appointment.getStartTime()));
        viewHolder.tvTime.setText(new SimpleDateFormat("HH:mm a").format(appointment.getStartTime())
                + " - "
                + new SimpleDateFormat("HH:mm a").format(appointment.getEndTime()));
        loadDataToTable(viewHolder, appointment);

       if(appointment.getStatus() == AppointmentStatus.NOT_APPROVED.getStatus()) {
           if (appointment.getStartTime().getTime()  + 15 * 60*1000<= Calendar.getInstance().getTimeInMillis()) {
               viewHolder.tvAppoinmentType.setText("Đã quá hạn");
               viewHolder.icDelete.setVisibility(View.GONE);
               viewHolder.tvCancelStatus.setVisibility(View.GONE);
               viewHolder.btnReview.setVisibility(View.GONE);
               viewHolder.commentLn.setVisibility(View.GONE);
           } else  if (appointment.getStartTime().getTime()  + 15 * 60*1000 >= Calendar.getInstance().getTimeInMillis()) {
               viewHolder.tvAppoinmentType.setText("Lịch sắp tới");
               viewHolder.icDelete.setVisibility(View.VISIBLE);
               viewHolder.tvCancelStatus.setVisibility(View.GONE);
               viewHolder.btnReview.setVisibility(View.GONE);
               viewHolder.commentLn.setVisibility(View.GONE);
           }

       } else if(appointment.getStatus() == AppointmentStatus.APPROVED.getStatus()) {
           viewHolder.tvAppoinmentType.setText("Lịch đã qua");
           viewHolder.icDelete.setVisibility(View.GONE);
           viewHolder.tvCancelStatus.setVisibility(View.GONE);

           if(appointment.getReview() != null) {
               viewHolder.commentLn.setVisibility(View.VISIBLE);
               viewHolder.rb.setRating(appointment.getReview().getRating());
               viewHolder.tvComment.setText(appointment.getReview().getContent());
               viewHolder.btnReview.setVisibility(View.GONE);
               viewHolder.rb.setVisibility(View.VISIBLE);
               viewHolder.tvComment.setVisibility(View.VISIBLE);
           }else {
               viewHolder.commentLn.setVisibility(View.GONE);
               viewHolder.btnReview.setVisibility(View.VISIBLE);
               viewHolder.btnReview.setVisibility(View.VISIBLE);
           }

       } else {
           //cancelled
           viewHolder.tvAppoinmentType.setText("Lịch đã hủy");
           viewHolder.icDelete.setVisibility(View.GONE);
           viewHolder.tvCancelStatus.setVisibility(View.VISIBLE);
           viewHolder.btnReview.setVisibility(View.GONE);
           if(appointment.getReason() != null && appointment.getReason().length() > 0 ) {
               viewHolder.commentLn.setVisibility(View.VISIBLE);
               viewHolder.tvReasonTitle.setText("Lý Do Hủy");
               viewHolder.tvComment.setText(appointment.getReason());
               viewHolder.tvComment.setVisibility(View.VISIBLE);
               viewHolder.rb.setVisibility(View.GONE);
           } else {
               viewHolder.commentLn.setVisibility(View.VISIBLE);
               viewHolder.tvReasonTitle.setText("Bạn Đã Hủy");
               viewHolder.tvComment.setVisibility(View.GONE);
               viewHolder.rb.setVisibility(View.GONE);
           }
       }


        viewHolder.appointmentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.appointmentDetail.toggle();
                if (viewHolder.appointmentDetail.isExpanded()) {
                    activeAppointment(viewHolder, appointment.getStatus());
                } else {
                    inactiveAppointment(viewHolder, appointment.getStatus());
                }
            }
        });

        //show dialog and handle dialog
        viewHolder.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.review_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                final RatingBar rb = dialog.findViewById(R.id.review_dialog_rb);
                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        ratingBar.setRating(v);
                    }
                });

                //send comment  .
                //disappear reviewBtn
                //visible comment in expand
                Button btnSend = dialog.findViewById(R.id.review_dialog_btn);
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final EditText et = dialog.findViewById(R.id.review_dialog_et);
                        final float rating = rb.getRating();

                        ApiClient.getInstance().create(AppointmentApi.class)
                                .reviewAppointment("Bearer " + MyContants.TOKEN, new Comment(appointment.getAppointmentId(), (int) rating, et.getText().toString()))
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.code() == 200) {
                                            viewHolder.tvComment.setText(et.getText().toString());
                                            viewHolder.rb.setRating(rating);
                                            viewHolder.commentLn.setVisibility(View.VISIBLE);
                                            viewHolder.tvReasonTitle.setText("Đánh giá của bạn");
                                            viewHolder.btnReview.setVisibility(View.GONE);
                                            Toast.makeText(context, "Cảm ơn bạn đã để lại đánh giá! Rất vui vì được phục vụ bạn", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        dialog.dismiss();
                                        AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng thử lại");
                                    }
                                });


                    }
                });

                dialog.show();
            }
        });


        viewHolder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context); // Context, this, etc.
                if (appointment.getStartTime().getTime() > Calendar.getInstance().getTimeInMillis()
                        && appointment.getStartTime().getTime() - Calendar.getInstance().getTimeInMillis() <= 24 * 60 * 60 * 1000) {
                    TextView btnOk;
                    dialog.setContentView(R.layout.dialog_appointment_late);
                    dialog.setTitle(R.string.dialog_title);
                    btnOk = dialog.findViewById(R.id.dialog_ok);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                if (appointment.getStartTime().getTime() < Calendar.getInstance().getTimeInMillis()) {
                    TextView tvText;
                    dialog.setContentView(R.layout.dialog_appointment);
                    tvText = dialog.findViewById(R.id.dialog_info);
                    tvText.setText(R.string.dialog_text_expired);

                    Button btnCancel = dialog.findViewById(R.id.dialog_cancel);
                    Button btnOk = dialog.findViewById(R.id.dialog_ok);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
                if (appointment.getStartTime().getTime() - Calendar.getInstance().getTimeInMillis() > 24 * 60 * 60 * 1000) {
                    dialog.setContentView(R.layout.dialog_appointment);
                    dialog.setTitle(R.string.dialog_title);

                    Button btnCancel = dialog.findViewById(R.id.dialog_cancel);
                    Button btnOk = dialog.findViewById(R.id.dialog_ok);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ApiClient.getInstance().create(AppointmentApi.class)
                                    .cancelAppointment("Bearer " + MyContants.TOKEN, appointment.getAppointmentId())
                                    .enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.code() == 200) {
                                                dialog.dismiss();
                                                viewHolder.icDelete.setVisibility(View.GONE);
                                                viewHolder.tvCancelStatus.setVisibility(View.VISIBLE);
                                                viewHolder.appointmentRl.setBackgroundColor(Color.parseColor("#eeeeee"));
                                            } else {
                                                AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng thử lại");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng thử lại");
                                        }
                                    });
                        }
                    });

                    dialog.show();
                }


            }
        });

    }

    private void loadDataToTable(AppointmentCardViewHolder holder, final Appointment appointment) {
        TextView tvName = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_name_tv);
        tvName.setText(salon != null ? salon.getName() : "");

        TextView tvDate = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_date_tv);
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(appointment.getStartTime()));

        TextView tvTime = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_time_tv);
        tvTime.setText(new SimpleDateFormat("HH:mm a").format(appointment.getStartTime()) + " - " + new SimpleDateFormat("HH:mm a").format(appointment.getEndTime()));

        holder.directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curLocation != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="
                                    + Double.toString(curLocation.getLatitude())
                                    + "," + Double.toString(curLocation.getLongitude()) + "&daddr=" + salon.getLatitude() + "," + salon.getLongtitude() + ""));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Hãy bật vị trí để chúng tôi có thể chỉ đường cho bạn!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        TableLayout table = holder.appointmentDetail.findViewById(R.id.tbl_service_bill);
        float subTotal = 0;
        for (int i = 0; i < appointment.getServices().size(); i++) {
            Service s = appointment.getServices().get(i);
            TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.service_table_row, table, false);
            TextView tvServiceName = row.findViewById(R.id.table_row_service_name);
            tvServiceName.setText(s.getName());
            TextView tvServicePrice = row.findViewById(R.id.table_row_service_price);
            tvServicePrice.setText(NumberFormat.getNumberInstance(Locale.US).format(s.getPrice()));

            table.addView(row, i);
            subTotal += s.getPrice();
        }

        TextView tvSubtotal = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_sub_total_tv);
        tvSubtotal.setText(NumberFormat.getNumberInstance(Locale.US).format(subTotal));

        TextView tvDiscount = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_discount_tv);
        tvDiscount.setText(appointment.getDiscount() + "%");

        TextView tvTotal = holder.appointmentDetail.findViewById(R.id.appointment_item_expand_total_tv);
        float total = subTotal * (1 - (float) appointment.getDiscount() / 100);
        tvTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(total));

    }

    @Override
    public int getItemCount() {
        if (appointments == null) return 0;
        return appointments.size();

    }

    public class AppointmentCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView icExpand;
        public Button btnReview;
        public View item;
        public CardView icMap;
        public RelativeLayout appointmentRl;
        public ExpandableLayout appointmentDetail;
        public Button directionBtn;
        public ImageView icDelete;
        public RatingBar rb;
        public LinearLayout commentLn;

        public TextView tvAppoinmentType, tvDate, tvSalonName, tvTime, tvComment, tvCancelStatus, tvReasonTitle;

        public AppointmentCardViewHolder(@NonNull View itemView) {
            super(itemView);

            appointmentRl = itemView.findViewById(R.id.fg_appointment_rv_item_rl);
            appointmentDetail = itemView.findViewById(R.id.expandable_layout);
            icExpand = itemView.findViewById(R.id.fg_appointment_expand_ic);

            tvAppoinmentType = itemView.findViewById(R.id.fg_appointment_upcomming_tv);
            tvDate = itemView.findViewById(R.id.fg_appointment_date_tv);
            tvSalonName = itemView.findViewById(R.id.fg_appointment_salon_name);
            tvTime = itemView.findViewById(R.id.fg_appointment_time);
            directionBtn = itemView.findViewById(R.id.btnDirection);
            icDelete = itemView.findViewById(R.id.rc_appointment_ic_delete);
            btnReview = itemView.findViewById(R.id.appointment_item_review_btn);

            commentLn = itemView.findViewById(R.id.appointment_item_expand_review_ln);
            rb = itemView.findViewById(R.id.appointment_item_expand_rb);
            tvComment = itemView.findViewById(R.id.appointment_item_expand_comment_tv);

            tvCancelStatus = itemView.findViewById(R.id.appointment_item_cancel_status_tv);
            tvReasonTitle = itemView.findViewById(R.id.tvReasonTitle);
            item = itemView;

        }

    }

    public void activeAppointment(AppointmentCardViewHolder itemView, int status) {
        itemView.icExpand.setImageResource(R.drawable.ic_collapse);
        if (status == AppointmentStatus.APPROVED.getStatus()) {
            itemView.appointmentDetail.setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }

    public void inactiveAppointment(AppointmentCardViewHolder itemView, int status) {
        itemView.icExpand.setImageResource(R.drawable.ic_expand);
        if (status == AppointmentStatus.APPROVED.getStatus()) {
            itemView.appointmentDetail.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
    }

}
