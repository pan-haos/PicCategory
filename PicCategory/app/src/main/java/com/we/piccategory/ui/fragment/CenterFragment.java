package com.we.piccategory.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.we.piccategory.R;
import com.we.piccategory.adapter.VpFragmentAdapter;
import com.we.piccategory.bean.User;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.CenterPresenter;
import com.we.piccategory.ui.activity.ChangeInfoActivity;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.ICenterView;
import com.we.piccategory.util.ImageUtil;
import com.we.piccategory.widget.CircleImageView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnPageChange;

import static com.we.piccategory.ui.base.BaseApp.context;

/**
 * Created with Android Studio.
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/1
 * Time: 22:51
 * Description:
 */

public class CenterFragment extends BaseFragment<ICenterView, CenterPresenter> implements ICenterView {

    @InjectView(R.id.tv_account)
    public TextView tvAccount;

    @InjectView(R.id.tv_like)
    public TextView tvLike;

    @InjectView(R.id.tv_setting)
    public TextView tvSetting;

    @InjectView(R.id.tv_head)
    public TextView tvHead;

    @InjectView(R.id.vp_bottom)
    public ViewPager vpBottom;

    @InjectView(R.id.view)
    public View line;

    @InjectView(R.id.center_head_img)
    public CircleImageView civ;

    @InjectViews({R.id.tv_account, R.id.tv_like, R.id.tv_setting})
    public List<TextView> tvHeadList;

    private int lineWidth;
    private CollectFragment collectFragment;
    private AccountFragment accountFragment;
    private Bitmap bm;
    private AlertDialog dialog;
    private User user;

    private boolean isLoad = false;


    @Override
    protected int initRes() {
        return R.layout.fragment_center;
    }

    @Override
    protected void init() {
        // 得到下划线的长度并申请布局
        myPresenter.loadImg();//加载顶部头像

        lineWidth = getActivity()
                .getWindowManager().getDefaultDisplay().getWidth() / 3;
        line.requestLayout();
        vpBottom.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        setFragmentAdapter();
    }

    private void setFragmentAdapter() {
        accountFragment = new AccountFragment();
        collectFragment = new CollectFragment();
        SettingFragment settingFragment = new SettingFragment();
        BaseFragment[] fragments = new BaseFragment[]{accountFragment, collectFragment, settingFragment};
        VpFragmentAdapter adapter = new VpFragmentAdapter(getFragmentManager(), fragments);
        vpBottom.setAdapter(adapter);
    }

    @OnClick({R.id.tv_account, R.id.tv_like, R.id.tv_setting, R.id.center_head_img, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account:
                vpBottom.setCurrentItem(0);
                break;
            case R.id.tv_like:
                vpBottom.setCurrentItem(1);
                break;
            case R.id.tv_setting:
                vpBottom.setCurrentItem(2);
                break;
            case R.id.center_head_img:
                showImgDialog();
                break;
            case R.id.tv_edit:
                skip();
                break;
            default:
                Log.i(BaseApp.TAG, "onClick: ");
                break;
        }
    }

    @OnPageChange(value = R.id.vp_bottom, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int state) {

        switch (state) {
            case 0:
                changeState(tvAccount);
                break;
            case 1:
                changeState(tvLike);
                //因为饼状图需要刷新动画所以每次点击会调用
                if (!Constant.isTag) {
                    collectFragment.reLoad();
                    Constant.isTag = true;
                }
                break;
            case 2:
                changeState(tvSetting);
                break;
            default:
                break;
        }
    }

    @OnPageChange(value = R.id.vp_bottom, callback = OnPageChange.Callback.PAGE_SCROLLED)
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float tagerX = position * lineWidth + positionOffsetPixels / 3;
        ViewPropertyAnimator.animate(line).translationX(tagerX)
                .setDuration(0);
    }


    private void changeState(TextView tv) {
        for (TextView text : tvHeadList) {
            if (tv.getId() == text.getId()) {
                text.setTextColor(getResources().getColor(R.color.red));
            } else {
                text.setTextColor(getResources().getColor(R.color.alpPrimary));
            }
        }
    }


    private void skip() {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), ChangeInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", user);
        intent.putExtra("BUNDLER", bundle);
        startActivityForResult(intent, Constant.SKIP_TO_CHANG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.SKIP_TO_CHANG) {
            myPresenter.loadImg();
            accountFragment.refreshData();
        }
        //摄像头
        else if (requestCode == Constant.OPEN_CAMERA_CODE) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bmp = extras.getParcelable("data");
                Uri uri = ImageUtil.saveBitmap(bmp);
                startImgZoom(uri);
            }
            //图库
        } else if (requestCode == Constant.OPEN_LIB_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = ImageUtil.convertUri(uri);
            startImgZoom(fileUri);
        } else if (requestCode == Constant.START_CROP) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            bm = extras.getParcelable("data");
            String img = ImageUtil.base64CodingBmp(bm);
            myPresenter.updateImg(img);
        }
    }

    private void startImgZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constant.START_CROP);
    }


    private void showImgDialog() {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout,
                (ViewGroup) getActivity().findViewById(R.id.dialog_layout));
        TextView tvTakePho = (TextView) layout.findViewById(R.id.tack_photo);
        TextView tvGetSys = (TextView) layout.findViewById(R.id.get_system);
        TvClickListener tvClickListener = new TvClickListener(this);

        tvTakePho.setOnClickListener(tvClickListener);
        tvGetSys.setOnClickListener(tvClickListener);
        dialog = new AlertDialog.Builder(this.getActivity()).setView(layout).show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去背景
    }


    @Override
    public CenterPresenter createPresenter() {
        return new CenterPresenter();
    }

    @Override
    public void setInfo(User user) {
        this.user = user;
        Glide.with(context).load(user.getHeadImageUrl()).fitCenter().centerCrop().into(civ);
        tvHead.setText(user.getUserName());
    }

    @Override
    public Dialog showLoading() {
        DialogBuilder builder = new DialogBuilder();
        builder.attachView(getActivity());
        AlertDialog loadingDialog = builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);

        loadingDialog.show();
        return loadingDialog;
    }

    @Override
    public Dialog showMsg(String msg) {
        DialogBuilder builder = new DialogBuilder();
        builder.attachView(getActivity());
        AlertDialog loadingDialog = builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL);
        loadingDialog.show();
        return loadingDialog;
    }

    @Override
    public void chgImg() {
        this.civ.setImageBitmap(bm);
    }


    private static class TvClickListener implements View.OnClickListener {

        private WeakReference<CenterFragment> ref;

        public TvClickListener(CenterFragment fragment) {
            ref = new WeakReference<>(fragment);
        }

        @Override
        public void onClick(View v) {
            CenterFragment fragment = ref.get();
            switch (v.getId()) {
                case R.id.tack_photo:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (fragment != null) {
                        fragment.startActivityForResult(intent, Constant.OPEN_CAMERA_CODE);
                        fragment.dialog.dismiss();
                    }
                    break;
                case R.id.get_system:
                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                    intent1.setType("image/*");
                    if (fragment != null) {
                        fragment.startActivityForResult(intent1, Constant.OPEN_LIB_CODE);
                        fragment.dialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
