package com.example.comosedemo.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.comosedemo.practice.DrawCircle
import com.example.comosedemo.practice.DrawPieChart
import com.example.comosedemo.practice.DrawTextDemo
import com.example.comosedemo.ui.theme.ComposeDemoTheme

/**
 *
 * @author cheng
 * @since 2025/3/26
 */
class CanvasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DrawCircleDemo()
        }
    }

    @Composable
    fun DrawCircleDemo() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    //DrawCircle(context).apply {
                    //    setBackgroundColor(Color.White.toArgb())
                    //}
                    //DrawPieChart(context)
                    DrawTextDemo(context)
                },
                modifier = Modifier
                    .width(500.dp)
                    .fillMaxHeight(),
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DrawCirclePreview() {
        ComposeDemoTheme {
            DrawCircleDemo()
        }
    }


}