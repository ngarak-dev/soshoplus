/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.text.util.Linkify;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.photoMulti;
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.noties.markwon.Markwon;
import io.noties.markwon.linkify.LinkifyPlugin;

public class MultiImage extends BaseItemProvider<post> {

    private static String TAG = "MULTI IMAGE POST : ";

    ImageView profile_pic;
    MaterialButton like;
    SocialTextView post_contents;

    @Override
    public int getItemViewType() {
        return post.MULTI_IMAGE_POST;
    }

    @Override
    public int getLayoutId() {
        return R.layout.multi_image_post_list_row;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {

        ImageLoader imageLoader = Coil.imageLoader(getContext());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);

        like = baseViewHolder.findView(R.id.like_btn);

        post_contents = baseViewHolder.findView(R.id.post_contents);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.like_btn, post.getPostLikes());
        baseViewHolder.setText(R.id.comment_btn, post.getPostComments());

        Markwon markwon = Markwon.builder(getContext())
                .usePlugin(LinkifyPlugin.create(
                        Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS
                ))
                .build();

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            markwon.setMarkdown(post_contents, post.getPostTextAPI());
        }

        /*bind profile pic*/
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }

        /*.......*/
        List<photoMulti> photos = post.getPhotoMulti();
        List<String> imageList = new ArrayList<>();
        for (photoMulti multi_photos : new Iterable<photoMulti>() {
            @NonNull
            @Override
            public Iterator<photoMulti> iterator() {
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
//    static class fullImageLoader implements XPopupImageLoader {
//        @Override
//        public void loadImage (int position, @NonNull Object uri, @NonNull ImageView imageView) {
//
//            ImageLoader imageLoader = Coil.imageLoader(imageView.getContext());
//            ImageRequest imageRequest = new ImageRequest.Builder(imageView.getContext())
//                    .data(uri)
//                    .placeholder(R.color.light_grey)
//                    .crossfade(true)
//                    .target(imageView)
//                    .build();
//            imageLoader.enqueue(imageRequest);
//        }
//
//        @Override
//        public File getImageFile (@NonNull Context context, @NonNull Object uri) {
//            try {
//                return Glide.with(context).downloadOnly().load(uri).submit().get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
