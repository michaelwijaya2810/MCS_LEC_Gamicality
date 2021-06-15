package com.mcs_lec_project.gamicality;

import java.util.ArrayList;

public class Post {
    int postId;
    int userId;
    int gameId;
    String title;
    String body;
    int likeCount;
    ArrayList<ReplyPost> replyList;
    String postDate;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public ArrayList<ReplyPost> getReplyList() {
        if(replyList.isEmpty()){
            replyList = new ArrayList<>();
        }
        return replyList;
    }

    public void setReplyList(ArrayList<ReplyPost> replyList) {
        this.replyList = replyList;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
