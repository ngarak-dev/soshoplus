/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.chip.Chip;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.photoMulti;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.utils.glide.glideImageLoader;
import com.yds.library.IMultiImageLoader;
import com.yds.library.MultiImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class MultiImage extends BaseItemProvider<post> {
    
    private static String TAG = "MULTI IMAGE POST : ";
    
    SimpleDraweeView profile_pic;
    TextView full_name, time_ago, contents,  no_likes, no_comments;
    MultiImageView post_multi_image;
    ProgressBar progressBar;
    Chip likes, comment, post_option;
    
    @Override
    public int getItemViewType () {
        return post.MULTI_IMAGE_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.multi_image_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
    
        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        full_name = baseViewHolder.findView(R.id.full_name);
        time_ago = baseViewHolder.findView(R.id.time_ago);
    
        no_likes = baseViewHolder.findView(R.id.no_likes);
        no_comments = baseViewHolder.findView(R.id.no_comments);
    
        contents = baseViewHolder.findView(R.id.post_contents);
        post_multi_image = baseViewHolder.findView(R.id.post_multi_image);
        progressBar = baseViewHolder.findView(R.id.progressBar);
    
        likes = baseViewHolder.findView(R.id.like_btn);
        comment = baseViewHolder.findView(R.id.comment_btn);
        post_option = baseViewHolder.findView(R.id.post_option);
    
        Observable.fromArray(post).subscribe(new Consumer<post>() {
            @Override
            public void accept (post post) throws Throwable {
                full_name.setText(post.getPublisherInfo().getName());
                time_ago.setText(post.getPostTime());
                no_likes.setText(post.getPostLikes() + " likes");
                no_comments.setText(post.getPostComments() + " comments");
            
                if (post.getPostTextAPI().isEmpty()) {
                    contents.setVisibility(View.GONE);
                }
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            
                /*getting if post is liked*/
                if (post.isLiked()) {
                    likes.setChipIconResource(R.drawable.ic_liked);
                    likes.setCheckedIconTintResource(R.color.colorPrimary);
                    likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            
                /*bind profile pic*/
                profile_pic.setImageURI(post.getPublisherInfo().getAvatar());
            
                /*.......*/
                List<photoMulti> photos = post.getPhotoMulti();
                List<String> imageList = new ArrayList<>();
                for(photoMulti multi_photos : new Iterable<photoMulti>() {
                    @NonNull
                    @Override
                    public Iterator<photoMulti> iterator () {
                        return photos.iterator();
                    }
                }) {
                
                    imageList.add(multi_photos.getImage());
                    post_multi_image.setImagesData(imageList);
                    post_multi_image.setGap(5);
                    post_multi_image.setShowText(true);
                    post_multi_image.setMaxSize(2);
                
                    post_multi_image.setMultiImageLoader(new IMultiImageLoader() {
                        @Override
                        public void load (Context context, Object url, ImageView imageView) {
                            Glide.with(context).load(url).into(imageView);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            
                post_multi_image.setOnItemImageClickListener(new MultiImageView.OnItemImageClickListener() {
                    @Override
                    public void onItemImageClick (Context context, ImageView imageView, int index, List list) {
                        new XPopup.Builder(getContext()).asImageViewer(imageView,
                                index,
                                list, new OnSrcViewUpdateListener() {
                                    @Override
                                    public void onSrcViewUpdate(@NotNull ImageViewerPopupView popupView, int position) {
//                            popupView.updateSrcView((ImageView) recyclerView.getChildAt(position));
                                    }
                                }, new fullImageLoader())
                                .show();
                        /*TODO create layout for this*/
                    }
                });
            }
        }).dispose();
    
        /*getting if post is liked*/
        if (post.isLiked()) {
            likes.setChipIconResource(R.drawable.ic_liked);
            likes.setCheckedIconTintResource(R.color.colorPrimary);
            likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
    }
    
    /*full screen image view*/
    static class fullImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage (int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }
        
        @Override
        public File getImageFile (@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
