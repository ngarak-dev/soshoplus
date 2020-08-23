/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.photoMulti;
import com.soshoplus.timeline.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiImage extends BaseItemProvider<post> {
    
    private static String TAG = "MULTI IMAGE POST : ";
    
    SimpleDraweeView profile_pic;
//    MultiImageView post_multi_image;
    MaterialButton like;
    
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
//        post_multi_image = baseViewHolder.findView(R.id.post_multi_image);

        like = baseViewHolder.findView(R.id.like_btn);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.no_likes, post.getPostLikes() + " likes");
        baseViewHolder.setText(R.id.no_comments, post.getPostComments() + " comments");

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            baseViewHolder.setText(R.id.post_contents, Html.fromHtml(post.getPostTextAPI()));
        }

        /*bind profile pic*/
        profile_pic.setImageURI(post.getPublisherInfo().getAvatar());

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }

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
//            post_multi_image.setImagesData(imageList);
//            post_multi_image.setGap(5);
//            post_multi_image.setShowText(true);
//            post_multi_image.setMaxSize(2);
//
//            post_multi_image.setMultiImageLoader(new IMultiImageLoader() {
//                @Override
//                public void load (Context context, Object url, ImageView imageView) {
//                    Glide.with(context).load(url).into(imageView);
//                }
//            });
        }

//        post_multi_image.setOnItemImageClickListener(new MultiImageView.OnItemImageClickListener() {
//            @Override
//            public void onItemImageClick (Context context, ImageView imageView, int index, List list) {
//                new XPopup.Builder(getContext()).asImageViewer(imageView,
//                        index,
//                        list, new OnSrcViewUpdateListener() {
//                            @Override
//                            public void onSrcViewUpdate(@NotNull ImageViewerPopupView popupView, int position) {
////                            popupView.updateSrcView((ImageView) recyclerView.getChildAt(position));
//                            }
//                        }, new fullImageLoader())
//                        .show();
//                /*TODO create layout for this*/
//            }
//        });
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
