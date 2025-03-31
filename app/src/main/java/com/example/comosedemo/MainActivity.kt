package com.example.comosedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comosedemo.activity.CanvasActivity
import com.example.comosedemo.activity.TelescopicToolBarActivity
import com.example.comosedemo.ui.components.AppTopBar
import com.example.comosedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDemoTheme {
                Scaffold(
                    Modifier.fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues()),
                    topBar = {
                        AppTopBar("HomeActivity")
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Greeting()
                        StartActivity()
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting() {
        Row(Modifier.padding(all = 10.dp)) {
            Image(
                painter = painterResource(R.drawable.img_receive_goods),
                contentDescription = "Receive Goods Icon",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    "Column Image",
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            top = 20.dp,
                        )
                        .background(Color.Transparent),
                    style = TextStyle(
                        fontSize = 20.sp,
                        shadow = Shadow(
                            color = Color.Blue,
                            offset = Offset(5f, 10f),
                            blurRadius = 6f
                        )
                    )
                )

                Text(
                    "Column Image 2",
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 10.dp,
                    ),
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }

    @Composable
    fun StartActivity() {
        val context = LocalContext.current
        var showText by remember { mutableStateOf("Column Row2 Greeting Text") }
        Row(Modifier.fillMaxWidth()) {
            Text(
                showText,
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                ),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
            Text(
                "Column Row2 Greeting Text 2",
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                ),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }

        Row {
            FilledTonalButton(
                onClick = {
                    showText = "show Text"
                },
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .width(130.dp)
                    .height(40.dp)
            ) {
                Text("更改数据")
            }
            FilledTonalButton(
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            TelescopicToolBarActivity::class.java
                        )
                    )
                },
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .width(130.dp)
                    .height(40.dp)
            ) {
                Text("Page2")
            }
            FilledTonalButton(
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            CanvasActivity::class.java
                        )
                    )
                },
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .width(150.dp)
                    .height(40.dp)
            ) {
                Text("CanvasActivity")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ComposeDemoTheme {
            Column {
                Greeting()
                StartActivity()
            }
        }
    }
}