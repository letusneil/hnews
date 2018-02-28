package com.nvinas.hnews.ui.stories;

import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.common.util.RxUtil;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.data.source.StoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by nvinas on 10/02/2018.
 */
public class StoriesPresenter implements StoriesContract.Presenter {

    private StoriesContract.View view;
    private StoryRepository storyRepository;
    private final CompositeDisposable subscriptions;

    private List<Integer> storyIds;
    private boolean refreshStories = false;
    private int nextPage = 1;

    @Inject
    public StoriesPresenter(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        this.subscriptions = new CompositeDisposable();
    }

    @Override
    public void takeView(StoriesContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
        RxUtil.unsubscribe(subscriptions);
    }

    @Override
    public void loadStories(boolean forceUpdateStoryIds) {
        view.setProgressIndicator(forceUpdateStoryIds);
        view.setIdleStatus(false);

        int lastItem = nextPage * CommonUtil.Constants.PAGE_STORY_SIZE;
        int firstItem = lastItem - CommonUtil.Constants.PAGE_STORY_SIZE;
        loadStoryIds(forceUpdateStoryIds, firstItem, lastItem);
    }

    private void loadStoryIds(boolean refreshStoryIds, int firstItem, int lastItem) {
        if (refreshStoryIds) {
            storyRepository.refreshStoryIds();
        }

        subscriptions.add(storyRepository
                .getTopStoryIds()
                .subscribe(ids -> {
                            getStories(ids, firstItem, lastItem);
                        },
                        throwable -> {
                            if (isAlive()) {
                                view.setProgressIndicator(false);
                                view.showStoriesUnavailableError();
                                view.showErrorMessage(throwable.getMessage());
                                view.setIdleStatus(true);
                            }
                        }));
    }

    private void getStories(List<Integer> storyIds, int firstItem, int lastItem) {
        this.storyIds = new ArrayList<>(storyIds);
        getStories(firstItem, lastItem);
    }

    private void getStories(int firstItem, int lastItem) {
        Timber.d("Getting story for item storyIds %s", storyIds.subList(firstItem, lastItem));
        List<Story> stories = new ArrayList<>();
        subscriptions.add(
                Observable.fromIterable(storyIds.subList(firstItem, lastItem))
                        .concatMap(id -> storyRepository.getStory(id))
                        .subscribe(stories::add,
                                e -> {
                                    Timber.e(e.getMessage());
                                    view.showErrorMessage(e.getMessage());
                                    view.setProgressIndicator(false);
                                    view.setIdleStatus(true);
                                },
                                () -> {
                                    if (isAlive()) {
                                        view.setProgressIndicator(false);
                                        if (refreshStories) {
                                            // replace current story list
                                            view.showStories(stories);
                                            refreshStories = false;
                                            nextPage = 2;
                                        } else {
                                            // adds new stories to existing story list
                                            view.showMoreStories(stories);
                                            nextPage++;
                                        }
                                        view.setIdleStatus(true);
                                    }
                                }));
    }

    @Override
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public void refreshStories() {
        view.setProgressIndicator(true);
        nextPage = 1;
        refreshStories = true;
        loadStoryIds(true, 0, CommonUtil.Constants.PAGE_STORY_SIZE);
    }

    private boolean isAlive() {
        return view != null && view.isActive();
    }

}
