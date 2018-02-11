package com.nvinas.hnews.data.source;

import com.nvinas.hnews.common.util.RxUtil;
import com.nvinas.hnews.data.Story;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by nvinas on 10/02/2018.
 */
@Singleton
public class StoryRepository implements StoryDataSource {

    private StoryDataSource dataSource;

    @Inject
    StoryRepository(StoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Integer>> getTopStories() {
        return dataSource.getTopStories()
                .compose(RxUtil.applySchedulers());
    }

    @Override
    public Observable<Story> getItem(int id) {
        return dataSource.getItem(id)
                .compose(RxUtil.applySchedulers());
    }
}
