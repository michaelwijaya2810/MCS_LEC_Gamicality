package com.mcs_lec_project.gamicality;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameListAdapter  extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
    ArrayList<Game> Games = new ArrayList<Game>();
    Context context;
    Intent intent;
    int curuser;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.item_game_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int imageId = Games.get(position).getImageId();
        String name = Games.get(position).getTitle();
        int gameid = Games.get(position).getGameid();
        holder.iv_profile_picture.setImageResource(imageId);
        holder.tv_name.setText(name);


        //Ketika post di kilk
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(context, PostIndexActivity.class);
            intent.putExtra("userid",curuser);
            intent.putExtra("gameid",gameid);
            context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return Games.size();
    }

    public void setData(ArrayList<Game> Games, Context context,int curuser) {
        this.Games = Games;
        this.context = context;
        this.curuser = curuser;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile_picture;
        TextView tv_name;
        ImageButton btn_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_profile_picture = itemView.findViewById(R.id.iv_profile_picture_gm);
            tv_name = itemView.findViewById(R.id.tv_game_title);
            btn_more = itemView.findViewById(R.id.btn_more1);

        }
    }
}
