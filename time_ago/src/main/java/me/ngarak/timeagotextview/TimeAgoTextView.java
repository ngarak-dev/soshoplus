/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package me.ngarak.timeagotextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Date;

public class TimeAgoTextView extends TextView {

    private long mTimestamp = 0;
    private boolean mAbbreviated = false;

    public TimeAgoTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TimeAgoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TimeAgoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimeAgoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray ta = context.getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.TimeAgoTextView, 0, 0);
            mAbbreviated = ta.getBoolean(R.styleable.TimeAgoTextView_tstv_abbreviated, false);
        }
    }

    /**
     * @return Unix timestamp associated with this instance, or seconds since January 1, 1970
     */
    public long getDate() {
        return mTimestamp;
    }

    /**
     * Sets the timestamp displayed by this view
     *
     * @param date {@link java.util.Date} from which to calculate time passed
     */
    public void setDate(Date date) {
        setDate(date.getTime() / 1000);
    }

    /**
     * Sets the timestamp displayed by this view
     *
     * @param utc Unix timestamp from which to calculate time passed
     */
    public void setDate(long utc) {
        mTimestamp = utc;
        setText(TimeAgo.getFormattedDateString(utc, mAbbreviated, getContext()));
        setContentDescription(TimeAgo.getFormattedDateString(utc, false, getContext()));
    }

    /**
     * @return True if view is displaying abbreviated timestamp strings, otherwise false
     */
    public boolean isAbbreviated() {
        return mAbbreviated;
    }

    /**
     * Set to true if the view should display abbreviated timestamp strings
     *
     * @param b True if the view should display abbreviated timestamp strings
     */
    public void isAbbreviated(boolean b) {
        mAbbreviated = b;
        invalidate();
        requestLayout();
    }

    /**
     * @deprecated Use {@link TimeAgo#getFormattedDateString(long, boolean, Context)}
     */
    @Deprecated
    public static String getFormattedDateString(long utc, boolean abbreviated, Context context) {
        return TimeAgo.getFormattedDateString(utc, abbreviated, context);
    }

    /**
     * @deprecated Use {@link TimeAgo#getFormattedDateString(long, long, boolean, Context)}
     */
    @Deprecated
    public static String getFormattedDateString(
            long start, long end, boolean abbreviated, Context context) {
        return TimeAgo.getFormattedDateString(start, end, abbreviated, context);
    }
}
