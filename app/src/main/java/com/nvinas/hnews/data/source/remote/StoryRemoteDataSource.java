package com.nvinas.hnews.data.source.remote;

import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.data.source.StoryDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nvinas on 10/02/2018.
 */
@Singleton
public class StoryRemoteDataSource implements StoryDataSource {

    private final HackerNewsService api;

    @Inject
    StoryRemoteDataSource(Retrofit retrofit) {
        api = retrofit.create(HackerNewsService.class);
    }

    @Override
    public Observable<Story> getStory(int id) {
        return api.getItem(id);
    }

    @Override
    public Observable<Comment> getComment(int id) {
        return api.getComment(id);
    }

    @Override
    public Observable<List<Integer>> getTopStoryIds() {
        return api.getTopStories();
    }

    @Override
    public void refreshStoryIds() {
        throw new UnsupportedOperationException();
    }

    interface HackerNewsService {

        @GET(CommonUtil.Url.TOP_STORIES)
        Observable<List<Integer>> getTopStories();

        @GET(CommonUtil.Url.STORY)
        Observable<Story> getItem(@Path("itemid") int id);

        @GET(CommonUtil.Url.COMMENT)
        Observable<Comment> getComment(@Path("itemid") int id);
    }

}
