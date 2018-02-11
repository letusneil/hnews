package com.nvinas.hnews.stories;

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
    private boolean initialLoad = true;
    private int currentPage = 1;
    private List<Integer> ids;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Inject
    StoriesPresenter(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void takeView(StoriesContract.View view) {
        this.view = view;
        if (initialLoad) loadStories();
    }

    @Override
    public void dropView() {
        view = null;
        RxUtil.unsubscribe(subscriptions);
    }

    @Override
    public void loadStories() {
        initialLoad = true;
        view.setProgressIndicator(true);
        subscriptions.add(storyRepository
                .getTopStories()
                .subscribe(x -> {
                            this.ids = x;
                            initialLoad = false;
                            loadStoriesInfo();
                        },
                        throwable -> {
                            if (isAlive()) {
                                view.setProgressIndicator(false);
                                view.showErrorMessage(throwable.getMessage());
                            }
                        }));
    }

    @Override
    public void loadStoriesInfo() {
        int end = currentPage * CommonUtil.Constants.PAGE_SIZE;
        int start = end - CommonUtil.Constants.PAGE_SIZE;
        List<Story> stories = new ArrayList<>();

        subscriptions.add(
                Observable.just(ids.subList(start, end))
                        .concatMapIterable(list -> list)
                        .concatMap(id -> storyRepository.getItem(id))
                        .subscribe(stories::add, Timber::e, () -> {
                            if (isAlive()) {
                                view.setProgressIndicator(false);
                                view.showStories(stories);
                                currentPage++;
                            }
                        }));
    }

    private boolean isAlive() {
        return view != null && view.isActive();
    }

}
