package com.example.pages.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;


public class ScaleLayout extends FrameLayout {

    private View child;
    private Context context;

    public ScaleLayout(@NonNull Context context) {
        this(context, null);
    }

    public ScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    ScaleGestureDetector scaleGestureDetector;

    private void init() {
        initViewDrag();
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                currentScale = preScale * detector.getScaleFactor();
                if (currentScale < minScale) {
                    currentScale = minScale;
                }
                if (currentScale > maxScale) {
                    currentScale = maxScale;
                }
//                ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
//                layoutParams.width *= (currentScale / preScale);
//                layoutParams.height *= (currentScale / preScale);
//                child.setLayoutParams(layoutParams);

                child.setScaleX(currentScale);
                child.setScaleY(currentScale);

//                int childLeft = (int) (-getScrollX() - child.getWidth() * (currentScale - 1));
//                int childR = (int) (childLeft + child.getWidth() * currentScale);
//                int childTop = (int) (-getScrollY() - child.getHeight() * (currentScale - 1));
//                int childB = (int) (childTop + child.getHeight() * currentScale);
//
//
//                int hor = 0;
//                int ver = 0;
//                if (childLeft > 0) {
//                    hor = childLeft;
//                }
//                if (childR < getWidth()) {
//                    hor = getWidth() - childR;
//                }
//
//                if (childTop > 0) {
//                    ver = childTop;
//                }
//                if (childB < getHeight()) {
//                    hor = getHeight() - childB;
//                }
//
//                scrollBy(hor, ver);
//                invalidate();

                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                preScale = currentScale;
                new Handler().postDelayed((Runnable) () -> {
                    System.out.println("s"+child);
                }, 200);

            }
        });

    }

    int lastX;
    int lastY;

    float maxScale = 3;
    float minScale = 1;
    float preScale = 1;
    float currentScale = 1;

    boolean canDrag;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        child = getChildAt(0);
        scaleGestureDetector.onTouchEvent(event);
        if (scaleGestureDetector.isInProgress()) return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //第一根(即pointId为0)手指落下的事件
            case MotionEvent.ACTION_POINTER_DOWN: //非第一根手指落下的事件
                //先从event中获取actionIndex
                //然后根据index获取id,判断是否为第一个手指
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    canDrag = true;
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    lastX = x;
                    lastY = y;
                }
                return true;


            case MotionEvent.ACTION_MOVE:
                if (!canDrag) break;
                //如果可以拖拽
                int x = (int) event.getX(event.findPointerIndex(0));
                int y = (int) event.getY(event.findPointerIndex(0));
                int xDealt = x - lastX;
                int yDealt = y - lastY;
                int nextScrollX = -xDealt + getScrollX();
                int nextScrollY = -yDealt + getScrollY();

                int xScrollBy = xDealt;
                int yScrollBy = yDealt;
                if (nextScrollX < (getWidth() - getWidth() * currentScale) / 2) {
                    nextScrollX = (int) ((getWidth() - getWidth() * currentScale) / 2);
                    xScrollBy = nextScrollX - getScrollX();
                }
                if (nextScrollX > ((currentScale * getWidth() - getWidth()) / 2)) {
                    nextScrollX = (int) ((currentScale * getWidth() - getWidth()) / 2);
                    xScrollBy = nextScrollX - getScrollX();
                }

                if (nextScrollY < (getHeight() - getHeight() * currentScale) / 2) {
                    nextScrollY = (int) ((getHeight() - getHeight() * currentScale) / 2);
                    yScrollBy = nextScrollY - getScrollY();
                }
                if (nextScrollY > (currentScale * getHeight() - getHeight()) / 2) {
                    nextScrollY = (int) ((currentScale * getHeight() - getHeight()) / 2);
                    yScrollBy = nextScrollY - getScrollY();
                }
                scrollBy(-xScrollBy, -yScrollBy);
                lastX = x;
                lastY = y;

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //当第一个手指抬起，屏蔽拖拽
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    canDrag = false;
                }
                break;
        }

        return super.onTouchEvent(event);
    }


    private ViewDragHelper mViewDragHelper;//用于处理子View 的滑动


    private void initViewDrag() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //这里的return true表示自view可以滑动，false表示不处理滑动
                return true;

            }

            /*这里是控制子view左右滑动的回调，child为本自定义view的子控件，left表示意图从手指操作子view从左边界滑动的距离，大于0表示向右移动，小于0表示向左移动，是从手指的移动测出的理论值。方法的返回值表示实际上控件移动的距离，可以用返回值控制边界*/
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //放大后控件的宽度-主布局的宽度 因为View chlid的控件使用的是match_parent,所以直接用父布局的宽取值
                if (left < (getWidth() - getWidth() * currentScale) / 2) {
                    return (int) ((getWidth() - getWidth() * currentScale) / 2);
                }
                if (left > ((currentScale * getWidth() - getWidth()) / 2)) {
                    return (int) ((currentScale * getWidth() - getWidth()) / 2);
                }
                return left;
                /** 这里进行边界处理，因为这里实际最大能放大2倍，那么实际会有3/4的左右滑动空间才会把子View（child）完全划出可见范围，这里为了保证view的可见性，使用1/2确保子view始终在可见范围之内*/
            }

            /*这个方法是控制上下滑动的，跟上面的方法意义一样，只是方向上不一样，top表示从顶部边界计算的距离，向下为正，向上为负*/
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                if (top < (getHeight() - getHeight() * currentScale) / 2) {
                    return ((int) (getHeight() - getHeight() * currentScale) / 2);
                }
                if (top > (currentScale * getHeight() - getHeight()) / 2) {
                    return (int) ((currentScale * getHeight() - getHeight()) / 2);
                }
                return top;
            }
        });
    }
}
