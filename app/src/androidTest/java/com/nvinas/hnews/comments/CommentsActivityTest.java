package com.nvinas.hnews.comments;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.internal.deps.guava.collect.Lists;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import com.nvinas.hnews.R;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.ui.comments.CommentsActivity;
import com.nvinas.hnews.ui.comments.CommentsFragment;
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
    CommentsFragment commentsFragment;
    FragmentIdlingResource fragmentIdlingResource;

    @Rule
    public ActivityTestRule<CommentsActivity> commentsActivityActivityTestRule =
            new ActivityTestRule<CommentsActivity>(CommentsActivity.class);

    @Before
    public void setup() {
        commentsActivity = commentsActivityActivityTestRule.getActivity();
        commentsActivityActivityTestRule.getActivity().runOnUiThread(() -> commentsFragment = startCommentsFragment());
        fragmentIdlingResource = new FragmentIdlingResource(commentsFragment);
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

    private CommentsFragment startCommentsFragment() {
        CommentsActivity activity = commentsActivityActivityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Story story = new Story(16374802);
        story.setBy("andimm");
        story.setDescendants(34);
        story.setKids(Lists.newArrayList(16378162, 16375950, 16375127, 16375616, 16375880, 16375292, 16378276, 16377227, 16378790, 16375762));
        story.setScore(161);
        story.setTime(1518603071);
        story.setTitle("After Storm Over Tweets, The Times and a New Hire Part Ways");
        story.setTitle("story");
        story.setUrl("https://www.nytimes.com/2018/02/13/business/media/quinn-norton-new-york-times.html");
        CommentsFragment commentsFragment = CommentsFragment.newInstance(story);
        transaction.add(R.id.container, commentsFragment);
        transaction.commit();
        return commentsFragment;
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
