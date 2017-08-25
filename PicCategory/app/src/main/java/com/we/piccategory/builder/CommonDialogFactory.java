package com.we.piccategory.builder;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/21
 * Time: 19:11
 * Description:
 */
public class CommonDialogFactory implements IDialogFactory {


    @Override
    public AlertDialog buildDialog(AlertDialog.Builder builder, Activity activity, String msg) {
        builder.setTitle("提示");
        builder.setMessage(msg);
        AlertDialog dialog = builder.setPositiveButton("确定", null).create();
        return dialog;
    }
}
