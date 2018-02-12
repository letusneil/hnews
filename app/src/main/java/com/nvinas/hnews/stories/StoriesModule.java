package com.nvinas.hnews.stories;

import com.nvinas.hnews.stories.StoriesContract;
import com.nvinas.hnews.stories.StoriesFragment;
import com.nvinas.hnews.stories.StoriesPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class StoriesModule {

    @ContributesAndroidInjector
    abstract StoriesFragment storiesFragment();

    @Binds
    abstract StoriesContract.Presenter storiesPresenter(StoriesPresenter storiesPresenter);
}
