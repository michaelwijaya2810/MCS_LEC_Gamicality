package com.mcs_lec_project.gamicality;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    //table game
    public static final String table_game = "Games";
    public static final String field_game_id = "gameid";
    public static final String field_game_title = "title";
    public static final String field_game_image = "image";

    private static final String create_table_game = "Create table if not Exists "+table_game+" " +
            "( "+field_game_id+" integer primary key autoincrement," +
            " "+field_game_title+" TEXT," +
            " "+field_game_image+" BLOB" +
            ")  ";


    //table post
    public static final String table_Post = "Posts";
    public static final String field_post_id = "postid";
    public static final String field_post_useridFK = "userid";
    public static final String field_post_gameidFK = "gameid";
    public static final String field_post_title = "title";
    public static final String field_post_body = "body";
    public static final String field_post_postDate = "postdate";

    private static final String create_table_post = "create table if not exists  "+table_Post+" (" +
            " "+field_post_id+" integer primary key autoincrement," +
            " "+field_post_useridFK+" integer," +
            " "+field_post_gameidFK+" integer," +
            " "+field_post_title+" TEXT," +
            " "+field_post_body+" TEXT," +
            " "+field_post_postDate+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" +
            ")  ";

    // table bookmark
    public static final String table_Bookmark = "bookmarklist";
    public static final String field_Bookmark_userid = "userid";
    public static final String field_Bookmark_postid = "postid";

    private static final String create_table_bookmark = "create table if not exists "+table_Bookmark+"("+field_Bookmark_userid+" integer ,"+field_Bookmark_postid+" integer," +
            "Foreign key (userid) references Users(Userid), Foreign key(postid) references Posts(postid)) ";

    //table reply
    public static final String table_Replies = "Replies";
    public static final String field_Replies_userid = "userid";
    public static final String field_Replies_postid = "postid";
    public static final String field_replies_postDate = "postdate";
    public static final String field_Replies_body = "body";

    private static final String create_table_Replies = "create table if not exists "+table_Replies+" ("+field_Replies_userid+" integer ,"+field_Replies_postid+" integer," +
            ""+field_Replies_body+" Text,  "+field_replies_postDate+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,   " +
            "Foreign key (userid) references Users(Userid), Foreign key(postid) references Posts(postid))";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(create_table_user);
        db.execSQL(create_table_game);
        db.execSQL(create_table_post);
        db.execSQL(create_table_bookmark);
        db.execSQL(create_table_Replies);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
