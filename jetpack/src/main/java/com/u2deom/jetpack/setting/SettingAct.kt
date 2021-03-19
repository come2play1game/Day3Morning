package com.u2deom.jetpack.setting

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseAct
import com.example.commonlib.constant.RoutePath
import com.u2deom.jetpack.R
import com.u2deom.jetpack.setting.control.SettingControl
import kotlinx.android.synthetic.main.activity_setting.*


@Route(path = RoutePath.jetpack_data_binding)
class SettingAct : BaseAct() {


    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        super.initView()
        tv_load_settings.setOnClickListener {
            SettingControl().also {

            }


        }
    }



}