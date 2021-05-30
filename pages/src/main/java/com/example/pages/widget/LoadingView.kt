package com.example.pages.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.max

class LoadingView : View {
    var paint: Paint = Paint()
    var numberOfCircle = 3  //圆圈数量
    var insideCircleRadius = 0f
    var circleMargin = 52 * 3f  //圆圈之间的间距

    var maxRadius = 0f
    var minRadius = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        paint.color = Color.parseColor("#6AA0FF")
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxRadius = max(height / 2, width / 2).toFloat()
        //最小半径即 当最外层碰到边界时，最内层需要移动到的位置。
        // 可以想象调整后的圆圈为这4个 [2',2,1,0]，所以减去numberOfCircle个circleMargin，而非numberOfCircle-1
        minRadius = maxRadius - numberOfCircle * circleMargin.toInt()
        insideCircleRadius = minRadius
    }


    override fun onDraw(canvas: Canvas) {
        var radiusJump = -1f

        for (i in 0 until numberOfCircle) { //画n个圆圈
            var radius = (insideCircleRadius + i * circleMargin)  //第i个圆圈的半径
            if (radius >= maxRadius) { //如果这个圆圈超过了最大半径，说明下一个vsync时要消失了。那么最内层的圆圈要移动
                //向内移动一个margin大小即可。这样向内移动过的0 ，原先的0，原先的1 ---> 新的0,1,2
                radiusJump = insideCircleRadius - circleMargin
                radius = maxRadius.toFloat()
            }

            //不透明度向外递减
            paint.alpha = ((1 - (radius - minRadius) / (maxRadius - minRadius))
                    * 255).toInt()
            if (i == 1) {
                Log.d("238643", "alpha:${paint.alpha},radius:$radius,insideCircleRadius:$insideCircleRadius")
            }
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                    radius, paint)
        }
        if (radiusJump != -1f) {
            insideCircleRadius = radiusJump
        }

        //每1个VSync，向外扩散.这里可以控制增加的速度
        insideCircleRadius += (30 * 2 / 60f)
        invalidate()
    }

}