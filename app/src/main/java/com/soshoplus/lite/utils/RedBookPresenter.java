/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.soshoplus.lite.R;
import com.ypx.imagepicker.adapter.PickerItemAdapter;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.selectconfig.BaseSelectConfig;
import com.ypx.imagepicker.data.ICameraExecutor;
import com.ypx.imagepicker.data.IReloadExecutor;
import com.ypx.imagepicker.data.ProgressSceneEnum;
import com.ypx.imagepicker.presenter.IPickerPresenter;
import com.ypx.imagepicker.utils.PViewSizeUtils;
import com.ypx.imagepicker.views.PickerUiConfig;
import com.ypx.imagepicker.views.redbook.RedBookUiProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;


public class RedBookPresenter implements IPickerPresenter {

    @Override
    public void displayImage(View view, ImageItem item, int size, boolean isThumbnail) {
        Object object = item.getUri() != null ? item.getUri() : item.path;

        ImageLoader imageLoader = Coil.imageLoader(view.getContext());

        if (item.isImage()) {
            ImageRequest imageRequest = new ImageRequest.Builder(view.getContext())
                    .data(object)
                    .crossfade(true)
                    .target((ImageView) view)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
        else {
            ImageRequest imageRequest = new ImageRequest.Builder(view.getContext())
                    .data(item.getVideoImageUri())
                    .crossfade(true)
                    .target((ImageView) view)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
    }

    @NotNull
    @Override
    public PickerUiConfig getUiConfig(Context context) {
        PickerUiConfig uiConfig = new PickerUiConfig();
        uiConfig.setThemeColor(context.getResources().getColor(R.color.colorPrimaryDark));
        uiConfig.setShowStatusBar(false);
        uiConfig.setStatusBarColor(Color.BLACK);
        uiConfig.setPickerBackgroundColor(Color.BLACK);
        uiConfig.setFolderListOpenDirection(PickerUiConfig.DIRECTION_TOP);
        uiConfig.setFolderListOpenMaxMargin(PViewSizeUtils.dp(context, 200));

        uiConfig.setPickerUiProvider(new RedBookUiProvider());
        return uiConfig;
    }

    @Override
    public void tip(Context context, String msg) {
        if (context == null) {
            return;
        }
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void overMaxCountTip(Context context, int maxCount) {
        if (context == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Maximum images " + maxCount);
        builder.setPositiveButton("OKAY",
                (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public DialogInterface showProgressDialog(@Nullable Activity activity, ProgressSceneEnum progressSceneEnum) {
        return ProgressDialog.show(activity, null, progressSceneEnum == ProgressSceneEnum.crop ? "Cropping..." : "loading...");
    }

    @Override
    public boolean interceptPickerCompleteClick(final Activity activity, final ArrayList<ImageItem> selectedList,
                                                BaseSelectConfig selectConfig) {
        return false;
    }

    @Override
    public boolean interceptPickerCancel(final Activity activity, ArrayList<ImageItem> selectedList) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }

        activity.finish();

//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage("Are you sure you want to cancel？");
//        builder.setPositiveButton("SURE",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        activity.finish();
//                    }
//                });
//        builder.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
        return true;
    }

    @Override
    public boolean interceptItemClick(@Nullable Activity activity, ImageItem imageItem, ArrayList<ImageItem> selectImageList, ArrayList<ImageItem> allSetImageList, BaseSelectConfig selectConfig, PickerItemAdapter adapter,boolean isClickCheckBox, @Nullable IReloadExecutor reloadExecutor) {
        return false;
    }

    @Override
    public boolean interceptCameraClick(@Nullable Activity activity, ICameraExecutor takePhoto) {
        return false;
    }
}
