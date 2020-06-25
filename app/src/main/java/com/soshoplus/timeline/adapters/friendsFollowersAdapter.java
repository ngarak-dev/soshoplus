/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.followers;

import java.util.List;

public class friendsFollowersAdapter extends RecyclerView.Adapter<friendsFollowersAdapter.FriendsHolder> {
    
    private List<followers> followersList;
    private Context context;
    private final onFriendClickListener friendClickListener;
    private static String TAG = "Friends";
    
    public friendsFollowersAdapter (Context context , List<followers> followers,
                                    onFriendClickListener friendClickListener) {
        this.context = context;
        this.followersList = followers;
        this.friendClickListener = friendClickListener;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public friendsFollowersAdapter.FriendsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_list_row, parent, false);
        return new FriendsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull friendsFollowersAdapter.FriendsHolder holder, int position) {
        /*following*/
//       following following_list =  followingList.get(position);

       /*followers*/
        /*bind items and set onclick listener*/
        holder.bind(followersList.get(position), friendClickListener, context);
    }
    
    @Override
    public int getItemCount () {
        return followersList.size();
    }
    
    static class FriendsHolder extends RecyclerView.ViewHolder{
        
        ImageView profile_pic;
        TextView full_name;
        
        public FriendsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
        }
    
        public void bind (followers followers, onFriendClickListener friendClickListener, Context context) {
            full_name.setText(followers.getName());
    
            Glide.with(context).load(followers.getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profile_pic);
            
            /*on friend Click*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    friendClickListener.onFriendClick(followers);
                }
            });
        }
    }
    
    public interface onFriendClickListener {
        void onFriendClick (followers followers);
    }
}
