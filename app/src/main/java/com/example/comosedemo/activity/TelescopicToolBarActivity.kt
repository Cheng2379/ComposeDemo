package com.example.comosedemo.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comosedemo.R
import com.example.comosedemo.ui.theme.ComposeDemoTheme
import kotlin.math.roundToInt

class TelescopicToolBarActivity : ComponentActivity() {
    private val toolBarHeight = 56.dp
    private val navigationIconSize = 40.dp
    private var scrollableAppBarHeight = 200.dp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDemoTheme {
                val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

                ScrollableAppBar(
                    modifier = Modifier,
                    //.height(toolBarHeight)
                    //.fillMaxWidth()
                    //.padding(start = 10.dp),
                    title = "ScrollBar",
                    backgroundImageId = R.drawable.img_bg,
                    scrollableAppBarHeight = scrollableAppBarHeight,
                    toolbarOffsetHeightPx = toolbarOffsetHeightPx
                )
            }
        }
    }

    @Composable
    fun ScrollableAppBar(
        modifier: Modifier = Modifier,
        title: String = "Title Bar",
        navigationIcon: @Composable () -> Unit = {
            val context = LocalContext.current
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        (context as? Activity)?.finish()
                    },
                imageVector = Icons.Filled.ArrowBack,
                //painter = painterResource(R.drawable.img_back),
                tint = Color.White,
                contentDescription = "ArrowBack Icon",
            )
        }, // 导航图标(默认返回)
        @DrawableRes backgroundImageId: Int = R.drawable.img_bg, // 顶部背景图片
        scrollableAppBarHeight: Dp,         // 动态ScrollableAppBar高度
        toolbarOffsetHeightPx: MutableState<Float> // 上滑偏移量
    ) {
        val density = LocalDensity.current
        val maxOffsetHeightPx = density.run {
            scrollableAppBarHeight.roundToPx().toFloat() - toolBarHeight.roundToPx().toFloat()
        }
        val titleOffsetWidthReferenceValue = density.run {
            navigationIconSize.roundToPx().toFloat()
        }

        // 使用derivedStateOf跟踪滚动位置变化
        // 写法一: 简便
        val lazyListState = rememberLazyListState()
        with(lazyListState) {
            val offset = firstVisibleItemScrollOffset.toFloat()
            val totalOffset =
                (firstVisibleItemIndex * 100f + offset).coerceIn(0f, maxOffsetHeightPx)
            totalOffset.coerceIn(0f, maxOffsetHeightPx)
            toolbarOffsetHeightPx.value = -totalOffset
            totalOffset
        }
        // 写法二: 常规写法
        //val scrollOffset = remember {
        //    derivedStateOf {
        //        val offset = lazyListState.firstVisibleItemScrollOffset.toFloat()
        //        val index = lazyListState.firstVisibleItemIndex
        //        val totalOffset = index * 100f + offset
        //        totalOffset.coerceIn(0f, maxOffsetHeightPx)
        //    }
        //}

        // 当滚动位置变化时更新toolbarOffsetHeightPx
        //LaunchedEffect(scrollOffset.value) {
        //    toolbarOffsetHeightPx.value = -scrollOffset.value
        //}

        val items = mutableListOf<String>()
        for (index in 1 until 31) {
            items.add("item $index")
        }

        // 在Box内部，后面的控件默认会将前面的布局遮住
        // 因此，需要先设置LazyColumn，再设置标题栏部分控件，将列内容列表上面遮住，再给LazyColumn设置顶部外边距为动态滚动的高度
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 内容列表
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = PaddingValues(top = scrollableAppBarHeight)
            ) {
                items(items, key = { it }) { itemData ->
                    ListItem(
                        headlineContent = {
                            Text(itemData)
                        },
                        modifier = Modifier
                            .animateItem()
                            .fillParentMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }
            }

            // 标题栏部分
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scrollableAppBarHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = toolbarOffsetHeightPx.value.roundToInt()
                        )
                    }
                    .fillMaxWidth()
            ) {
                // 背景图片
                Image(
                    painter = painterResource(backgroundImageId),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(scrollableAppBarHeight),
                    contentDescription = "background",
                    contentScale = ContentScale.FillBounds
                )
                // 自定义标题栏
                Row(
                    modifier = modifier
                        .offset {
                            IntOffset(
                                x = 0,
                                y = -toolbarOffsetHeightPx.value.roundToInt()
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically, //设置垂直方向为居中对齐
                ) {
                    // 导航图标
                    Box(
                        modifier = Modifier
                            .size(navigationIconSize),
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon()
                    }
                }
                // title
                Box(
                    Modifier
                        .height(toolBarHeight)
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .offset {
                            IntOffset(
                                x = -((toolbarOffsetHeightPx.value / maxOffsetHeightPx) * titleOffsetWidthReferenceValue - 30).roundToInt(),
                                y = 0
                            )
                        } // 标题栏偏移量
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .matchParentSize(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewTeleScopic() {
        ComposeDemoTheme {
            val context = LocalContext.current
            val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

            ScrollableAppBar(
                modifier = Modifier
                    .height(toolBarHeight)
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                title = "ScrollBar",
                backgroundImageId = R.drawable.img_bg,
                scrollableAppBarHeight = scrollableAppBarHeight,
                toolbarOffsetHeightPx = toolbarOffsetHeightPx
            )
        }
    }
}