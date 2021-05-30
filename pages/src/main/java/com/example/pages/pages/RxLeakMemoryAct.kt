package com.example.pages.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.constant.RoutePath
import com.example.pages.R
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Route(path = RoutePath.test_memory_leak)
class RxLeakMemoryAct : AppCompatActivity() {
    var subscribe: Disposable? = null
    var a = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_leak_memory)
        subscribe = Observable.create<Int> {

            Log.d("238643", "send  event")
            while (true) {
                Random(System.currentTimeMillis()).nextInt(1000).apply {
                    if (this == 80) {
                        Log.d("238643", "is recycle:${this@RxLeakMemoryAct == null}")
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
                .subscribe {

                    Log.d("238643", "out loop:$it")
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
    }
}