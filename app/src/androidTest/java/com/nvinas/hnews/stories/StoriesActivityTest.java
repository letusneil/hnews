package com.nvinas.hnews.stories;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nvinas.hnews.R;
import com.nvinas.hnews.ui.stories.StoriesActivity;
import com.nvinas.hnews.util.HnewsActivityIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by nvinas on 15/02/2018.
 */
@RunWith(AndroidJUnit4.class)
public class StoriesActivityTest {

    HnewsActivityIdlingResource hnewsActivityIdlingResource;

    @Rule
    public ActivityTestRule<StoriesActivity> storiesActivityActivityTestRule =
            new ActivityTestRule<>(StoriesActivity.class);

    @Before
    public void setup() {
        StoriesActivity storiesActivity = storiesActivityActivityTestRule.getActivity();
        hnewsActivityIdlingResource = new HnewsActivityIdlingResource(storiesActivity);
        IdlingRegistry.getInstance().register(hnewsActivityIdlingResource);
    }

    @Test
    public void storiesActivityUiCreated() {
        allOf(withId(R.id.rv_stories),
                withParent(allOf(withId(R.id.swipe_refresh),
                        withParent(withId(R.id.fragment_stories)))),
                isDisplayed());
    }

    @Test
    public void backFromTasksScreen_ExitsApp() {
        assertPressingBackExitsApp();
    }

    @After
    public void teardown() {
        IdlingRegistry.getInstance().unregister(hnewsActivityIdlingResource);
    }

    private void assertPressingBackExitsApp() {
        try {
            pressBack();
            fail("Should kill the app and throw an exception");
        } catch (NoActivityResumedException e) {
            // Test OK
        }
    }
}
