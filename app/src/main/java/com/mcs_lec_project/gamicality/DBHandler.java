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
            user.setUserid(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            userlist.add(user);
        }
        cursor.close();

        return userlist;
    }



    public void addPost(int userid,int gameid,String title, String body)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.execSQL("insert into Posts(userid,gameid,title,body) values('"+userid+"','"+gameid+"','"+title+"','"+body+"' )");
    }

    public Post getpost(int postid)
    {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Post post = new Post();
        Cursor cursor;

        cursor = db.rawQuery("select * from Posts where postid == '"+postid+"'",null);
        cursor.moveToFirst();
        post.setPostId(cursor.getInt(0));
        post.setUserId(cursor.getInt(1));
        post.setGameId(cursor.getInt(2));
        post.setTitle(cursor.getString(3));
        post.setBody(cursor.getString(4));
        post.setPostDate(cursor.getString(5));


        return post;
    }

    public Game getgamefrompost(int gameid)
    {
        Game game = new Game();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("select * from Games where gameid == '"+gameid+"'  ",null);
        cursor.moveToFirst();
        game.setTitle(cursor.getString(1));

        return game;
    }

    public User getauthorfrompost(int userid)
    {
        User user = new User();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("select * from Users where Userid == '"+userid+"'  ",null);
        cursor.moveToFirst();
        user.setUsername(cursor.getString(1));

        return user;
    }

    public void addbookmark(int userid,int postid)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.execSQL("insert into Bookmarklist(userid,postid) values('"+userid+"', '"+postid+"')");
    }



}
