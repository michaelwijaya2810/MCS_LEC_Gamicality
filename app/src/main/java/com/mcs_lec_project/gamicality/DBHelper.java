package com.mcs_lec_project.gamicality;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "GamicalityDB", null, 1);
    }

    //table user
    public static final String table_user = "Users";
    public static final String field_user_id = "Userid";
    public static final String field_user_name = "username";
    public static final String field_user_password = "password";
    public static final String field_user_DOB = "DOB";
    public static final String field_user_Email = "Email";

    private static final String create_table_user = "Create table if not Exists "+table_user+"" +
            "( "+field_user_id+" integer primary key autoincrement," +
            " "+field_user_name+" TEXT," +
            " "+field_user_password+" TEXT," +
            " "+field_user_DOB+" TEXT," +
            " "+field_user_Email+" TEXT" +
            ")  ";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
