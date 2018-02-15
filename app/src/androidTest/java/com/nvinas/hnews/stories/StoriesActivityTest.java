package com.nvinas.hnews.stories;

import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentTransaction;


import com.nvinas.hnews.R;
import com.nvinas.hnews.ui.stories.StoriesActivity;
import com.nvinas.hnews.ui.stories.StoriesFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by nvinas on 15/02/2018.
 */

public class StoriesActivityTest {

    private StoriesActivity storiesActivity;
    private StoriesFragment storiesFragment;

    @Rule
    public ActivityTestRule<StoriesActivity> storiesActivityActivityTestRule =
            new ActivityTestRule<>(StoriesActivity.class);

    @Before
    public void setup() {
        storiesActivity = storiesActivityActivityTestRule.getActivity();
        storiesActivityActivityTestRule.getActivity().runOnUiThread(() -> storiesFragment = startStoriesFragment());
    }

    @Test
    public void storiesActivityCreated() {
        // container test
        onView(allOf(withId(R.id.container), isDisplayed()));
    }

    @Test
    public void storiesFragmentCreated() {
        onView(allOf(withId(R.id.appbar),
                withId(R.id.toolbar),
                withId(R.id.rv_stories), isDisplayed()));
    }

    @Test
    public void backFromTasksScreen_ExitsApp() {
        // From the tasks screen, press back should exit the app.
        assertPressingBackExitsApp();
    }

    private void assertPressingBackExitsApp() {
        try {
            pressBack();
            fail("Should kill the app and throw an exception");
        } catch (NoActivityResumedException e) {
            // Test OK
        }
    }

    private StoriesFragment startStoriesFragment() {
        StoriesActivity activity = storiesActivityActivityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        StoriesFragment storiesFragment = StoriesFragment.newInstance();
        transaction.add(R.id.container, storiesFragment);
        transaction.commit();
        return storiesFragment;
    }
}
