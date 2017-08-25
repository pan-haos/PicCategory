package com.we.piccategory.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.bean.Task;
import com.we.piccategory.presenter.CollectPresenter;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.view.ICollectView;
import com.we.piccategory.widget.PinView;

import java.lang.ref.WeakReference;

import butterknife.InjectView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/25
 * Time: 22:32
 * Description:
 */
public class CollectFragment extends BaseFragment<ICollectView, CollectPresenter> implements ICollectView {

    @InjectView(R.id.tv_task_num)
    public TextView tvTaskNum;

    @InjectView(R.id.tv_task_comp)
    public TextView tvTaskComp;

    @InjectView(R.id.tv_task_other_credits)
    public TextView getTvCredits;
    //大饼
    private PinView pinView;

    private Task task;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myPresenter.fetch();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int initRes() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void init() {
        //加载数据
        myPresenter.fetch();
    }

    public void reLoad() {
        myPresenter.fetch();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public CollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showTask(Task task) {
        this.task = task;


        //jiu
        int comp = task.getTaskCompleteCount();
        int count = task.getTaskCount();

        float comPercent = comp / (float) count;
        float unComPercent = (count - comp) / (float) count;

        float[] circle = CommonUtil.getCircle(new float[]{comPercent, unComPercent});

        pinView = (PinView) rootRef.get().findViewById(R.id.pin_View);
        pinView.setHumidity(circle);
        pinView.start(2);

        tvTaskNum.setText("" + task.getTaskCount());
        tvTaskComp.setText("" + task.getTaskCompleteCount());
        getTvCredits.setText("" + task.getExtraIntegral());


    }

    private static class TaskHandler extends Handler {
        private WeakReference<CollectFragment> ref;

        public TaskHandler(CollectFragment fragment) {
            this.ref = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i("ph", "handleMessage: -----");
            int what = msg.what;
            if (what == 1) {
                final CollectFragment fragment = ref.get();
                if (fragment == null || fragment.isRemoving()) {
                    removeCallbacksAndMessages(null);
                }
                fragment.pinView = (PinView) fragment.rootRef.get().findViewById(R.id.pin_View);
                fragment.pinView.start(2);
            }
        }
    }

}
