/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.following;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class friendsFollowingAdapter extends RecyclerView.Adapter<friendsFollowingAdapter.FriendsHolder> {
    
    private List<following> followingList;
    private Context context;
    private final onFriendClickListener friendClickListener;
    private static String TAG = "Friends";
    
    public friendsFollowingAdapter (Context context , List<following> followings,
                                    onFriendClickListener friendClickListener) {
        this.context = context;
        this.followingList = followings;
        this.friendClickListener = friendClickListener;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public friendsFollowingAdapter.FriendsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_list_row, parent, false);
        return new FriendsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull friendsFollowingAdapter.FriendsHolder holder, int position) {
       /*following*/
        /*bind items and set onclick listener*/
        holder.bind(followingList.get(position), friendClickListener);
    }
    
    @Override
    public int getItemCount () {
        return followingList.size();
    }
    
    public class FriendsHolder extends RecyclerView.ViewHolder{
        
        ImageView profile_pic;
        TextView full_name;
        
        public FriendsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
        }
    
        public void bind (following following, onFriendClickListener friendClickListener) {
            full_name.setText(following.getName());
            Picasso.get().load(following.getAvatar()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(profile_pic, new Callback() {
                @Override
                public void onSuccess () {
                    Log.d(TAG, "onSuccess: " + "Image loaded");
                }
        
                @Override
                public void onError (Exception e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                    profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                }
            });
            
            /*on friend Click*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    friendClickListener.onFriendClick(following);
                }
            });
        }
    }
    
    public interface onFriendClickListener {
        void onFriendClick (following following);
    }
}
