package com.pro.salon.cattocdi.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.pro.salon.cattocdi.models.Appointment;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.models.enums.AppointmentStatus;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class MyContants {
   public static String TOKEN = "";
    public static final String BASE_URL = "http://192.168.43.217/cattocdi.api/";
    public static final String USERNAME_TEST = "salon";
    public static final String PASSWORD_TEST = "123";
    public static final int FRAGMENT_ABOVE = 1;
    public static final int FRAGMENT_BELOW = 2;
    public static final int APPOINTMENT_SMALL = 1;
    public static final int APPOINTMENT_FULL = 2;
    public static final int HOME_PAGE = 1;
    public static final int SCHEDULE_PAGE = 2;
    public static final int CLIENT_PAGE = 3;
    public static final int SIGNUP_PAGE = 4;
    public static final int MANAGER_SERVICE_PAGE = 5;
    public static final int PROFILE_PAGE = 6;
    public static final int PREVIEW_PAGE = 7;
    public static int CAPACITY = 0;


}
