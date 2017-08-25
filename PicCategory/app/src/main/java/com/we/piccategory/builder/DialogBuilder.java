package com.we.piccategory.builder;

import android.app.Activity;
import android.app.AlertDialog;

import java.lang.ref.WeakReference;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/21
 * Time: 18:23
 * Description:
 */
public class DialogBuilder {

    public static final int LOADING_MODEL = 1;

    public static final int COMMON_MODEL = 2;


    private WeakReference<Activity> viewRef;


    public void attachView(Activity activity) {
        this.viewRef = new WeakReference<>(activity);
    }


    public AlertDialog createLoadingDialog(String msg, int model) {

        Activity activity = viewRef.get();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            AlertDialog buildDialog = null;
            switch (model) {
                case LOADING_MODEL:
                    buildDialog = new LoadingDialogFactory().buildDialog(builder, activity, "");
                    break;
                case COMMON_MODEL:
                    buildDialog = new CommonDialogFactory().buildDialog(builder, activity, msg);
                    break;
                default:
                    break;
            }
            return buildDialog;
        }
        return null;
    }


    public AlertDialog createLoadDialog() {
        Activity activity = viewRef.get();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            AlertDialog buildDialog = new LoadingDialogFactory().buildDialog(builder, activity, "");
            return buildDialog;
        }
        return null;
    }


    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }


}
