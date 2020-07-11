/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class timelineScrollListener extends RecyclerView.OnScrollListener {
    //starting index
    private final int startingPageIndex = 0;
    //Minimum items before scrolling
    private int visibleThreshold = 3;
    //current index of data loaded
    private int currentId = 0;
    //total number of items after the last load
    private int previousTotalItemCount = 0;
    
    private boolean loadMore = true;
    private RecyclerView.LayoutManager layoutManager;
    
    public timelineScrollListener (LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }
    
    @Override
    public void onScrolled (@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    
        int lastVisibleItemPosition = 0;
        int totalItemCount = layoutManager.getItemCount();
    
        if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
    
        //if total item is zero list is reseated back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentId = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loadMore = true;
            }
        }
    
        Log.d("TAG", ": startingPageIndex  " + startingPageIndex);
        Log.d("TAG", ": previousTotalItemCount  " + previousTotalItemCount);
    
        //if it is loading, check if data set has changed. Then stop loading
        if (loadMore && (totalItemCount > previousTotalItemCount)) {
            loadMore = false;
            previousTotalItemCount = totalItemCount;
        }
    
        //if it is not loading, check is threhold has met and reload more data
        if (!loadMore && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentId  = lastVisibleItemPosition;
            onLoadMorePosts(currentId, totalItemCount, recyclerView);
            loadMore = true;
        }
    
        Log.d("TAG", "onScrolledPage: " + currentId);
    }
    /*load more*/
    protected abstract void onLoadMorePosts (int currentId, int totalItemCount, RecyclerView recyclerView);
}
