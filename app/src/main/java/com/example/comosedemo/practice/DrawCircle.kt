package com.example.comosedemo.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt

/**
 * <p>
 * Point(坐标): 表示二维空间的x,y坐标点, 仅存储位置，不参与绘制逻辑
 * <p>
 * Paint(画笔): 定义绘制的样式和属性, 控制绘制, 建议在onDraw之前提前定义好, 防止在绘制页面时重复创建Paint, 从而导致内存泄漏
 * <p>
 * Canvas(画布): 定义画布(画笔的呈现大小、位置等), 所有的Paint都需要Canvas作为载体进行呈现
 *
 * @author cheng
 * @since 2025/3/26
 */
class DrawCircle @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val circlePoint1 = Paint().apply {
        color = Color.BLACK
        strokeWidth = 2f
        style = Paint.Style.FILL
    }
    private val circlePoint2 = Paint().apply {
        color = "#454545".toColorInt()
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val circlePoint3 = Paint().apply {
        color = "#5790E6".toColorInt()
        strokeWidth = 2f
        style = Paint.Style.FILL
    }

    // 外层圈
    private val circlePoint4 = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
        style = Paint.Style.FILL
    }

    // 内层圈
    private val circlePoint5 = Paint().apply {
        color = Color.WHITE
        strokeWidth = 2f
    }

    private val circlePoint6 = Paint().apply {
        color = Color.BLACK
        strokeWidth = 50f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(200f, 150f, 120f, circlePoint1)
        canvas.drawCircle(600f, 150f, 120f, circlePoint2)

        canvas.drawCircle(200f, 400f, 120f, circlePoint3)
        // 实现方式1
        canvas.drawCircle(600f, 400f, 120f, circlePoint4)
        canvas.drawCircle(600f, 400f, 90f, circlePoint5)
        // 实现方式2，设置边框
        canvas.drawCircle(400f, 650f, 120f, circlePoint6)

    }



}