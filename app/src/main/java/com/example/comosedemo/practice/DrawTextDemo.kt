package com.example.comosedemo.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
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
            .build()

        spannableString.setSpan(
            ForegroundColorSpan(Color.Red.toArgb()),
            targetStartIndex,
            targetEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            targetStartIndex,
            targetEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            // 设置字号为原来的1.5倍
            RelativeSizeSpan(1.5f),
            targetStartIndex,
            targetEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor("#F8F8F8".toColorInt())


        // 绘制文本到画布上，写法一，简便写法
        canvas.withTranslation(50f, 50f) {
            staticLayout.draw(this)
        }
        // 写法二，常规写法
        //canvas.translate(50f, 50f)
        //staticLayout.draw(canvas)
        //canvas.restore()
    }
}