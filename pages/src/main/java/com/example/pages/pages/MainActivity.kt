package com.example.pages.pages

import android.app.AlertDialog
import android.app.Dialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.commonlib.base.BaseAct
import com.example.commonlib.constant.RoutePath
import com.example.commonlib.utils.ToastUtil
import com.example.pages.R
import com.example.pages.control.EntranceAdapter
import com.example.pages.model.EntranceInfo
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseAct() {


    lateinit var adapter: EntranceAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        hideLeftIcon()
        adapter = EntranceAdapter(this)
        adapter.list = listOf(
                EntranceInfo("转场动画", RoutePath.test_transition),
                EntranceInfo("view", RoutePath.test_view),
                EntranceInfo("ListView", RoutePath.test_listview),
                EntranceInfo("dataBinding", RoutePath.jetpack_data_binding),
                EntranceInfo("picture edit", RoutePath.test_animator),
                EntranceInfo("rx memory leak", RoutePath.test_memory_leak),
                EntranceInfo("test_inflate_async", RoutePath.test_inflate_async),
                EntranceInfo("touch event", RoutePath.touch_event)
        )

        lv_entrance_list.layoutManager = LinearLayoutManager(this)
        lv_entrance_list.adapter = adapter
        ll_root.setOnClickListener {
            ToastUtil.showShort("click root")
        }
    }


    override fun onEvent() {
        ARouter.getInstance().build(RoutePath.test_animator).navigation()
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_logout)
//        dialog.show()
    }

}