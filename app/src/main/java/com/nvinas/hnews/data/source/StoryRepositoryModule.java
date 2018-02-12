package com.nvinas.hnews.data.source;

import com.nvinas.hnews.data.source.StoryDataSource;
import com.nvinas.hnews.data.source.remote.StoryRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class StoryRepositoryModule {

    @Singleton
    @Binds
    abstract StoryDataSource providesItemDataSource(StoryRemoteDataSource dataSource);
}
