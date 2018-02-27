package com.nvinas.hnews.data.source;

import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by nvinas on 10/02/2018.
 */

public interface StoryDataSource {

    Observable<Story> getStory(int id);

    Observable<List<Integer>> getTopStories();

    Observable<Comment> getComment(int id);

    void refreshStories();
}
