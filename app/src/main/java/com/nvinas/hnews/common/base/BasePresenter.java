package com.nvinas.hnews.common.base;

/**
 * Created by nvinas on 10/02/2018.
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
