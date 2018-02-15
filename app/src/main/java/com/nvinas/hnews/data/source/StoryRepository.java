package com.nvinas.hnews.data.source;

import com.nvinas.hnews.common.util.RxUtil;
import com.nvinas.hnews.data.Comment;
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
    public StoryRepository(StoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Integer>> getTopStories() {
        return dataSource.getTopStories()
                .compose(RxUtil.applySchedulers());
    }

    @Override
    public Observable<Story> getStory(int id) {
        return dataSource.getStory(id)
                .compose(RxUtil.applySchedulers());
    }

    @Override
    public Observable<Comment> getComment(int id) {
        return dataSource.getComment(id);
    }
}
