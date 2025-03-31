package com.example.comosedemo.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

/**
 *
 * @author cheng
 * @since 2025/3/27
 */
class DrawPaintDemo @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor("#F8F8F8".toColorInt())
        // 渐变
        val toIntArray = listOf(Color.Red, Color.Blue).map { it.toArgb() }.toIntArray()
        val positions = floatArrayOf(0.5f, 1f)
        val shader = LinearGradient(
            100f, 100f, 500f, 500f,
            toIntArray,
            positions,
            Shader.TileMode.MIRROR
        )

        paint.shader = shader
        canvas.drawCircle(400f, 400f, 300f, paint)


        //val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.bitmap_test)
        //val shader1 = BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        //val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.icon_click)
        //val shader2 = BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //
        //val shader = ComposeShader(shader1, shader2,PorterDuff.Mode.SRC_OVER)

        //paint.shader = shader
        //paint.isAntiAlias = true

        //// 模糊效果
        //paint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
        //
        //canvas.drawBitmap(bitmap1, 200f, 500f, paint)
    }

}