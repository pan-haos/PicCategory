package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.we.piccategory.R;
import com.we.piccategory.bean.Label;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.PagePresenter;
import com.we.piccategory.ui.base.BaseActivity;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.view.IPageView;
import com.we.piccategory.widget.AutoScrollView;
import com.we.piccategory.widget.FlowLayout;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.we.piccategory.builder.DialogBuilder.COMMON_MODEL;
import static com.we.piccategory.builder.DialogBuilder.LOADING_MODEL;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/9
 * Time: 9:38
 * Description:打标签的界面
 */
public class PageActivity extends BaseActivity<IPageView, PagePresenter> implements IPageView {

    @InjectView(R.id.exit_page_fl)
    public FlowLayout exitFlowLayout;

    @InjectView(R.id.my_page_fl)
    public FlowLayout mFlowLayout;

    @InjectView(R.id.make_tag)
    public TextView tvTag;

    @InjectView(R.id.et_tag)
    public EditText etTag;

    @InjectView(R.id.page_iv)
    public ImageView ivTag;

    @InjectView(R.id.page_iv_left)
    public ImageView ivLeft;


    @InjectView(R.id.auto_scroll)
    public AutoScrollView autoScroll;

    @InjectView(R.id.top_alpha_layout)
    public RelativeLayout topRel;

    @InjectView(R.id.page_out_layout)
    public RelativeLayout outRel;

    private String url;

    private String label;

    private String myLabel;

    private Label mLabel;


    private boolean flag = false;


    @Override
    protected int initRes() {
        return R.layout.page_layout;
    }

    @Override
    protected void initData() {
        //获取url并根据url获取标签
        url = getIntent().getStringExtra("url");
        label = getIntent().getStringExtra("label");
        if (label != null) {
            List<String> list = CommonUtil.cutStr(label);
            exitFlowLayout.setData(list);
        }
        exitFlowLayout.setClickListener(exitClickListener);
        mFlowLayout.setClickListener(mClickListener);

        //加载我打过的标签
        myPresenter.loadTag(url);
        Glide.with(BaseApp.context).load(url).fitCenter().centerCrop().into(ivTag);
        TextView topTv = (TextView) topRel.findViewById(R.id.page_top_tv);
        //设置头部的scrollview效果
        autoScroll.setTitleAndHead(topRel, outRel, topTv);
    }

    @Override
    public PagePresenter createPresenter() {
        return new PagePresenter();
    }


    @Override
    public void ensureCommit() {
        mLabel = new Label();
        if (myLabel != null) {
            mLabel.setImageLabel(myLabel);
        }
    }

    @Override
    public void skip() {
        this.finish();
    }

    @Override
    public void showMsgDialog(String msg) {
        AlertDialog dialog = builder.createLoadingDialog(msg, COMMON_MODEL);
        dialog.show();
    }

    @Override
    public void showData(Label label) {
        this.mLabel = label;

        //用户没给该图打过标签
        if (label != null) {
            String imageLabel = label.getImageLabel();
            if (imageLabel != null) {
                List<String> list = CommonUtil.cutStr(imageLabel);
                mFlowLayout.setData(list);
                if (label.getLastTime() != null) {
                    //说明已经进入迭代过程不能修改了
                    flag = true;
                    tvTag.setClickable(false);
                }
            }
        }
    }

    @Override
    public Dialog showLoading() {
        AlertDialog dialog = builder.createLoadingDialog("", LOADING_MODEL);
        dialog.show();
        return dialog;
    }

    @OnClick({R.id.make_tag, R.id.page_iv_right, R.id.page_iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.make_tag:
                String tag = etTag.getText().toString();
                if (tag != null && !"".equals(tag)) {
                    if (mFlowLayout.getChildCount() < 5) {
                        String text = etTag.getText().toString().trim();
                        List<String> data = mFlowLayout.getData();
                        if (!CommonUtil.contains(data, text)) {
                            if (tag.length() > 10) {
                                Toast.makeText(BaseApp.context, "标签长度不能超过10个数字", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            etTag.setText("");
                            mFlowLayout.addTextView(text);
                        } else {
                            Toast.makeText(BaseApp.context, "不能打重复的标签哟~~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(BaseApp.context, "不能打更多标签哟", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.page_iv_right:
                if (flag) {
                    builder.createLoadingDialog("你对该图片的标注已经生效，不能修改", DialogBuilder.COMMON_MODEL).show();
                    return;
                }
                List<String> data = mFlowLayout.getData();
                if (data == null || data.size() == 0) {
                    AlertDialog dialog = builder.createLoadingDialog("标签不能为空", DialogBuilder.COMMON_MODEL);
                    dialog.show();
                } else {
                    //提交标签
                    String labs = CommonUtil.changeStrList(mFlowLayout.getData());
                    int userId = LoginManger.getUserId();
                    Label label = new Label(userId, url, labs, null);


                    if (mLabel != null && mLabel.getImageLabel() != null) {
                        //更新方式
                        myPresenter.updateTag(label);
                    } else {
                        //插入方式
                        myPresenter.markTag(label);
                        myLabel = label.getImageLabel();
//                        mLabel = new Label();
//                        mLabel.setImageLabel(label.getImageLabel());
                    }
                }
                break;

            case R.id.page_iv_left:
                this.finish();
                break;
            default:
                break;

        }
    }


    FlowLayout.ClickListener exitClickListener = new FlowLayout.ClickListener() {
        @Override
        public void click(View v) {
            TextView v1 = (TextView) v;
            String text = v1.getText().toString().trim();
            if (flag) {
                builder.createLoadingDialog("你对该图片的标注已经生效，不能修改", DialogBuilder.COMMON_MODEL).show();
                return;
            }

            //当我打的标签不足5个时可以继续打标签
            if (mFlowLayout.getChildCount() < 5) {
                List<String> data = mFlowLayout.getData();
                if (!CommonUtil.contains(data, text)) {
                    mFlowLayout.addTextView(text);
                } else {
                    Toast.makeText(BaseApp.context, "不能打重复的标签哟~~", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(BaseApp.context, "不能打更多标签哟~~", Toast.LENGTH_SHORT).show();
            }
        }
    };

    FlowLayout.ClickListener mClickListener = new FlowLayout.ClickListener() {
        @Override
        public void click(View v) {
            TextView tv = (TextView) v;
            Toast.makeText(PageActivity.this, "我被点击了" + tv.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    };


}
