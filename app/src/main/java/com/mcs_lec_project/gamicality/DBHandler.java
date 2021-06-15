package com.mcs_lec_project.gamicality;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class DBHandler {

    DBHelper dbhelper;


    public DBHandler(Context context) {
        dbhelper = new DBHelper(context);
    }


    public void insertuser(String username,String password,String email)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.execSQL("insert into users(username,password,email) values ('"+username+"' , '"+password+"', '"+email+"')");

    }

    public ArrayList<User> getuserlist()
    {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<User> userlist = new ArrayList<User>();

        if(db!=null)
        {
            cursor = db.rawQuery("select * from " +"Users",null);
        }
        cursor.moveToFirst();
        while(cursor.moveToNext())
        {
            User user = new User();
            user.Userid = cursor.getInt(0);
            user.Username = cursor.getString(1);
            user.password = cursor.getString(2);
            userlist.add(user);
        }

        return userlist;
    }

}
