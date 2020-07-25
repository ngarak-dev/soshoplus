/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.ui.user_profile.userProfile;
import com.soshoplus.timeline.utils.xpopup.adFullImageViewPopup;
import com.soshoplus.timeline.utils.xpopup.fullImageViewPopup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class userPhotosAdapter extends BaseQuickAdapter<post, BaseViewHolder> {
    
    /*......*/
    private static String timeAgo, noLikes, noComments, fullName;
    private static boolean isLiked;
    
    /*......*/
    private static String adFullName, adLocation, adDescription, adHeadline;
    
    public userPhotosAdapter (int layoutResId, @Nullable List<post> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
    
        SimpleDraweeView imageView = baseViewHolder.findView(R.id.image);
    
        if (post.getPostType().equals("ad")) {
            imageView.setImageURI(post.getAdMedia());
        }
        else {
            imageView.setImageURI(post.getPostFileFull());
        }
        
        /*on click*/
        setOnItemClickListener((adapter, view, position) -> {

            Log.d("TAG", "convert: " + position);
            
            if (post.getPostType().equals("ad")) {
                /*show ad popup*/
                /*initializing popup*/
                adFullImageViewPopup imageViewPopup =
                        new adFullImageViewPopup(getContext());
                /*setting up*/
                imageViewPopup.setSingleSrcView(imageView, post.getAdMedia());
                imageViewPopup.isShowSaveButton(false);
                imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                    @Override
                    public void loadImage (int position, @androidx.annotation.NonNull Object uri,
                                           @androidx.annotation.NonNull ImageView imageView) {
                        Glide.with(imageView).load(uri).into(imageView);
                    }

                    @Override
                    public File getImageFile (@androidx.annotation.NonNull Context context,
                                              @androidx.annotation.NonNull Object uri) {
                        return null;
                    }
                });
                /*show popup*/
                new XPopup.Builder(getContext()).asCustom(imageViewPopup).show();

                /*......*/
                /*Converting Object to json data*/
                Gson gson = new Gson();
                String toJson = gson.toJson(post.getUserData());
                /*getting data from json string using pojo class*/
                userData user_data = gson.fromJson(toJson, userData.class);

                adFullName = user_data.getName();
                adLocation = post.getLocation();
                adDescription = post.getDescription();
                adHeadline = post.getHeadline();
            }
            else {
                /*initializing popup*/
                fullImageViewPopup imageViewPopup = new fullImageViewPopup(getContext());
                /*setting up*/
                imageViewPopup.setSingleSrcView(imageView, post.getPostFileFull());
                imageViewPopup.isShowSaveButton(false);
                imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                    @Override
                    public void loadImage (int position, @androidx.annotation.NonNull Object uri,
                                           @androidx.annotation.NonNull ImageView imageView) {
                        Glide.with(imageView).load(uri).into(imageView);
                    }

                    @Override
                    public File getImageFile (@androidx.annotation.NonNull Context context,
                                              @androidx.annotation.NonNull Object uri) {
                        return null;
                    }
                });
                /*show popup*/
                new XPopup.Builder(getContext()).asCustom(imageViewPopup).show();

                /*.......*/
                fullName = post.getName();
                timeAgo = post.getPostTime();
                noLikes = post.getPostLikes();
                noComments = post.getPostComments();
                isLiked = post.isLiked();
            }
        });
    }
    
    public static void getInfo (TextView full_name, TextView time_ago,
                                TextView no_likes, TextView no_comments,
                                MaterialButton like, MaterialButton comment) {
    
        /*setting up*/
        full_name.setText(fullName);
        time_ago.setText(timeAgo);
        no_likes.setText(noLikes + " likes");
        no_comments.setText(noComments + " comments");
    
        /*setting like btn*/
        if (isLiked) {
            like.setIconResource(R.drawable.ic_liked);
            like.setText("Liked");
        }
    }
    
    public static void getADInfo (TextView full_name, TextView location,
                                  TextView description, TextView headline) {
        
        /*setting up*/
        full_name.setText(adFullName);
        location.setText(adLocation);
        description.setText(Html.fromHtml(adDescription));
        headline.setText(adHeadline);
    }
//    /*.....*/
//    private final List<post> postList;
//    private Context context;
//    /*onclick Listener*/
//    private final onClickListener clickListener;
//
//    /*....*/
//    RequestOptions options = new RequestOptions()
//            .format(DecodeFormat.PREFER_RGB_565)
//            .placeholder(R.drawable.ic_image_placeholder)
//            .error(R.drawable.ic_image_placeholder)
//            .priority(Priority.LOW);
//
//    public userPhotosAdapter (List<post> postList, Context context, onClickListener clickListener) {
//        this.postList = postList;
//        this.context = context;
//        this.clickListener = clickListener;
//    }
//
//    @NonNull
//    @Override
//    public ImageHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,
//                parent,
//                false);
//        return new ImageHolder(view, clickListener);
//    }
//
//    @Override
//    public void onBindViewHolder (@NonNull ImageHolder holder, int position) {
//        holder.bindImages(postList.get(position), position,
//            clickListener, context);
//    }
//
//    @Override
//    public int getItemCount () {
//        return postList.size();
//    }
//
//    class ImageHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        public ImageHolder (View itemView, onClickListener clickListener) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.image);
//        }
//
//        public void bindImages (post post, int position, onClickListener clickListener, Context context) {
//            /*load image*/
//            if (post.getPostType().equals("ad")) {
//                Glide.with(context).load(post.getAdMedia())
//                        .apply(options).into(imageView);
//            }
//            else {
//                Glide.with(context).load(post.getPostFileFull())
//                        .apply(options).into(imageView);
//            }
//
//            /*click listener*/
//            if (post.getPostType().equals("ad")) {
//                /*show ad popup*/
//                imageView.setOnClickListener(view -> {
//                    clickListener.viewFullADImage(context, post, imageView);
//                });
//            }
//            else {
//                imageView.setOnClickListener(view -> {
//                    clickListener.viewFullImage(context, post, imageView);
//                });
//            }
//        }
//    }
//
//    /*onClick Interface*/
//    public interface onClickListener {
//        /*on image click*/
//        void viewFullImage (Context context, post post, ImageView imageView);
//        /*on ad image click*/
//        void viewFullADImage (Context context, post post, ImageView imageView);
//    }
}
