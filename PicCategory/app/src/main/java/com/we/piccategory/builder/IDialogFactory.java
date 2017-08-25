package com.we.piccategory.builder;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/21
 * Time: 19:04
 * Description:
 */
public interface IDialogFactory {

    AlertDialog buildDialog(AlertDialog.Builder builder, Activity activity,String msg);

}
