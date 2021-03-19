package com.example.pages.pages

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.commonlib.constant.RoutePath
import com.example.pages.R
import kotlinx.android.synthetic.main.activity_animator_test.*

@Route(path = RoutePath.test_animator)
class AnimatorTestAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_test)
        bt_view_test.setScaleType(ImageView.ScaleType.MATRIX)

//        bt_start_animator.setOnClickListener {
//            for (i in 0 until ll_animator_container.childCount - 1) {
//                ll_animator_container.getChildAt(i).apply {
//                    ObjectAnimator.ofFloat(this, "translationY", 500f)
//                            .apply {
//                                interpolator = LinearInterpolator()
//                                duration = 1000
//                                repeatMode = REVERSE
//                                repeatCount = INFINITE
//                                start()
//                            }
//                }
//            }
//
//        }
//
//
//        for (i in 0 until 100) {
//            val button = Button(this)
//            button.text = "button$i"
//            ll_animator_container.addView(button, LinearLayout.LayoutParams.MATCH_PARENT, 100)
//        }
//        bt_gone_view.setOnClickListener {
//            ll_animator_container.visibility = View.GONE
//        }

        bt_start_animator.setOnClickListener {
            val matrix = Matrix()
            matrix.preScale(2f, 2f)
            matrix.preTranslate(100f, 100f)
            Log.d("238643", matrix.toString())
            bt_view_test.imageMatrix = matrix
        }

    }
}