package com.salon.cattocdi.utils;

import android.support.annotation.NonNull;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Appointment;
import com.salon.cattocdi.models.Category;
import com.salon.cattocdi.models.Comment;
import com.salon.cattocdi.models.Customer;
import com.salon.cattocdi.models.Promotion;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.models.enums.AppointmentStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyContants {
    public static final String BASE_URL = "http://192.168.43.217/cattocdi.userapi/";
    public static final String PHONE_TEST = "0123456789";
    public static final String PASSWORD_TEST = "123";
    public static final int RV_ITEM_VOUCHER = 1;
    public static final int RV_ITEM_NEW = 2;
    public static final int RV_ITEM_NORMAL = 3;
    public static final int SERVICE_ADD = 1;
    public static final int SERVICE_CHECKBOX = 2;
    public static final double LATITUDE_DEFAULT = 10.7826525;
    public static final double LONGTITUDE_DEFAULT = 106.6678123;
    public static final float ZOOM_DEFAULT = 13;
    public static String TOKEN = "";
    public static final int MORNING = 1;
    public static final int AFTERNOON = 2;
    public static final int EVENING = 3;

    public static final int[] SALON_IMAGE_IDS = {
            R.drawable.salon1,
            R.drawable.salon2,
            R.drawable.salon3,
            R.drawable.salon4,
            R.drawable.salon5,
            R.drawable.salon6,
            R.drawable.salon7,
            R.drawable.salon8,
            R.drawable.salon9,
            R.drawable.salon10,

    };

    public static HashMap<Integer, Salon> SalonList = new HashMap<>();
    public static Customer Customer;

    public static List<Salon> getPromotions(){
        List<Salon> promotions = new ArrayList<>();
        for (Salon salon:
             new ArrayList<Salon>(SalonList.values())) {

            if(salon.getPromotion() != null){
                promotions.add(salon);
            }

        }
        return promotions;
    }

    public static <T> ArrayList<T> toList(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        for (T item :
                array) {
            list.add(item);
        }
        return list;
    }





}
