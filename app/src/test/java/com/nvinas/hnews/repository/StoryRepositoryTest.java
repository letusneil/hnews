package com.nvinas.hnews.repository;

import com.google.common.collect.Lists;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.data.source.StoryRepository;
import com.nvinas.hnews.data.source.remote.StoryRemoteDataSource;
import com.nvinas.hnews.testrule.RxTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by nvinas on 15/02/2018.
 */

public class StoryRepositoryTest {

    @ClassRule
    public static final RxTestRule schedulers = new RxTestRule();

    private StoryRepository storyRepository;

    @Mock
    private StoryRemoteDataSource storyRemoteDataSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        storyRepository = new StoryRepository(storyRemoteDataSource);
    }

    @Test
    public void getTopStories() {
        List<Integer> integers = Lists.newArrayList(1, 2, 3);
        when(storyRemoteDataSource.getTopStories()).thenReturn(Observable.just(integers));

        TestObserver<List<Integer>> testObserver = storyRepository.getTopStories().test();
        testObserver.assertNoErrors();
    }

    @Test
    public void getStory() {
        Story story = new Story(1234);
        when(storyRemoteDataSource.getStory(1234)).thenReturn(Observable.just(story));

        TestObserver<Story> testObserver = storyRepository.getStory(1234).test();
        testObserver.assertNoErrors();
    }

    @Test
    public void getComment() {
        Comment comment = new Comment(12345);
        when(storyRemoteDataSource.getComment(12345)).thenReturn(Observable.just(comment));

        TestObserver<Comment> testObserver = storyRepository.getComment(12345).test();
        testObserver.assertNoErrors();
    }

}
