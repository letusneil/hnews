package com.nvinas.hnews.ui.comments;

import com.nvinas.hnews.common.util.RxUtil;
import com.nvinas.hnews.data.Comment;
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

public class CommentsPresenter implements CommentsContract.Presenter {

    private CommentsContract.View view;
    private StoryRepository storyRepository;
    private final CompositeDisposable subscriptions;

    @Inject
    CommentsPresenter(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        this.subscriptions = new CompositeDisposable();
    }

    @Override
    public void loadComments(List<Integer> ids) {
        view.setProgressIndicator(true);
        List<Comment> comments = new ArrayList<>();
        subscriptions.add(
                Observable.just(ids.subList(0, ids.size() == 1 ? 1 : ids.size() - 1))
                        .concatMapIterable(list -> list)
                        .concatMap(id -> storyRepository.getComment(id))
                        .subscribe(comments::add, Timber::e, () -> {
                            if (isAlive()) {
                                view.setProgressIndicator(false);
                                view.showComments(comments);
                            }
                        }));
    }

    @Override
    public void loadCommentChild(int id) {

    }

    @Override
    public void takeView(CommentsContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
        RxUtil.unsubscribe(subscriptions);
    }

    private boolean isAlive() {
        return view != null && view.isActive();
    }
}
