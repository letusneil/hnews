package com.nvinas.hnews.ui.stories;

import com.nvinas.hnews.common.di.annotation.ActivityScope;
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
@ActivityScope
public class StoriesPresenter implements StoriesContract.Presenter {

    private StoriesContract.View view;
    private StoryRepository storyRepository;
    private final CompositeDisposable subscriptions;

    private int currentPage = 1;
    private List<Integer> ids;

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
    public void loadStories() {
        view.setProgressIndicator(true);
        view.setIdleStatus(false);
        subscriptions.add(storyRepository
                .getTopStories()
                .subscribe(x -> {
                            this.ids = x;
                            loadStoriesInfo();
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

    @Override
    public void loadStoriesInfo() {
        int lastItem = currentPage * CommonUtil.Constants.PAGE_STORY_SIZE;
        int firstItem = lastItem - CommonUtil.Constants.PAGE_STORY_SIZE;
        List<Story> stories = new ArrayList<>();

        Timber.d("ids %s", ids);

        subscriptions.add(
                Observable.just(ids.subList(firstItem, lastItem))
                        .concatMapIterable(list -> list)
                        .concatMap(id -> storyRepository.getStory(id))
                        .subscribe(stories::add, e -> {
                            Timber.e(e.getMessage());
                            view.setIdleStatus(true);
                        }, () -> {
                            if (isAlive()) {
                                view.setProgressIndicator(false);
                                view.showStories(stories);
                                view.setIdleStatus(true);
                                currentPage++;
                            }
                        }));
    }

    private boolean isAlive() {
        return view != null && view.isActive();
    }

}
