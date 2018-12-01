package com.salon.cattocdi.fragements;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.salon.cattocdi.ListSalonActivity;
import com.salon.cattocdi.R;
import com.salon.cattocdi.SearchResultActivity;
import com.salon.cattocdi.adapters.ExpandableListViewAdapter;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText etDate, etService, etPlace;
    private Button btSearch;
    ExpandableListView listService;
    CircularImageView circularImageView1, circularImageView2, circularImageView3, circularImageView4;
    private String service;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        etDate = view.findViewById(R.id.fg_search_date_et);
        // Get Current Date
//        final Calendar c = Calendar.getInstance();
//        final int mYear = c.get(Calendar.YEAR);
//        final int mMonth = c.get(Calendar.MONTH);
//        final int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//        etDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        etDate.setText(day + "/" + month + "/" + year);
//                    }
//                }, mYear, mMonth, mDay);
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                dialog.show();
//            }
//        });


//        circularImageView1 = (CircularImageView) view.findViewById(R.id.img_district_1);
//        circularImageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchTrend("Quận 1");
//            }
//        });
//        circularImageView2 = (CircularImageView) view.findViewById(R.id.img_district_2);
//        circularImageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchTrend("Quận 2");
//            }
//        });
//        circularImageView3 = (CircularImageView) view.findViewById(R.id.img_district_3);
//        circularImageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchTrend("Quận 3");
//            }
//        });
//        circularImageView4 = (CircularImageView) view.findViewById(R.id.img_district_4);
//        circularImageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchTrend("Quận 10");
//            }
//        });
//
//
//
//

        etPlace = (EditText) view.findViewById(R.id.edt_place_fg_search);
        etService = (EditText) view.findViewById(R.id.fg_search_service_et);
        btSearch = view.findViewById(R.id.fg_search_search_btn);

        etService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowServiceFragment showServiceFragment = new ShowServiceFragment();
                getFragmentManager().beginTransaction().replace(R.id.activity_main_container_fl, showServiceFragment, null)
                        .addToBackStack(null).commit();
            }
        });

        service = "";
        Bundle bundle = getArguments();
        if(bundle != null){
            service = bundle.getString("service_name");
        }
        etService.setText(service, TextView.BufferType.EDITABLE);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiClient.getInstance().create(SalonApi.class)
                        .searchSalon("Bearer " + MyContants.TOKEN, etPlace.getText().toString(), service)
                        .enqueue(new Callback<List<Salon>>() {
                            @Override
                            public void onResponse(Call<List<Salon>> call, Response<List<Salon>> response) {
                                if(response.body().size() == 0){
                                    AlertError.showDialog(getActivity(),"Không tìm thấy salon nào");
                                }else{
                                    Intent intent = new Intent(getActivity(), ListSalonActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("salon", (Serializable) response.body());
//                                    bundle.putString("title", "Kết quả tìm kiếm");
//                                    bundle.putInt("type", MyContants.RV_ITEM_NORMAL);
//                                    intent.putExtra("bundle", bundle);
                                    intent.putExtra("type", MyContants.RV_ITEM_NORMAL);
                                    intent.putExtra("title", "Kết quả tìm kiếm");
                                    intent.putExtra("salon", (Serializable) response.body());
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onFailure(Call<List<Salon>> call, Throwable t) {
                                AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                            }
                        });


            }
        });


        return view;
    }

    private void searchTrend(String nameTrend) {
        Intent intent = new Intent(getActivity(), ListSalonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "Xu hướng tìm kiếm " + nameTrend);
        bundle.putInt("type", MyContants.RV_ITEM_NORMAL);
        intent.putExtra("activity_content", bundle);
        startActivity(intent);
    }

}
