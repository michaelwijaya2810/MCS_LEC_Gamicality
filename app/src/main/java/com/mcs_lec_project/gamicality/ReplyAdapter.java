package com.mcs_lec_project.gamicality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>{
    private final ArrayList<ReplyPost> replyList;
    DBHandler dbhandler;
    Context context;
    public ReplyAdapter(ArrayList<ReplyPost> replyList){
        this.replyList = replyList;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View replyView = layoutInflater.inflate(R.layout.item_post_reply, parent, false);
        context = parent.getContext();
        dbhandler = new DBHandler(context);
        return new ReplyViewHolder(replyView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ReplyViewHolder holder, int position) {
        //get author as User object from Reply's UserId?
        User author = dbhandler.getauthorfrompost(replyList.get(position).getUserId());
//        User author = new User();
        //get author's username
        holder.tvUsername.setText(author.getUsername());
        ReplyPost replyPost = replyList.get(position);
        holder.tvReplyDate.setText(replyPost.getReplyDate());
        holder.tvReplyBody.setText(replyPost.getBody());

    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername;
        TextView tvReplyDate;
        TextView tvReplyBody;
        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvReplyDate = itemView.findViewById(R.id.tv_reply_date);
            tvReplyBody = itemView.findViewById(R.id.tv_reply_body);
        }
    }
}
