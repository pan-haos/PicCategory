package com.we.piccategory.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.we.piccategory.R;
import com.we.piccategory.adapter.RecordFragmentAdapter;
import com.we.piccategory.bean.Record;
import com.we.piccategory.presenter.RecordPresenter;
import com.we.piccategory.ui.base.BaseActivity;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.ui.fragment.RecordComFragment;
import com.we.piccategory.ui.fragment.RecordFragment;
import com.we.piccategory.ui.fragment.RecordWaitFragment;
import com.we.piccategory.view.IRecordView;

import java.util.List;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/25
 * Time: 20:49
 * Description:
 */
public class RecordActivity extends BaseActivity<IRecordView, RecordPresenter> implements IRecordView {

    @InjectView(R.id.record_iv_back)
    public ImageView ivBack;

    @InjectView(R.id.record_tv_all)
    public TextView tvAll;

    @InjectView(R.id.record_tv_finish)
    public TextView tvComp;

    @InjectView(R.id.record_tv_wait)
    public TextView tvWait;

    @InjectView(R.id.record_vp)
    public ViewPager vpBottom;

    @InjectViews({R.id.record_tv_all, R.id.record_tv_finish, R.id.record_tv_wait})
    public List<TextView> tvHeadList;

    @InjectView(R.id.line_view)
    public View line;

    private int lineWidth;
    private RecordFragment fragmentAll;
    private RecordComFragment fragmentCom;
    private RecordWaitFragment fragmentWait;

    private boolean isCom = false;
    private boolean isWait = false;


    @Override
    protected int initRes() {
        return R.layout.activity_record;
    }

    @Override
    public RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected void initData() {
        //设置界面逻辑
        lineWidth = this.getWindowManager().getDefaultDisplay().getWidth() / 4;
        line.requestLayout();
        vpBottom.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        setFragment();
    }

    private void setFragment() {
        fragmentAll = new RecordFragment();
        fragmentCom = new RecordComFragment();
        fragmentWait = new RecordWaitFragment();
        BaseFragment[] fragments = {fragmentAll, fragmentCom, fragmentWait};
        RecordFragmentAdapter adapter = new RecordFragmentAdapter(getSupportFragmentManager(), fragments);
        vpBottom.setAdapter(adapter);
    }


    @OnClick({R.id.record_tv_all, R.id.record_tv_finish, R.id.record_tv_wait})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_tv_all:
                vpBottom.setCurrentItem(0);
                break;
            case R.id.record_tv_finish:
                vpBottom.setCurrentItem(1);
                break;
            case R.id.record_tv_wait:
                vpBottom.setCurrentItem(2);
                break;
            default:
                break;
        }
    }


    @OnPageChange(value = R.id.record_vp, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int state) {
        switch (state) {
            case 0:
                changeState(tvAll);
                break;
            case 1:
                changeState(tvComp);
                if (isCom == false) {
                    isCom = true;
                    fragmentCom.load();
                }
                break;
            case 2:
                changeState(tvWait);
                if (isWait == false) {
                    isWait = true;
                }
                fragmentWait.load();
                break;
            default:
                break;
        }
    }

    @OnPageChange(value = R.id.record_vp, callback = OnPageChange.Callback.PAGE_SCROLLED)
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float tagerX = position * lineWidth + positionOffsetPixels / 4;
        ViewPropertyAnimator.animate(line).translationX(tagerX)
                .setDuration(0);
    }


    private void changeState(TextView tv) {
        for (TextView text : tvHeadList) {
            if (tv.getId() == text.getId()) {
                System.out.println(text.toString());
                text.setTextColor(getResources().getColor(R.color.red));
            } else {
                text.setTextColor(getResources().getColor(R.color.alpPrimary));
            }
        }
    }

    @Override
    public void loadData(List<Record> list) {


    }

    @Override
    public void addData(List<Record> list) {

    }

    @Override
    public void showMsg() {
    }
}
