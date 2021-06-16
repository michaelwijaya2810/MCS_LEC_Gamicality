package com.mcs_lec_project.gamicality;

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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    ArrayList<Bookmark> bookmarklist;
    Context context;
    OnBookmarkListener listener;
    DBHandler dbhandler;
    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        dbhandler = new DBHandler(context);


        View view = LayoutInflater.from(context).inflate(R.layout.bookmark_list, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int imageId = bookmarklist.get(position).getImageId();
        Post post = dbhandler.getpost(bookmarklist.get(position).getPostid());


//        holder.iv_profile_picture.setImageResource(imageId);
        holder.tv_content.setText(post.getBody());

        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn_more);
                popupMenu.inflate(R.menu.more_options);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.more_share:
                                Toast.makeText(context, "Successfully shared", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.more_remove:
                                dbhandler.removebookmark(bookmarklist.get(position).getUserid(),bookmarklist.get(position).getPostid());
                                bookmarklist.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, bookmarklist.size());

                                break;
                            case R.id.more_report:
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
                                break;
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
        return bookmarklist.size();
    }

    public void setData(ArrayList<Bookmark> bookmarks, Context context, OnBookmarkListener listener){
        this.bookmarklist = bookmarks;
        this.context = context;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_profile_picture;
        TextView tv_content;
        OnBookmarkListener listener;
        ImageButton btn_more;

        public ViewHolder(@NonNull View itemView, OnBookmarkListener listener) {
            super(itemView);

            iv_profile_picture = itemView.findViewById(R.id.iv_profile_picture_bm);
            tv_content = itemView.findViewById(R.id.tv_content_bm);
            this.listener = listener;
            btn_more = itemView.findViewById(R.id.btn_more1);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnBookmarkClick(getAdapterPosition());
        }
    }

    public interface OnBookmarkListener{
        void OnBookmarkClick(int position);
    }
}