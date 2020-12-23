package com.u2deom.jetpack.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.constant.RoutePath
import com.u2deom.jetpack.R


@Route(path = RoutePath.jetpack_data_binding)
class SettingAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}