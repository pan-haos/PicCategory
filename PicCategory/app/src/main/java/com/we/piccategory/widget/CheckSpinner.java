package com.we.piccategory.widget;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.ui.activity.ChangeInfoActivity;
import com.we.piccategory.ui.activity.RegisterInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/23
 * Time: 9:45
 * Description:
 */
public class CheckSpinner extends android.support.v7.widget.AppCompatSpinner {

    private List<Integer> list = new ArrayList<Integer>();

    String[] mItems = getResources().getStringArray(R.array.check_arr);

    public CheckSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {
        final SpinnerDialog dialog = new SpinnerDialog(getContext());
        TextView textView = (TextView) dialog.findViewById(R.id.over_text);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    if (RegisterInfoActivity.tvShow != null) {
                        RegisterInfoActivity.tvShow.setText(mItems[0], null);
                    }
                    if (ChangeInfoActivity.tvShow != null) {
                        ChangeInfoActivity.tvShow.setText(mItems[0], null);
                    }
                } else {
                    StringBuffer buffer = new StringBuffer();
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        buffer.append(mItems[list.get(i)] + ',');
                    }
                    if (RegisterInfoActivity.tvShow != null) {
                        RegisterInfoActivity.tvShow.setText(buffer.toString().substring(0, buffer.length() - 1), null);
                    }
                    if (ChangeInfoActivity.tvShow != null) {
                        ChangeInfoActivity.tvShow.setText(buffer.toString().substring(0, buffer.length() - 1), null);
                    }

                }
                dialog.cancel();
                list.clear();
            }
        });
        dialog.show();
        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_list_item_multiple_choice, mItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer object = Integer.valueOf(position);
                CheckSpinner.this.setSelection(position);
                if (list.contains(object)) {
                    list.remove(object);
                } else {
                    list.add(object);
                }
            }
        });
        dialog.show();
        return true;
    }


    private class SpinnerDialog extends AppCompatDialog {
        public SpinnerDialog(Context context) {
            super(context, R.style.Theme_dialog);
            setContentView(R.layout.spinner);
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            DisplayMetrics dm = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeigh = dm.heightPixels;
            params.height = (int) (screenHeigh * 0.8);
            params.width = (int) (screenWidth * 0.8);
            window.setAttributes(params);
        }

    }


}
