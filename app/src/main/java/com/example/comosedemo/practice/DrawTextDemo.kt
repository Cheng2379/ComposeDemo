package com.example.comosedemo.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import androidx.core.graphics.withTranslation

/**
 *
 * @author cheng
 * @since 2025/3/27
 */
class DrawTextDemo @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint = TextPaint().apply {
        textSize = 80f
        color = "#000000".toColorInt()
    }
    private val borderPaint = Paint().apply {
        color = Color.Magenta.toArgb() // 边框颜色
        style = Paint.Style.STROKE // 设置为空心
        strokeWidth = 10f // 边框宽度
    }

    private val targetText = "18802"
    private val text = "今日步数 $targetText 步"
    private val targetStartIndex = text.indexOf(targetText)
    private val targetEndIndex = targetStartIndex + targetText.length

    private var staticLayout: StaticLayout
    private val spannableString = SpannableStringBuilder(text)

    init {
        val desiredWidth = (paint.measureText(text) * 1.1).toInt()
        staticLayout = StaticLayout.Builder.obtain(
            spannableString,
            0,
            spannableString.length,
            paint,
            desiredWidth
        ).setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setIncludePad(false)
            .build()

        spannableString.apply {
            val flag = Spannable.SPAN_EXCLUSIVE_INCLUSIVE

            setSpan(
                ForegroundColorSpan(Color.Red.toArgb()),
                targetStartIndex,
                targetEndIndex,
                flag
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                targetStartIndex,
                targetEndIndex,
                flag
            )
            setSpan(
                // 设置字号为原来的1.5倍
                RelativeSizeSpan(1.5f),
                targetStartIndex,
                targetEndIndex,
                flag
            )
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Toast.makeText(context, "步数: $targetText", Toast.LENGTH_SHORT).show()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = false
                    }
                },
                targetStartIndex,
                targetEndIndex,
                flag
            )
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor("#F8F8F8".toColorInt())


        // 绘制文本到画布上，写法一，简便写法
        canvas.withTranslation(50f, 50f) {
            drawRect(
                0f,
                -10f,
                staticLayout.width.toFloat() * 1.2f,
                staticLayout.height.toFloat() * 1.2f,
                borderPaint
            )
            staticLayout.draw(this)
        }
        // 写法二，常规写法
        //canvas.translate(50f, 50f)
        //staticLayout.draw(canvas)
        //canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                // 虽然在canvas的withTranslation作用域中将整个文本和边框向坐标圆点的右下方平移了50个像素点,
                // 点击事件是在用户实际触摸的屏幕位置生效的，这个位置是相对于 View 的 (0, 0) 点来报告的（即 event.x , event.y ）
                // StaticLayout 内部的 坐标系 是从 (0, 0) 开始计算的，我们需要将触摸事件的坐标转换到这个 相对坐标系中。
                // 所以需要将MotionEvent的x, y坐标给恢复到圆点
                val x = event.x - 50f
                val y = event.y - 50f
                if (x >= 0 && x <= staticLayout.width && y >= 0 && y <= staticLayout.height) {
                    val line = staticLayout.getLineForVertical(y.toInt())
                    if (line >= 0) {
                        val offset = staticLayout.getOffsetForHorizontal(line, x)
                        if (offset >= 0 && offset <= spannableString.length) {
                            spannableString.getSpans(offset, offset, ClickableSpan::class.java)
                                ?.takeIf {
                                    it.isNotEmpty()
                                }?.let {
                                    it[0].onClick(this)
                                    return true
                                }
                        }
                    }
                }
            }

            MotionEvent.ACTION_DOWN -> {
                val x = event.x - 50f
                val y = event.y - 50f
                if (x >= 0 && x <= staticLayout.width && y >= 0 && y <= staticLayout.height) {
                    // 如果触摸点可能在文本区域内，返回 true 以继续接收事件
                    return true
                }
            }
        }

        return super.onTouchEvent(event)
    }

}