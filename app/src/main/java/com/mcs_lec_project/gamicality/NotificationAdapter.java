package com.mcs_lec_project.gamicality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    ArrayList<Notification> notifications = new ArrayList<Notification>();
    Context context;
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.notif_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        int imageId = notifications.get(position).getImageId();
        String content = notifications.get(position).getContent();

        holder.iv_profile_picture.setImageResource(imageId);
        holder.tv_content.setText(content);

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setData(ArrayList<Notification> notifications, Context context){
        this.notifications = notifications;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile_picture;
        TextView tv_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_profile_picture = itemView.findViewById(R.id.iv_profile_picture);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
