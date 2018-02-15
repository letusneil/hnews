package com.nvinas.hnews.common.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import com.nvinas.hnews.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by nvinas on 10/02/2018.
 */

public class CommonUtil {

    private CommonUtil() {
    }

    public static class Url {

        private Url() {
        }

        public static final String BASE_URL = "https://hacker-news.firebaseio.com";
        public static final String TOP_STORIES = "/v0/topstories.json";
        public static final String STORY = "/v0/item/{itemid}.json";
        public static final String COMMENT = "/v0/item/{itemid}.json";
    }

    public static class Constants {

        private Constants() {
        }

        public static final int PAGE_STORY_SIZE = 8;
        public static final int PAGE_COMMENT_SIZE = 24;
        public static final String EMPTY_STRING = "";
        public static final String INTENT_KEY_URL = "key_story_url";
        public static final String INTENT_KEY_STORY = "key_story";
        public static final String GOOGLE_PDF_VIEWER_URL = "http://drive.google.com/viewerng/viewer?embedded=true&url=";
        public static final String PDF_FILE = ".pdf";
    }

    /**
     * Returns an empty string ("") instead of null.
     * Can be useful for inconsistent or nullable external parameters to gracefully handle a null string.
     *
     * @param string The string input.
     * @return Empty string if input is null, otherwise returns the same input.
     */
    public static String nullToEmptySting(@Nullable String string) {
        if (TextUtils.isEmpty(string)) {
            return Constants.EMPTY_STRING;
        }
        return string;
    }

    /**
     * Returns the base url.
     *
     * @param url Url
     * @return base url
     */
    public static String urlToBaseUrl(@Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            return nullToEmptySting(url);
        }
        try {
            URL a = new URL(url);
            return a.getHost().replaceAll("www.", Constants.EMPTY_STRING);
        } catch (MalformedURLException e) {
            Timber.e(e);
            return Constants.EMPTY_STRING;
        }
    }

    public static String toTimeSpan(long timeMillis) {
        return DateUtils
                .getRelativeTimeSpanString(timeMillis * 1000,
                        System.currentTimeMillis(),
                        DateUtils.MINUTE_IN_MILLIS).toString();
    }

    public static DividerItemDecoration getDividerItemDecoration(
            @NonNull Context context, @DrawableRes int drawable, @RecyclerView.Orientation int orientation) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, orientation) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.setEmpty();
                } else {
                    super.getItemOffsets(outRect, view, parent, state);
                }
            }
        };
        Drawable dividerDrawable = ContextCompat.getDrawable(context, drawable);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
        }
        return dividerItemDecoration;
    }
}
