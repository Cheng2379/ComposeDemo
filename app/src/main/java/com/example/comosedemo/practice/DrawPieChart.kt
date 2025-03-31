package com.example.comosedemo.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt

/**
 *
 * 饼图
 * @author cheng
 * @since 2025/3/26
 */
class DrawPieChart @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var dataList: List<Float> = listOf(10f, 20f, 30f, 40f)

    private val arcPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    /**
     * Canvas drawArc()函数:
     * [startAngle]: 弧形的起始角度(x轴的正向为正右方向，为0度的位置，顺时针为正角度，逆时针为负角度)
     * [sweepAngle]: 弧形划过多少度
     * [useCenter]: 表示是否连接到圆心，不连接到圆心，即为弧形, 否则就是扇形
     *
     */
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor("#546E7B".toColorInt())

        // 基础坐标
        val rectF = RectF(100f, 50f, 400f, 350f)
        // red
        canvas.drawArc(RectF(rectF).apply {
            offset(-10f, -10f)
        }, -60f, -122f, true, arcPaint.apply {
            color = "#EA422D".toColorInt()
        })

        // yellow
        canvas.drawArc(RectF(rectF), 0f, -60f, true, arcPaint.apply {
            color = "#F9C100".toColorInt()
        })

        // blue
        canvas.drawArc(RectF(rectF), -182f, -96f, true, arcPaint.apply {
            color = "#4096F7".toColorInt()
        })

        // green
        canvas.drawArc(RectF(rectF), 32f, 48f, true, arcPaint.apply {
            color = "#309688".toColorInt()
        })

        // grey
        canvas.drawArc(RectF(rectF), 15f, 15f, true, arcPaint.apply {
            color = "#9E9E9E".toColorInt()
        })

        // purple
        canvas.drawArc(RectF(rectF), 3f, 10f, true, arcPaint.apply {
            color = "#9726B3".toColorInt()
        })


    }


}