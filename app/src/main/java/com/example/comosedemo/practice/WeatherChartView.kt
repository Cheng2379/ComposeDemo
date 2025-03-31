package com.example.comosedemo.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

/**
 * 自定义天气折线图
 * @author cheng
 * @since 2025/3/24
 */
@SuppressLint("ViewConstructor")
class WeatherChartView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val temperatures = listOf(21, 25, 20, 23, 19, 25, 21)
    private val dates = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // 选中的节点索引
    private var selectedIndex = -1

    // 折线线条样式
    private val linePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    // 设置线条端点为圆形的线帽
    private val pointPoint = Paint().apply {
        color = Color.RED
        strokeWidth = 12f
        strokeCap = Paint.Cap.ROUND
        // 开启抗锯齿
        isAntiAlias = true
    }

    // text样式
    private val textPoint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
        textAlign = Paint.Align.CENTER
        // 向右倾斜
        textSkewX = -0.5f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawChart(canvas)
    }

    private fun drawChart(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        val linePadding = 80f
        val offsetX = 25f

        // 绘制X轴
        canvas.drawLine(
            linePadding,
            height - linePadding,
            width - (linePadding / 2),
            height - linePadding,
            linePaint
        )
        // 绘制Y轴
        canvas.drawLine(linePadding, linePadding, linePadding, height - linePadding, linePaint)

        // 计算每个数据的坐标
        val maxTemp = temperatures.maxOrNull() ?: 0
        val minTemp = temperatures.minOrNull() ?: 0
        // (400 - 2 * 50) / 6
        val stepX = (width - 2 * linePadding) / (temperatures.size - 1)
        val scaleY = (height - 2 * linePadding) / (maxTemp - minTemp + 10)

        // 计算每个折线的结束坐标(起点为上一个数据点坐标)
        val points = temperatures.mapIndexed { index, temp ->
            val x = linePadding + index * stepX + offsetX
            val y = height - linePadding - (temp - minTemp + 5) * scaleY
            PointF(x, y)
        }

        // 绘制折线
        val path = Path()
        points.forEachIndexed { index, pointF ->
            if (index == 0) path.moveTo(pointF.x, pointF.y)
            else path.lineTo(pointF.x, pointF.y)
        }
        canvas.drawPath(path, linePaint)

        // 绘制温度点数据和文本
        points.forEachIndexed { index, pointF ->
            // 温度点线帽
            canvas.drawPoint(pointF.x, pointF.y, pointPoint)
            // 温度点数据文本
            canvas.drawText("${temperatures[index]}°", pointF.x, pointF.y - 20, textPoint)
            // 星期数文本
            canvas.drawText(dates[index], pointF.x, height - linePadding + 40, textPoint)
        }

        // 修改选中的节点样式
        selectedIndex.takeIf {
            it != -1
        }?.let {
            val pointF = points[selectedIndex]
            canvas.drawCircle(pointF.x, pointF.y, 10f, Paint().apply {
                color = Color.BLUE
            })
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                selectedIndex = temperatures.indices.minByOrNull { index ->
                    val pointX = 80f + index * (width - 2 * 80f) / (temperatures.size - 1)
                    abs(event.x - pointX)
                } ?: -1
                // 触发重绘
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }


}