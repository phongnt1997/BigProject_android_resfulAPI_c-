package com.salon.cattocdi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.salon.cattocdi.models.Customer;

import java.sql.SQLException;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public boolean isLogin(){
        String[] cols = new String[] {DatabaseHelper.COL_IS_LOGIN};
        Cursor cursor = database.query(DatabaseHelper.TABLE_PROFILE,cols, null ,null, null, null, null );
        if(cursor != null){
            return cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_IS_LOGIN)) > 0;
        }
        return false;
    }

    public Customer getProfile(){
        Customer customer = new Customer();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PROFILE + " WHERE " + DatabaseHelper.COL_ID + "=1", null);
        customer.setToken(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TOKEN)));
        customer.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRSTNAME)));
        customer.setLastname(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LASTNAME)));
        customer.setPhone(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE)));
        customer.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL)));
        return customer;
    }

    public String getToken(){
        Cursor cursor = database.rawQuery("SELECT " + DatabaseHelper.COL_TOKEN + " FROM " + DatabaseHelper.TABLE_PROFILE + " WHERE " + DatabaseHelper.COL_ID+"=1", null);
        return cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TOKEN));
    }

    public boolean updataProfile(Customer customer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_TOKEN, customer.getToken());
        contentValues.put(DatabaseHelper.COL_FIRSTNAME, customer.getName());
        contentValues.put(DatabaseHelper.COL_LASTNAME, customer.getLastname());
        contentValues.put(DatabaseHelper.COL_PHONE, customer.getPhone());
        contentValues.put(DatabaseHelper.COL_EMAIL, customer.getEmail());
        contentValues.put(DatabaseHelper.COL_IS_LOGIN, 1);
        return database.update(DatabaseHelper.TABLE_PROFILE, contentValues, DatabaseHelper.COL_ID +"=1",null) > 0;


    }

}
