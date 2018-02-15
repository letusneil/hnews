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
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nvinas on 15/02/2018.
 */

public class StoriesPresenterTest {

    @ClassRule
    public static final RxTestRule schedulers = new RxTestRule();

    @Mock
    private StoriesContract.View storiesView;

    @Mock
    private StoryRepository storyRepository;

    private StoriesPresenter storiesPresenter;

    private List<Integer> ids = Lists.newArrayList(16377055, 16379257, 16377757);

    private List<Story> stories = Lists.newArrayList(
            new Story(1, "Neil", 0, Lists.newArrayList(1, 2, 3), 12,
                    1518627174, "Hello world", "Why", "https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/"),
            new Story(2, "Merlin", 0, Lists.newArrayList(15, 3, 3), 1612,
                    1528624174, "Why not world", "st", "https://codelabs.developers.google.com/codelabs/android-testing/index.html#5"),
            new Story(3, "Spot", 0, Lists.newArrayList(15, 21, 43), 156,
                    1224624173, "Susid", "st", "https://www.reddit.com/r/androiddev/"));

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        storiesPresenter = new StoriesPresenter(storyRepository);
    }

    @Test
    public void loadStories() {
        Story story = new Story(1, "Neil", 0, Lists.newArrayList(1, 2, 3), 12,
                1518627174, "Hello world", "Why", "https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/");
        when(storyRepository.getStory(anyInt())).thenReturn(Observable.just(story));
        when(storyRepository.getTopStories()).thenReturn(Observable.just(ids));
    }
}
