package com.example.pages.pages;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseAct;
import com.example.commonlib.constant.RoutePath;
import com.example.pages.R;
import com.example.pages.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RoutePath.test_view)
public class ViewStatusAct extends BaseAct {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_status;
    }


    @BindView(R2.id.v_test_status)
    public View view;

    @OnClick(R2.id.v_test_pressed)
    public void press() {
        view.setPressed(!view.isPressed());
    }
}