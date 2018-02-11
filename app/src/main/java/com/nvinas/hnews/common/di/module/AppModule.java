package com.nvinas.hnews.common.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class AppModule {

    @Binds
    abstract Context context(Application app);
}
