/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.adapters;

import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.soshoplus.lite.R;
import com.soshoplus.lite.feedHolders.AdPost;
import com.soshoplus.lite.feedHolders.AudioPost;
import com.soshoplus.lite.feedHolders.BlogPost;
import com.soshoplus.lite.feedHolders.ColouredPost;
import com.soshoplus.lite.feedHolders.CoverPic;
import com.soshoplus.lite.feedHolders.DefaultPost;
import com.soshoplus.lite.feedHolders.ImagePost;
import com.soshoplus.lite.feedHolders.MapPost;
import com.soshoplus.lite.feedHolders.MultiImage;
import com.soshoplus.lite.feedHolders.ProfilePic;
import com.soshoplus.lite.feedHolders.SharedPost;
import com.soshoplus.lite.feedHolders.VideoPost;
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class timelineFeedAdapter extends BaseProviderMultiAdapter<post> implements LoadMoreModule, UpFetchModule {
    
    private static String TAG = "timelineFeed Adapter";
    
    public timelineFeedAdapter (@Nullable List<post> posts) {
        super(posts);
        /*default post*/
        addItemProvider(new DefaultPost());
        /*profile pic post*/
        addItemProvider(new ProfilePic());
        /*cover pic post*/
        addItemProvider(new CoverPic());
        /*ad post*/
        addItemProvider(new AdPost());
        /*shared post*/
        addItemProvider(new SharedPost());
        /*coloured post*/
        addItemProvider(new ColouredPost());
        /*video post*/
        addItemProvider(new VideoPost());
        /*image post*/
        addItemProvider(new ImagePost());
        /*audio post*/
        addItemProvider(new AudioPost());
        /*blog post*/
        addItemProvider(new BlogPost());
        /*map post*/
        addItemProvider(new MapPost());
        /*multi pic post*/
        addItemProvider(new MultiImage());
        
        addChildClickViewIds(R.id.like_btn, R.id.post_option, R.id.profile_pic,
                R.id.post_image, R.id.shared_post_image, R.id.article_thumbnail,
                R.id.post_contents, R.id.comment_btn);
    }
    
    @Override
    protected int getItemType (@NotNull List<? extends post> postList, int position) {
        
        /*checking file extension*/
        String extension = MimeTypeMap.getFileExtensionFromUrl(postList.get(position).getPostFile());
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getMimeTypeFromExtension(extension);
        
        if (postList.get(position).getPostType().equals(getContext().getString(R.string.profile_pic_type_post))) {
            return post.PROFILE_PIC;
        } else if (postList.get(position).getPostType().equals(getContext().getString(R.string.cover_pic_type_post))) {
            return post.COVER_PIC;
        } else if (postList.get(position).getPostType().equals(getContext().getString(R.string.ad_type_post))) {
            return post.ADS;
        }
        /*SHARED POST*/
        else if (postList.get(position).getPostType().equals("")) {
            return post.SHARED_POST;
        }
        /*COLOURED POST*/
        else if (!postList.get(position).getColorId().equals("0")) {
            return post.COLOURED_POST;
        }
        /*VIDEO POST*/
        else if (Objects.equals(type, getContext().getString(R.string.video_mp4))) {
            return post.VIDEO_POST;
        } else if (Objects.equals(type, getContext().getString(R.string.video_quicktime))) {
            return post.VIDEO_POST;
        }
        /*SINGLE IMAGE POST*/
        else if (Objects.equals(type, getContext().getString(R.string.image_jpeg))) {
            return post.IMAGE_POST;
        } else if (Objects.equals(type, getContext().getString(R.string.image_png))) {
            return post.IMAGE_POST;
        }
        /*AUDIO POST*/
        else if (Objects.equals(type, getContext().getString(R.string.audio_mpeg))) {
            return post.AUDIO_POST;
        }
        /*BLOG POST*/
        else if (!postList.get(position).getBlogId().equals("0")) {
            return post.BLOG_POST;
        }
        /*MAP POST*/
        else if (!postList.get(position).getPostMap().isEmpty()) {
            return post.MAP_POST;
        }
        /*MULTI IMAGE POST*/
        else if (postList.get(position).getMultiImage().equals("1")) {
            return post.MULTI_IMAGE_POST;
        }
        
        return post.DEFAULT_POST;
    }
}