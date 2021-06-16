package com.mcs_lec_project.gamicality;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

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

        do {
            if(db!=null)
            {
                User user = new User();
                user.setUserid(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                userlist.add(user);
            }

        }
        while(cursor.moveToNext());


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
        cursor.close();
        return user;
    }

    public void addbookmark(int userid,int postid, Context context)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("select * from bookmarklist where userid = '"+userid+"' and postid = '"+postid+"' ",null);
        cursor.moveToFirst();

        if(cursor.getCount()>0 &&cursor.getInt(0) == userid && cursor.getInt(1)== postid)
        {

          Toast.makeText(context,"already bookmarked",Toast.LENGTH_SHORT).show();
        }
        else
        {
            db.execSQL("insert into Bookmarklist(userid,postid) values('"+userid+"', '"+postid+"')");
        }


    }

    public void addreply(int userid,int postid,String body)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.execSQL("insert into Replies(userid,postid,body) Values ('"+userid+"', '"+postid+"', '"+body+"')");
    }

    public ArrayList<ReplyPost> getreplylist(int postid)
    {
        ArrayList<ReplyPost> replylist = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor check;


        check = db.rawQuery("select count(userid) from Replies",null);
        check.moveToFirst();
        if(check.getInt(0)<=0)
        {
            check.close();
            return replylist;
        }
        else
        {
            Cursor cursor;

            cursor = db.rawQuery("select * from Replies where postid == '"+postid+"'  ",null);
            cursor.moveToFirst();

            do {

                ReplyPost replypost = new ReplyPost();
                replypost.setUserId(cursor.getInt(0));
                replypost.setBody(cursor.getString(2));
                replypost.setReplyDate(cursor.getString(3));
                replylist.add(replypost);
            }
            while(cursor.moveToNext());

            check.close();
            cursor.close();
            return replylist;
        }
    }

    public ArrayList<Bookmark> getbookmarklist(int userid)
    {
        ArrayList<Bookmark> bookmarklist = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();


        Cursor check;
        check = db.rawQuery("select count(userid) from bookmarklist where userid == '"+userid+"'",null);
        check.moveToFirst();



        if(check.getInt(0)<=0)
        {
            check.close();
            return bookmarklist;
        }
        else {
            Cursor cursor;

            cursor = db.rawQuery("select * from bookmarklist where userid == '" + userid + "'  ", null);
            cursor.moveToFirst();

            do {

                Bookmark bookmark = new Bookmark();
                bookmark.setUserid(cursor.getInt(0));
                bookmark.setPostid(cursor.getInt(1));
                bookmarklist.add(bookmark);
            }
            while (cursor.moveToNext());
            check.close();
            cursor.close();
        }
        return bookmarklist;
    }



    public void removebookmark(int userid,int postid)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.execSQL("delete from bookmarklist where userid == '"+userid+"' and postid=='"+postid+"'");
    }

    public ArrayList<Notification> getnotificationlist(int userid)
    {
        ArrayList<Notification> notificationlist= new ArrayList<>();

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor check;
        check = db.rawQuery("select count(Replies.userid) from Replies join Posts on Replies.postid = Posts.postid where Replies.userid != "+userid+" and '"+userid+"' = Posts.userid "  ,null);
        check.moveToFirst();



        if(check.getInt(0)<=0)
        {
            check.close();
            return notificationlist;
        }
        else {
            Cursor cursor;

            cursor = db.rawQuery("select * from Replies join Posts on Replies.postid = Posts.postid where Replies.userid != "+userid+" and '"+userid+"' = Posts.userid", null);
            cursor.moveToFirst();

            do {
                Post post = getpost(cursor.getInt(1));
                Notification notification = new Notification();
                notification.setId(cursor.getInt(0));
                notification.setContent(post.getBody());
                notificationlist.add(notification);
            }
            while (cursor.moveToNext());
            check.close();
            cursor.close();
        }


        return notificationlist;
    }
}
