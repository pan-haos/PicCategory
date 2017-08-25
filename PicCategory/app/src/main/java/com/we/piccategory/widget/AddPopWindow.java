package com.we.piccategory.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.util.Constant;

import java.lang.ref.WeakReference;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 17:22
 * Description:
 */
public class AddPopWindow extends PopupWindow {
    private View convertView;
    private WeakReference<Activity> ref;
    private ImageView imageView;
    private OnClick click;

    public AddPopWindow(Activity activity, OnClick click) {
        this.ref = new WeakReference<>(activity);
        this.click = click;
        setWindow();
    }


    private void setWindow() {
        final Activity activity = ref.get();
        final LayoutInflater inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.convertView = inflate.inflate(R.layout.pop_window, null);

        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View  
        this.setContentView(convertView);
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(Color.argb(255, 255, 255, 255));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
//        this.setAnimationStyle(R.style.AnimationPreview);
        TextView tvPhoto = (TextView) convertView.findViewById(R.id.pop_photo);
        TextView tvCamera = (TextView) convertView.findViewById(R.id.pop_camera);
        TextView tvEnter = (TextView) convertView.findViewById(R.id.pop_enter);
        imageView = (ImageView) convertView.findViewById(R.id.pop_iv);

        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                if (activity != null) {
                    activity.startActivityForResult(intent1, Constant.MAIN_OPEN_LIB);
                }

            }
        });

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (activity != null) {
                    activity.startActivityForResult(intent, Constant.MAIN_OPEN_CAMERA);
                }
            }
        });


        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddPopWindow.this.click != null) {
                    click.onClick(v);
                }
            }
        });

    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBmP(Bitmap bmP) {

        Drawable drawable = new BitmapDrawable(bmP);

        imageView.setBackground(drawable);
//        this.imageView.setImageBitmap(bmP);
    }

    public void setImg() {
        this.imageView.setImageResource(R.color.lin_color);
    }

    public interface OnClick {
        void onClick(View view);
    }

}
