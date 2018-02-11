package com.nvinas.hnews.comments;

import android.os.Bundle;

import com.nvinas.hnews.common.base.BaseView;
import com.nvinas.hnews.common.util.CommonUtil;

import dagger.android.support.DaggerFragment;

/**
 * Created by nvinas on 10/02/2018.
 */

public class CommentsFragment extends DaggerFragment implements BaseView {

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void setProgressIndicator(boolean progress) {

    }

    public static CommentsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(CommonUtil.Constants.INTENT_KEY_ID, id);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
