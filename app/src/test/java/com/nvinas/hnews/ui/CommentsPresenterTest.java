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
import org.mockito.Mockito;
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

    @Mock
    private CommentsContract.View commentsView;

    @Mock
    private StoryRepository storyRepository;

    private CommentsPresenter commentsPresenter;

    private List<Integer> kidsTest = Lists.newArrayList(16377344, 16379114, 16377757, 16379257);

    private Comment comment1 = new Comment(
            16481112, "WhiteRiceWill", kidsTest, 48, 1519799288,
            "Something something something hello world", "comment", 0);

    private Comment comment2 = new Comment(
            16483112, "BlackRiceWill", kidsTest, 58, 1516799288,
            "hello world Something something something ", "comment", 1);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        commentsPresenter = new CommentsPresenter(storyRepository);
        commentsPresenter.takeView(commentsView);

        when(commentsView.isActive()).thenReturn(true);
    }

    @Test
    public void loadComments() {
        when(storyRepository.getComment(16480503)).thenReturn(Observable.just(comment1));
        when(storyRepository.getComment(16481112)).thenReturn(Observable.just(comment2));
        commentsPresenter.loadComments(kidsTest);
        verify(commentsView).setProgressIndicator(true);
        verify(commentsView).setProgressIndicator(false);
        verify(commentsView).showErrorMessage(Mockito.anyString());
        //verify(commentsView).showComment(Mockito.any());
    }

    @Test
    public void getInnerComments() {
        when(storyRepository.getComment(16481112)).thenReturn(Observable.just(comment2));
        commentsPresenter.getInnerComments(comment1, 1);
    }

    @Test
    public void dropView() {
        commentsPresenter.dropView();
    }
}
