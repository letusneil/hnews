package com.nvinas.hnews.comments;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nvinas.hnews.R;
import com.nvinas.hnews.ui.comments.CommentsActivity;
import com.nvinas.hnews.util.FragmentIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by nvinas on 15/02/2018.
 */
@RunWith(AndroidJUnit4.class)
public class CommentsActivityTest {

    CommentsActivity commentsActivity;
    FragmentIdlingResource fragmentIdlingResource;

    @Rule
    public ActivityTestRule<CommentsActivity> commentsActivityActivityTestRule =
            new ActivityTestRule<CommentsActivity>(CommentsActivity.class);

    @Before
    public void setup() {
        commentsActivity = commentsActivityActivityTestRule.getActivity();
    }

    @Test
    public void commentsActivityCreated() {
        // container test
        onView(allOf(withId(R.id.container),
                withParent(withParent(withId(R.id.activity_stories))), isDisplayed()));
        // appbar test
        onView(allOf(withId(R.id.toolbar),
                withParent(withParent(withId(R.id.appbar))), isDisplayed()));
    }

    @Test
    public void commentsFragmentCreated() {
        // check if item is displayed
        onView(allOf(withId(R.id.tv_item_title),
                withId(R.id.tv_item_info),
                withId(R.id.rv_comments),
                withParent(withParent(withId(R.id.swipe_refresh))), isDisplayed()));
    }

    @After
    public void teardown() {
        IdlingRegistry.getInstance().unregister(fragmentIdlingResource);
    }

}
