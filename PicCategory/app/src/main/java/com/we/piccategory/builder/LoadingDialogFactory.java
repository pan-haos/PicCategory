package com.we.piccategory.builder;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.we.piccategory.R;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/21
 * Time: 19:05
 * Description:
 */
public class LoadingDialogFactory implements IDialogFactory {
    @Override
    public AlertDialog buildDialog(AlertDialog.Builder builder, Activity activity, String msg) {
        if (activity != null) {
            View layout = activity.getLayoutInflater().inflate(R.layout.layout_dialog,
                    (ViewGroup) activity.findViewById(R.id.layout_dialog));
            builder.setView(layout);
//            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去背景
            return dialog;
        }
        return null;
    }
}
