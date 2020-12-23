package com.example.pages.pages

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonlib.base.BaseAct
import com.example.commonlib.constant.RoutePath
import com.example.pages.R
import com.example.pages.control.EntranceAdapter
import com.example.pages.model.EntranceInfo
import kotlinx.android.synthetic.main.activity_main.*

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
                EntranceInfo("dataBinding", RoutePath.jetpack_data_binding)
        )

        lv_entrance_list.layoutManager = LinearLayoutManager(this)
        lv_entrance_list.adapter = adapter
    }


}