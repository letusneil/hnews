package com.nvinas.hnews.ui;

import com.google.common.collect.Lists;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.source.StoryRepository;
import com.nvinas.hnews.testrule.RxTestRule;
import com.nvinas.hnews.ui.comments.CommentsContract;
import com.nvinas.hnews.ui.comments.CommentsPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nvinas on 15/02/2018.
 */

public class CommentsPresenterTest {

    @ClassRule
    public static final RxTestRule schedulers = new RxTestRule();

    @Mock
    private CommentsContract.View commentsView;

    @Mock
    private StoryRepository storyRepository;

    private CommentsPresenter commentsPresenter;

    private List<Integer> kidsTest = Lists.newArrayList(16377344, 16379114, 16377757, 16379257);

    private List<Integer> kidsTestError = Lists.newArrayList(-1, -2, -3, -4);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        commentsPresenter = new CommentsPresenter(storyRepository);
        commentsPresenter.takeView(commentsView);
    }

    @Test
    public void loadComments() {
        Comment comment = new Comment(16377344);
        commentsPresenter.loadComments(kidsTest);

        verify(commentsView, times(1)).setProgressIndicator(true);
        verify(commentsView, times(1)).isActive();
        when(storyRepository.getComment(16377344)).thenReturn(Observable.just(comment));
    }

    @Test
    public void getInnerComments() {
        Comment comment = new Comment(16379114);
        commentsPresenter.getInnerComments(comment, 0);
        when(storyRepository.getComment(16379114)).thenReturn(Observable.just(comment));
    }
}
