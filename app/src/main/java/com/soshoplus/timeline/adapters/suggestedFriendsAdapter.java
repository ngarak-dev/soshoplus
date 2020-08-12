/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.os.Looper;
import android.text.Html;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class suggestedFriendsAdapter extends BaseQuickAdapter<suggestedInfo, BaseViewHolder> {
    
    private static String TAG = "Suggested Friends Adapter";

    public suggestedFriendsAdapter (int layoutResId, @Nullable List<suggestedInfo> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder, suggestedInfo suggestedInfo) {
        if (suggestedInfo == null) {
            return;
        }

        HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
            
            baseViewHolder.setText(R.id.full_name, suggestedInfo.getName());

            SimpleDraweeView profile_pic = baseViewHolder.findView(R.id.profile_pic);
            TextView aboutMe = baseViewHolder.findView(R.id.about_me);
            
            if (suggestedInfo.getAbout() == null) {
                aboutMe.setText("Hello there, I am using soshoplus");
            } else {
                aboutMe.setText(Html.fromHtml(suggestedInfo.getAbout()));
            }
            
            profile_pic.setImageURI(suggestedInfo.getAvatar());
            
        });
    }
}

//    private final onSuggestedClickListener suggestedClickListener;
//    private final List<suggestedInfo> suggestedInfoList;
//    private Context context;
//    private static String TAG = "Suggested Groups";
//
//    public suggestedFriendsAdapter (Context context, List<suggestedInfo> list,
//                                    onSuggestedClickListener suggestedClickListener) {
//        this.context = context;
//        this.suggestedInfoList = list;
//        this.suggestedClickListener = suggestedClickListener;
//    }
//
//    /*inflating and initializing a view*/
//    @NonNull
//    @Override
//    public suggestedFriendsAdapter.SuggestedFriendsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
//        View view =  LayoutInflater.from(context).inflate(R.layout.suggested_friends_list_row, parent, false);
//        return new SuggestedFriendsHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder (@NonNull suggestedFriendsAdapter.SuggestedFriendsHolder holder, int position) {
//        /*bind items and set onclick listener*/
//        holder.bind(suggestedInfoList.get(position), suggestedClickListener, context);
//    }
//
//    @Override
//    public int getItemCount () {
//        return suggestedInfoList.size();
//    }
//
//    static class SuggestedFriendsHolder extends RecyclerView.ViewHolder{
//
//        ShapeableImageView profile_pic;
//        TextView full_name;
//        TextView about;
//        MaterialButton follow;
//        ProgressBar progressBar;
//
//        public SuggestedFriendsHolder (@NonNull View itemView) {
//            super(itemView);
//            profile_pic = itemView.findViewById(R.id.profile_pic);
//            full_name = itemView.findViewById(R.id.full_name);
////            follow = itemView.findViewById(R.id.btn_follow);
//            about = itemView.findViewById(R.id.about_me);
//            progressBar = itemView.findViewById(R.id.progressBar_follow);
//        }
//
//        public void bind (suggestedInfo suggestedInfo, onSuggestedClickListener suggestedClickListener, Context context) {
//
//            Observable.fromArray(suggestedInfo).subscribe(new Consumer<com.soshoplus.timeline.models.friends.suggested.suggestedInfo>() {
//                @Override
//                public void accept (suggestedInfo suggestedInfo) throws Throwable {
//
//                    full_name.setText(suggestedInfo.getName());
//
//                    if (String.valueOf(suggestedInfo.getAbout()).isEmpty()) {
//                        about.setText("Hey there I am using soshoplus");
//                    } else if (String.valueOf(suggestedInfo.getAbout()).equals("null")) {
//                        about.setText("Hey there I am using soshoplus");
//                    } else if (String.valueOf(suggestedInfo.getAbout()).equals("")) {
//                        about.setText("Hey there I am using soshoplus");
//                    } else {
//                        about.setText(Html.fromHtml(String.valueOf(suggestedInfo.getAbout())));
//                    }
//
//                }
//            });
//
//            profile_pic.setShapeAppearanceModel(profile_pic
//                    .getShapeAppearanceModel()
//                    .toBuilder()
//                    .setAllCorners(CornerFamily.ROUNDED, 20)
//                    .build());
//
//            Observable.fromArray(suggestedInfo.getAvatar()).subscribe(new Consumer<String>() {
//                @Override
//                public void accept (String postImage) throws Throwable {
//                    Glide.with(context).load(suggestedInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder)
//                            .thumbnail(0.5f).into(profile_pic);
//                }
//            }).dispose();
//
//            /*on click*/
//            itemView.setOnClickListener(v ->
//                    suggestedClickListener.onClick(suggestedInfo));
//        }
//    }
//
//    /*interface for click listener*/
//    public interface onSuggestedClickListener {
//        /*onclick*/
//        void onClick (suggestedInfo suggestedInfo);
///*        *//*on follow btn click*//*
//        void onFollowClick (suggestedInfo suggestedInfo, MaterialButton follow, ProgressBar progressBar);*/
//    }
