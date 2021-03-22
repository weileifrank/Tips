package com.frank.newapp

import android.R.attr
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator


class BezierView : View {
    val TAG = "http_tag"
    val mPaint by lazy {
        val paint = Paint()
//        paint.setColor(Color.BLACK)
        paint.setColor(Color.BLUE)
        paint.setStyle(Paint.Style.FILL)
//        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(5f)
        paint
    }
    val mPath by lazy {
        val path = Path()
        path
    }
    var mItemWidth = 200;
    var mItemHeight = 100;
    val count = 2

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {

    }

    private var mOffsetX = 0
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged: ")
        mItemWidth = w / count
        mItemHeight = mItemWidth/5
        val mAnimator = ValueAnimator.ofInt(0, mItemWidth);
        mAnimator.addUpdateListener {
            mOffsetX = it.getAnimatedValue() as Int
            invalidate()
        }
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.duration = 1500
        mAnimator.repeatCount = -1
        mAnimator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            mPath.reset()
            val halfItem = mItemWidth / 2;
            mPath.moveTo((-mItemWidth+mOffsetX).toFloat(), halfItem.toFloat())
//            for (index in 1..100 step 2){
//                print(index)//会输出1..3..5......
//            }
            for (index in 0..count) {
                mPath.rQuadTo(halfItem / 2.toFloat(), -mItemHeight.toFloat(), halfItem.toFloat(), 0f)
                mPath.rQuadTo(halfItem / 2.toFloat(), mItemHeight.toFloat(), halfItem.toFloat(), 0f)
            }

            mPath.lineTo(width.toFloat(),height.toFloat())
            mPath.lineTo(0.toFloat(),height.toFloat())
            mPath.close()
            drawPath(mPath, mPaint)
        }
    }
}