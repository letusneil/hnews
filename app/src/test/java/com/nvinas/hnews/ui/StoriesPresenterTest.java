package com.nvinas.hnews.ui;

import com.google.common.collect.Lists;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.data.source.StoryRepository;
import com.nvinas.hnews.testrule.RxTestRule;
import com.nvinas.hnews.ui.stories.StoriesContract;
import com.nvinas.hnews.ui.stories.StoriesPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nvinas on 15/02/2018.
 */
public class StoriesPresenterTest {

    private static List<Integer> storyIds;

    @ClassRule
    public static final RxTestRule schedulers = new RxTestRule();

    @Mock
    private StoriesContract.View storiesView;

    @Mock
    private StoryRepository storyRepository;

    private StoriesPresenter storiesPresenter;

    private List<Integer> randomIds = Lists.newArrayList(16481636,16481343,16481546,16481564,16481219,16481617,16481258,16481382,16481751,16481358,16481389,16481209);
    private Story story = new Story(
            16481112, "WhiteRiceWill", 48, randomIds, 159,
            1519799288, "Dtube – A decentralized video platform using STEEM and IPFS",
            "story", "https://d.tube/");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        storiesPresenter = new StoriesPresenter(storyRepository);
        storiesPresenter.takeView(storiesView);

        when(storiesView.isActive()).thenReturn(true);
    }

    @Test
    public void showProgressIndicator() {
        storyIds = Lists.newArrayList(16481112, 16480503, 16476454, 16481392, 16480787, 16480038, 16476073, 16480142);
        storiesPresenter.setNextPage(1);
        when(storyRepository.getTopStoryIds()).thenReturn(Observable.just(storyIds));
        when(storyRepository.getStory(116481112)).thenReturn(Observable.just(story));
        storiesPresenter.loadStories(true);

        verify(storiesView, times(1)).setProgressIndicator(true);
        verify(storiesView, times(1)).setProgressIndicator(false);
    }

    @Test
    public void loadStories() {
        storyIds = Lists.newArrayList(16481112, 16480503, 16476454, 16481392, 16480787, 16480038, 16476073, 16480142);
        storiesPresenter.setNextPage(1);
        when(storyRepository.getStory(16480503)).thenReturn(Observable.just(story));
        when(storyRepository.getTopStoryIds()).thenReturn(Observable.just(storyIds));
        storiesPresenter.loadStories(false);
        //verify(storiesView).showStories(Mockito.any());
    }

    @Test
    public void showErrorMessage() {
        when(storyRepository.getTopStoryIds()).thenReturn(Observable.error(new IOException()));
        storiesPresenter.loadStories(true);
        verify(storiesView).showErrorMessage(Mockito.any());
    }

    @Test
    public void refreshStories() {
        storyIds = Lists.newArrayList(16481112, 16480503, 16476454, 16481392, 16480787, 16480038, 16476073, 16480142);
        storiesPresenter.setNextPage(1);
        when(storyRepository.getStory(16480503)).thenReturn(Observable.just(story));
        when(storyRepository.getTopStoryIds()).thenReturn(Observable.just(storyIds));
        storiesPresenter.refreshStories();
        verify(storiesView, times(1)).setProgressIndicator(true);
        //verify(storiesView).showStories(Mockito.any());
        verify(storiesView, times(1)).setProgressIndicator(false);
    }
}
