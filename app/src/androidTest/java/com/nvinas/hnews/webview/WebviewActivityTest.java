package com.nvinas.hnews.webview;

import android.support.test.espresso.web.sugar.Web;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nvinas.hnews.R;
import com.nvinas.hnews.ui.webview.WebViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by nvinas on 15/02/2018.
 */
@RunWith(AndroidJUnit4.class)
public class WebviewActivityTest {

    @Rule
    public ActivityTestRule<WebViewActivity> activityTestRule = new ActivityTestRule<>(WebViewActivity.class);

    @Test
    public void webviewActivityCreated() {
        onView(allOf(withId(R.id.webview), withId(R.id.avi_progress_indicator), isDisplayed()));
    }
}
