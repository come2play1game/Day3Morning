package com.example.pages.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.constant.RoutePath.test_inflate_async
import com.example.pages.R
import java.util.zip.Inflater

@Route(path = test_inflate_async)
class InflateAsyncAct : AppCompatActivity() {
    lateinit var root: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val thread = Thread {
            root = LayoutInflater.from(this@InflateAsyncAct).inflate(R.layout.activity_inflate_async, null, false) as ViewGroup
            root.setOnClickListener {
                val imageView = ImageView(this@InflateAsyncAct).apply {
                    setImageResource(R.drawable.test_bg)
                }
                root.addView(imageView)
            }
        }
        thread.start()
        thread.join()
        setContentView(root)

    }


}