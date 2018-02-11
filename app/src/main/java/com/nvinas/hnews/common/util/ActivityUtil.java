package com.nvinas.hnews.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by nvinas on 10/02/2018.
 */

public class ActivityUtil {

    private ActivityUtil() {
    }

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void startActivity(Context context, Class<?> cls, @NonNull Intent intent){
        if (context == null || cls == null) {
            return;
        }
        intent.setClass(context, cls);
        context.startActivity(intent);
    }
}
