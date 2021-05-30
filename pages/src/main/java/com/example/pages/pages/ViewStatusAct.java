package com.example.pages.pages;

import android.os.Handler;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseAct;
import com.example.commonlib.bean.CommonEvent;
import com.example.commonlib.constant.RoutePath;
import com.example.pages.R;

import org.greenrobot.eventbus.EventBus;


@Route(path = RoutePath.test_view)
public class ViewStatusAct extends BaseAct {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_status;
    }


    public View view;

    public void press() {
        view.setPressed(!view.isPressed());
    }

    @Override
    public void initView() {
        super.initView();
//        new Handler().postDelayed(() -> EventBus.getDefault().post(new CommonEvent()), 2000);
    }
}