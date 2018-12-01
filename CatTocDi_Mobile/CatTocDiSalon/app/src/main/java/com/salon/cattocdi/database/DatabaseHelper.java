package com.salon.cattocdi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StyleViet.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PROFILE = "Profile";

    public static final String COL_ID = "ID";
    public static final String COL_TOKEN = "Token";
    public static final String COL_FIRSTNAME = "Firstname";
    public static final String COL_LASTNAME = "Lastname";
    public static final String COL_PHONE = "Phone";
    public static final String COL_EMAIL = "Email";
    public static final String COL_IS_LOGIN = "IsLogin";

    private static final String CREATE_TABLE_PROFILE =
            "CREATE TABLE " + TABLE_PROFILE + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TOKEN + " TEXT, " +
                    COL_FIRSTNAME + " TEXT, " +
                    COL_LASTNAME + " TEXT, " +
                    COL_PHONE + " TEXT, " +
                    COL_EMAIL + " TEXT, " +
                    COL_IS_LOGIN + " INTEGER" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILE);

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TOKEN, "");
        contentValues.put(COL_FIRSTNAME, "");
        contentValues.put(COL_LASTNAME, "");
        contentValues.put(COL_PHONE, "");
        contentValues.put(COL_EMAIL, "");
        contentValues.put(COL_IS_LOGIN, 0);
        database.insert(TABLE_PROFILE, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
}
