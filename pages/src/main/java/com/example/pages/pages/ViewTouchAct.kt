package com.example.pages.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.constant.RoutePath
import com.example.commonlib.utils.ToastUtil
import com.example.pages.R
import kotlinx.android.synthetic.main.activity_view_touch.*

@Route(path = RoutePath.touch_event)
class ViewTouchAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_touch)
        tv_toast.setOnClickListener {
            ToastUtil.showShort("sss")
            Thread.sleep(4000)
            finish()
        }
    }
}