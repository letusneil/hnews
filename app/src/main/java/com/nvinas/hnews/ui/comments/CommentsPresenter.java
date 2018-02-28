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
    private List<Integer> ids;

    @Inject
    public CommentsPresenter(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        this.subscriptions = new CompositeDisposable();
    }

    @Override
    public void loadComments(List<Integer> kids) {
        ids = kids;
        view.setProgressIndicator(true);
        view.setIdleStatus(false);

        List<Comment> comments = new ArrayList<>(0);
        subscriptions.add(Observable.fromIterable(kids)
                .concatMap(id -> storyRepository.getComment(id))
                .concatMap(x -> getInnerComments(x, 1))
                .subscribe(a -> {
                    Timber.d("Comment id: %s and level: %s", a.getId(), a.getLevel());
                    comments.add(a);
                    if (isAlive())
                        view.showComment(a);
                }, e -> {
                    if (isAlive()) {
                        view.setProgressIndicator(false);
                        view.showErrorMessage(e.getMessage());
                        view.setIdleStatus(true);
                    }
                }, () -> {
                    if (isAlive()) {
                        view.setProgressIndicator(false);
                        view.setIdleStatus(true);
                    }
                }));
    }

    public Observable<Comment> getInnerComments(Comment comment, int level) {
        if (comment.getKids() != null && !comment.getKids().isEmpty() && comment.getKids().size() > 0) {
            return Observable.just(comment)
                    .mergeWith(
                            Observable.fromIterable(comment.getKids())
                                    .concatMap(x -> storyRepository.getComment(x)
                                            .onErrorReturn(throwable -> {
                                                Timber.d(throwable.getMessage());
                                                return null;
                                            }))
                                    .filter(x -> {
                                        Timber.d("Comment x: %s", x);
                                        x.setLevel(level);
                                        return true;
                                    })
                                    .concatMap(x -> getInnerComments(x, level + 1)));
        }
        return Observable.just(comment);
    }

    @Override
    public void takeView(CommentsContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        RxUtil.unsubscribe(subscriptions);
        view = null;
    }

    private boolean isAlive() {
        return view != null && view.isActive();
    }

}
