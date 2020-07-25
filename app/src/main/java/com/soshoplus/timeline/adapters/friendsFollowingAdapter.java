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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.followers;
import com.soshoplus.timeline.models.friends.following;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class friendsFollowingAdapter extends BaseQuickAdapter<following, BaseViewHolder> {
    
    private static String TAG = "Friends";
    
    public friendsFollowingAdapter (int layoutResId, @Nullable List<following> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder,
                            following followings) {
    
        if (followings == null) {
            return;
        }
    
        baseViewHolder.setText(R.id.full_name, followings.getName());
    
        SimpleDraweeView profile_pic = baseViewHolder.findView(R.id.profile_pic);
        profile_pic.setImageURI(followings.getAvatar());
    }

//    private List<following> followingList;
//    private Context context;
//    private final onFriendClickListener friendClickListener;

//
//    public friendsFollowingAdapter (Context context , List<following> followings,
//                                    onFriendClickListener friendClickListener) {
//        this.context = context;
//        this.followingList = followings;
//        this.friendClickListener = friendClickListener;
//    }
//
//    /*inflating and initializing a view*/
//    @NonNull
//    @Override
//    public friendsFollowingAdapter.FriendsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.friends_list_row, parent, false);
//        return new FriendsHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder (@NonNull friendsFollowingAdapter.FriendsHolder holder, int position) {
//       /*following*/
//        /*bind items and set onclick listener*/
//        holder.bind(followingList.get(position), friendClickListener, context);
//    }
//
//    @Override
//    public int getItemCount () {
//        return followingList.size();
//    }
//
//    static class FriendsHolder extends RecyclerView.ViewHolder{
//
//        ImageView profile_pic;
//        TextView full_name;
//
//        public FriendsHolder (@NonNull View itemView) {
//            super(itemView);
//            profile_pic = itemView.findViewById(R.id.profile_pic);
//            full_name = itemView.findViewById(R.id.full_name);
//        }
//
//        public void bind (following following, onFriendClickListener friendClickListener, Context context) {
//
//            Observable.fromArray(following).subscribe(new Consumer<following>() {
//                @Override
//                public void accept (following following) throws Throwable {
//                    full_name.setText(following.getName());
//                    Glide.with(context).load(following.getAvatar()).placeholder(R.drawable.ic_image_placeholder)
//                            .thumbnail(0.5f).into(profile_pic);
//                }
//            }).dispose();
//
//            /*on friend Click*/
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick (View v) {
//                    friendClickListener.onFriendClick(following);
//                }
//            });
//        }
//    }
//
//    public interface onFriendClickListener {
//        void onFriendClick (following following);
//    }
}
