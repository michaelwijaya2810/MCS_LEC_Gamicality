package com.mcs_lec_project.gamicality;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    //pake Post atau Home?
    ArrayList<Post> postList;
    Context context;
    DBHandler dbhandler;
    int currentuser;
    int postid;
    String username;
    Intent intent;
    public ProfileAdapter(Context context, ArrayList<Post> postList,int currentuser){
        this.context = context;
        this.postList = postList;
        this.currentuser = currentuser;
    }

    @NonNull
    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View profileView = inflater.inflate(R.layout.item_post_options_menu, parent, false);
        dbhandler = new DBHandler(context);
        User user = dbhandler.getauthorfrompost(currentuser);

        username = user.getUsername();


        return new ProfileViewHolder(profileView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileViewHolder holder, int position) {
        //get post information from DB (username, post date, post title)
        //setText all TextView
        holder.tvUsername.setText(username);
        holder.tvTitle.setText(postList.get(position).getTitle());
        holder.tvDate.setText(postList.get(position).getPostDate());
        postid = postList.get(position).getPostId();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to PostDetailActivity when user click on the post in the list
                intent = new Intent(context,PostDetailActivity.class);
                intent.putExtra("userid",currentuser);
                intent.putExtra("postid",postList.get(position).getPostId());
                intent.putExtra("gameid",postList.get(position).getGameId());
                context.startActivity(intent);
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btnMore);
                popupMenu.inflate(R.menu.menu_bookmark_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.bm_menu_share){
                            Toast.makeText(context, "Successfully shared", Toast.LENGTH_SHORT).show();
                            return true;
                        }else if(menuItem.getItemId() == R.id.bm_menu_remove){
                            //remove post from user's post list
                            dbhandler.removepost(currentuser,postid);
                            postList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Post removed!", Toast.LENGTH_SHORT).show();
                            return true;
                        }else if(menuItem.getItemId() == R.id.bm_menu_report){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Report this post?");
                            builder.setPositiveButton("REPORT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Report submitted", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            builder.create();
                            builder.show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername;
        TextView tvDate;
        TextView tvTitle;
        ImageButton btnMore;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTitle = itemView.findViewById(R.id.tv_title);
            btnMore = itemView.findViewById(R.id.btn_more1);
        }
    }
}
