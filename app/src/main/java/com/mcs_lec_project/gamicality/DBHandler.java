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
import android.util.Log;
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

        cursor.close();
        return post;
    }

    public void removepost(int userid,int postid)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.execSQL("delete from Posts where userid ='"+userid+"' and postid = '"+postid+"'");
    }

    public Game getgamefrompost(int gameid)
    {
        Game game = new Game();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("select * from Games where gameid == '"+gameid+"'  ",null);
        cursor.moveToFirst();
        game.setTitle(cursor.getString(1));
        game.setGameid(cursor.getInt(0));

        cursor.close();
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

    public boolean addbookmark(int userid,int postid, Context context)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("select * from bookmarklist where userid = '"+userid+"' and postid = '"+postid+"' ",null);
        cursor.moveToFirst();

        if(cursor.getCount()>0 &&cursor.getInt(0) == userid && cursor.getInt(1)== postid)
        {
          cursor.close();
          return false;
        }
        else
        {
            db.execSQL("insert into Bookmarklist(userid,postid) values('"+userid+"', '"+postid+"')");
            cursor.close();
            return true;
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

        check = db.rawQuery("select count(userid) from Replies where postid = '"+postid+"'",null);
        check.moveToFirst();
        if (check.getInt(0) > 0) {
            Cursor cursor;

            cursor = db.rawQuery("select * from Replies where postid == '" + postid + "'  ", null);
            cursor.moveToFirst();

            do {

                ReplyPost replypost = new ReplyPost();
                replypost.setUserId(cursor.getInt(0));
                replypost.setBody(cursor.getString(2));
                replypost.setReplyDate(cursor.getString(3));
                replylist.add(replypost);
            }
            while (cursor.moveToNext());

            cursor.close();
        }
        check.close();
        return replylist;
    }

    public ArrayList<Bookmark> getbookmarklist(int userid)
    {
        ArrayList<Bookmark> bookmarklist = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor check;
        check = db.rawQuery("select count(userid) from bookmarklist where userid == '"+userid+"'",null);
        check.moveToFirst();

        if (check.getInt(0) > 0) {
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

            cursor.close();
        }
        check.close();
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

        if (check.getInt(0) > 0) {
            Cursor cursor;

            cursor = db.rawQuery("select * from Replies join Posts on Replies.postid = Posts.postid where Replies.userid != " + userid + " and '" + userid + "' = Posts.userid", null);
            cursor.moveToFirst();

            do {
                Post post = getpost(cursor.getInt(1));
                Notification notification = new Notification();
                notification.setId(cursor.getInt(0));
                notification.setContent(post.getBody());
                notificationlist.add(notification);
            }
            while (cursor.moveToNext());

            cursor.close();
        }
        check.close();
        return notificationlist;
    }

    public ArrayList<Home> getgamepostlist(int gameid)
    {
        ArrayList<Home> postlist = new ArrayList<>();

        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor check;
        check = db.rawQuery("select count(postid) from Posts where gameid == '"+gameid+"'",null);
        check.moveToFirst();

        if (check.getInt(0) > 0) {
            Cursor cursor;

            cursor = db.rawQuery("select * from Posts where gameid == '" + gameid + "'  ", null);
            cursor.moveToFirst();

            do {
                Home post = new Home();
                //postid
                post.setId(cursor.getInt(0));
                //authorid
                post.setAuthorid(cursor.getInt(1));
                //title
                post.setName(cursor.getString(3));
                //date
                post.setDate(cursor.getString(5));
                postlist.add(post);

            }
            while (cursor.moveToNext());

            cursor.close();
        }
        check.close();
        return postlist;
    }

    public ArrayList<Game> getgamelist()
    {
        ArrayList<Game> gamelist = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("select * from Games",null);

        cursor.moveToFirst();

        do {
            Game game = new Game();
            game.setGameid(cursor.getInt(0));
            game.setTitle(cursor.getString(1));
            game.setImageId(cursor.getInt(2));
            gamelist.add(game);
        }
        while(cursor.moveToNext());

        cursor.close();
        return gamelist;
    }

    public User getcurrentuser(int userid)
    {
        User user = new User();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("select * from Users where Userid = '"+userid+"'",null);
        cursor.moveToFirst();
        user.setUsername(cursor.getString(1));
        user.setDOB(cursor.getString(3));
        user.setEmail(cursor.getString(4));

        return user;
    }

    public int getuserpostcount(int userid)
    {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        int postcount=0;

        Cursor cursor;

        cursor = db.rawQuery("select count(userid) from Posts where userid = '"+userid+"'",null);
        cursor.moveToFirst();
        postcount = cursor.getInt(0);

        return postcount;
    }

    public ArrayList<Post> getuserpostlist(int userid)
    {
        ArrayList<Post> postlist = new ArrayList<>();

        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor check;
        check = db.rawQuery("select count(userid) from Posts where userid == '"+userid+"'",null);
        check.moveToFirst();

        if (check.getInt(0) > 0) {
            Cursor cursor;

            cursor = db.rawQuery("select * from Posts where userid == '" + userid + "'  ", null);
            cursor.moveToFirst();

            do {
                Post post = new Post();
                post.setPostId(cursor.getInt(0));
                post.setUserId(cursor.getInt(1));
                post.setGameId(cursor.getInt(2));
                post.setTitle(cursor.getString(3));
                post.setPostDate(cursor.getString(5));

                postlist.add(post);

            }
            while (cursor.moveToNext());
        }



        return postlist;
    }


    public User getuserinformation(int userid)
    {
        User user = new User();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from Users where userid = '"+userid+"'",null);
        cursor.moveToFirst();
        user.setUserid(userid);
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setDOB(cursor.getString(3));
        user.setEmail(cursor.getString(4));

        return user;
    }

    public void edituserprofile(String username, String password, String email , int userid)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.execSQL("update Users set username = '"+username+"' , password = '"+password+"', Email = '"+email+"' where Userid = '"+userid+"'");
    }

}
