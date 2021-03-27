package com.frank.stepprogressview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class FootCountProgressView : View {
    val tikeTextList = mutableListOf<String>()
    private val tikeGroup = 10
    private var mTikeCount = (tikeTextList.size - 1) * tikeGroup + 1 //刻度的个数
    val mOuterRectF by lazy { RectF() }
    val paintProgressBackground by lazy { Paint() }//进度条背景
    val paintProgress by lazy { Paint() }//进度条颜色

    val paintTike by lazy { Paint() }//刻度
    val paintTikeText by lazy { Paint() }//刻度文字
    val mStrokeWidth = 10.px
    private val START_ARC = 180f
    private val DURING_ARC = 180f
    var percent = 0.8f
        set(value) {
            field = value
            invalidate()
        }
    val tikeLenLong = 50f
    val tikeLenShort = 30f


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        paintProgressBackground.isAntiAlias = true
        paintProgressBackground.strokeWidth = mStrokeWidth
        paintProgressBackground.style = Paint.Style.STROKE
        paintProgressBackground.strokeCap = Paint.Cap.ROUND
        paintProgressBackground.color = Color.WHITE

        paintProgress.isAntiAlias = true
        paintProgress.strokeWidth = mStrokeWidth
        paintProgress.style = Paint.Style.STROKE
        paintProgress.strokeCap = Paint.Cap.ROUND
        paintProgress.color = Color.parseColor("#12C2D8")


        paintTike.isAntiAlias = true
        paintTike.color = Color.WHITE
        paintTike.strokeWidth = 2f

        paintTikeText.isAntiAlias = true
        paintTikeText.textAlign = Paint.Align.CENTER
        paintTikeText.color = Color.WHITE
        paintTikeText.textSize = 12.px.toFloat()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val radius = w / 2f - paintProgressBackground.getStrokeWidth() * 0.5f
        mOuterRectF.left = -radius
        mOuterRectF.top = -radius
        mOuterRectF.right = radius
        mOuterRectF.bottom = radius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawArc(canvas)
    }

    private fun drawArc(canvas: Canvas?) {
        canvas?.apply {
            translate(width / 2f, height / 2f)
            //绘制圆弧
            drawArc(mOuterRectF, START_ARC, DURING_ARC, false, paintProgressBackground)
            if (percent > 1.0f) {
                percent = 1.0f //限制进度条在弹性的作用下不会超出
            }
            if (percent <= 0.0f) {
                percent = 0.0f
            }
            drawArc(mOuterRectF, START_ARC, percent * DURING_ARC, false, paintProgress)

            //绘制刻度
            save() //记录画布状态
            rotate(-(180 - START_ARC + 90), 0f, 0f)
            val numY: Float = -height / 2 + mStrokeWidth * 3 / 2
            val rAngle = DURING_ARC / ((mTikeCount - 1) * 1.0f) //n根线，只需要n-1个区间
            for (i in 0 until mTikeCount) {
                save() //记录画布状态
                rotate(rAngle * i, 0f, 0f)
                if (i == 0 || i % tikeGroup == 0) {
                    drawLine(0f, numY, 0f, numY + tikeLenLong, paintTike) //画长刻度线
                    val text = tikeTextList[i / tikeGroup]
                    val fontMetrics = paintTikeText.fontMetricsInt
                    val baseline = numY + tikeLenLong + (fontMetrics.bottom - fontMetrics.top)
                    drawText(text, -0f, baseline, paintTikeText)
                } else {
                    drawLine(
                        0f,
                        numY.toFloat() + tikeLenLong - tikeLenShort,
                        0f,
                        numY + tikeLenLong,
                        paintTike
                    ) //画短刻度线
                }
                restore()
            }
            restore()

        }
    }

    fun setText(textList: List<String>) {
        tikeTextList.clear()
        tikeTextList.addAll(textList)
        mTikeCount = (tikeTextList.size - 1) * tikeGroup + 1
//        invalidate()
    }

}