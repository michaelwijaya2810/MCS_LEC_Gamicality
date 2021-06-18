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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    ArrayList<Home> homes = new ArrayList<>();
    Context context;
    DBHandler dbhandler;
    int currentuser;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_options_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int imageId = homes.get(position).getImageId();
        dbhandler = new DBHandler(context);
        User author = dbhandler.getauthorfrompost(homes.get(position).getAuthorid());
        String name = homes.get(position).getName();
        String date = homes.get(position).getDate();

//        holder.iv_profile_picture.setImageResource(imageId);
        holder.tv_name.setText(author.getUsername());
        holder.tv_date.setText(date);
        holder.tv_title.setText(name);



    //Ketika post di kilk
        holder.itemView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("userid",currentuser);
            intent.putExtra("postid",homes.get(position).getId());
            context.startActivity(intent);
            ((Activity)context).finish();
        }
     });

        //option menu di post
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn_more);
                popupMenu.inflate(R.menu.menu_post_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.post_menu_share){
                            Toast.makeText(context, "Successfully shared", Toast.LENGTH_SHORT).show();
                            return true;
                        }else if(menuItem.getItemId() == R.id.post_menu_bookmark){
                            //QC dong apa ini logic nya udh bener atau nggak? thx (MW)
                            //harusnya
                            if(dbhandler.addbookmark(currentuser, homes.get(position).getId(),context)){
                                Toast.makeText(context, "Post bookmarked!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context,"Post already bookmarked",Toast.LENGTH_SHORT).show();
                            }

                            return true;
                        }else if(menuItem.getItemId() == R.id.post_menu_report){
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
        return homes.size();
    }

    public void setData(ArrayList<Home> homes, Context context,int currentuser){
        this.homes = homes;
        this.context = context;
        this.currentuser = currentuser;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile_picture;
        TextView tv_name;
        TextView tv_date;
        TextView tv_title;
        ImageButton btn_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_picture = itemView.findViewById(R.id.iv_profile_picture_hm);
            tv_name = itemView.findViewById(R.id.tv_username);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            btn_more = itemView.findViewById(R.id.btn_more1);
        }
    }
}
