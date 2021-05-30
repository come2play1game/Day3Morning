package com.example.commonlib.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.commonlib.R
import com.example.commonlib.bean.CommonEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseAct : FragmentActivity() {


    var titleView: TextView? = null
    var backView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        EventBus.getDefault().register(this)
        initView()
    }

    protected abstract fun getLayoutId(): Int

    open fun initView() {
        titleView = findViewById<TextView>(R.id.ict_tv_title)
        backView = findViewById<View>(R.id.ict_iv_back)
        if (titleView != null) {
            titleView!!.text = javaClass.simpleName
        }
        if (backView != null) {
            backView!!.setOnClickListener { view: View? -> finish() }
        }
    }

    open fun setTitle(title: String?) {
        if (titleView != null) {
            titleView!!.text = title
        }
    }


    @Subscribe
    public fun subscribeEvent(event: CommonEvent) {
        onEvent()
    }

    open fun onEvent() {

    }


    open fun jumpToAct(activityClz: Class<*>?) {
        val intent = Intent(this, activityClz)
        startActivity(intent)
    }

    fun hideLeftIcon() {
        backView?.visibility = View.GONE
    }
}