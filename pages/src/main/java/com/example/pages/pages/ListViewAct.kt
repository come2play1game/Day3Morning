package com.example.pages.pages

import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.base.BaseAct
import com.example.commonlib.constant.RoutePath
import com.example.pages.R
import com.example.pages.control.PersonAdapter
import kotlinx.android.synthetic.main.activity_list_view.*

@Route(path = RoutePath.test_listview)
class ListViewAct : BaseAct() {


    override fun getLayoutId(): Int {
        return R.layout.activity_list_view
    }


    override fun initView() {
        super.initView()


        IntArray(2) { it }  //创建整型数组(基本类型)，

        Array(2) { it }    //创建对象数组(包装类型)，
        arrayOf("s", "dd")  //接收多个参数，来创建对象数组，底层使用了Array


        lv_test_list_view.adapter = ArrayAdapter<String>(this, R.layout.item_test_list_view,
                Array(20) { "$it index" })

        PersonAdapter.MyObj.doMyObj()


    }

    fun main(vararg args: String) {


    }
}