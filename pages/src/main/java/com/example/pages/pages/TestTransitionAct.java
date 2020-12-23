package com.example.pages.pages;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseAct;
import com.example.commonlib.constant.RoutePath;
import com.example.pages.R;

@Route(path = RoutePath.test_transition)
public class TestTransitionAct extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_transition;
    }
}