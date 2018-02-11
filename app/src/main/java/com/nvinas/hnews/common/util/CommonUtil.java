package com.nvinas.hnews.common.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.net.MalformedURLException;
import java.net.URL;

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
        public static final String ITEM = "/v0/item/{itemid}.json";
    }

    public static class Constants {

        private Constants() {
        }

        public static final int PAGE_SIZE = 8;
        public static final String EMPTY_STRING = "";
        public static final String INTENT_KEY_URL = "my_story_url";
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
     * @param url   Url
     * @return      base url
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

    public static void share(Context context, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, !TextUtils.isEmpty(subject) ?
                        TextUtils.join(" - ", new String[]{subject, text}) : text);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
