package com.nvinas.hnews.ui.stories;


import dagger.Binds;
import dagger.Module;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class StoriesModule {

    @Binds
    abstract StoriesContract.Presenter storiesPresenter(StoriesPresenter storiesPresenter);
}
