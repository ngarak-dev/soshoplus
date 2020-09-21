/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package me.ngarak.timeagotextview;

import android.content.Context;

import androidx.annotation.PluralsRes;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TimeSinceTextViewAndroidTest {

    private final long mTime = new Date().getTime() / 1000;

    Context getContext() {
        return InstrumentationRegistry.getContext();
    }

    @Test
    public void getFormattedDateString_now() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 10, mTime, false, getContext()),
                is(getContext().getString(R.string.tstv_timespan_now))
        );
    }

    @Test
    public void getFormattedDateString_11seconds() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 11, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_seconds, 11))
        );
    }

    @Test
    public void getFormattedDateString_59seconds() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 59, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_seconds, 59))
        );
    }

    @Test
    public void getFormattedDateString_1minute() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_minutes, 1))
        );
    }

    @Test
    public void getFormattedDateString_59minutes() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 60 * 60 + 1, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_minutes, 59))
        );
    }

    @Test
    public void getFormattedDateString_1hour() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_hours, 1))
        );
    }

    @Test
    public void getFormattedDateString_23hours() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 24 * 60 * 60 + 1, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_hours, 23))
        );
    }

    @Test
    public void getFormattedDateString_1day() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 24 * 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_days, 1))
        );
    }

    @Test
    public void getFormattedDateString_6days() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 7 * 24 * 60 * 60 + 1, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_days, 6))
        );
    }

    @Test
    public void getFormattedDateString_1week() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 7 * 24 * 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_weeks, 1))
        );
    }

    @Test
    public void getFormattedDateString_4weeks() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 30 * 24 * 60 * 60 + 1, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_weeks, 4))
        );
    }

    @Test
    public void getFormattedDateString_1month() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 30 * 24 * 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_months, 1))
        );
    }

    @Test
    public void getFormattedDateString_12months() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 365 * 24 * 60 * 60 + 1, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_months, 12))
        );
    }

    @Test
    public void getFormattedDateString_1year() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 365 * 24 * 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_years, 1))
        );
    }

    @Test
    public void getFormattedDateString_30years() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 30 * 365 * 24 * 60 * 60, mTime, false, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_years, 30))
        );
    }

    @Test
    public void getFormattedDateString_abbrev() {
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 59, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_seconds_abbr, 59))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_minutes_abbr, 1))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 60 * 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_hours_abbr, 1))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 24 * 60 * 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_days_abbr, 1))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 7 * 24 * 60 * 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_weeks_abbr, 1))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 30 * 24 * 60 * 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_months_abbr, 1))
        );
        assertThat(
                TimeAgo.getFormattedDateString(
                        mTime - 365 * 24 * 60 * 60, mTime, true, getContext()),
                is(getQuantityString(R.plurals.tstv_timespan_years_abbr, 1))
        );
    }

    @Test
    public void setDate_long() {
        TimeAgoTextView view = new TimeAgoTextView(getContext());
        view.setDate(mTime - 24 * 60 * 60); // 1 day
        assertThat(
                view.getText().toString(),
                is(getQuantityString(R.plurals.tstv_timespan_days, 1))
        );
    }

    @Test
    public void setDate_java_util_date() {
        TimeAgoTextView view = new TimeAgoTextView(getContext());
        view.setDate(new Date((mTime - 24 * 60 * 60) * 1000)); // 1 day
        assertThat(
                view.getText().toString(),
                is(getQuantityString(R.plurals.tstv_timespan_days, 1))
        );
    }

    private static Matcher<String> is(String matcher, Object... args) {
        return Is.is(
                String.format(matcher, args)
        );
    }

    private String getQuantityString(@PluralsRes int resId, int n) {
        return getContext().getResources().getQuantityString(resId, n, n);
    }
}
