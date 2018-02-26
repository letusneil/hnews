package com.nvinas.hnews.common.di.component;

import android.app.Application;

import com.nvinas.hnews.App;
import com.nvinas.hnews.common.di.module.ActivityBindingModule;
import com.nvinas.hnews.common.di.module.AppModule;
import com.nvinas.hnews.data.source.StoryRepositoryModule;
import com.nvinas.hnews.common.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by nvinas on 10/02/2018.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        StoryRepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
