package com.example.introtoandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.introtoandroid.ui.theme.IntroToAndroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntroToAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImageGallery(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ImageGallery(modifier: Modifier = Modifier) {
    val pictures = listOf(
        R.drawable.dbz1,
        R.drawable.dbz2,
        R.drawable.dbz3,
        R.drawable.dbz4,
        R.drawable.dbz5,
        R.drawable.dbz6,
        R.drawable.dbz7,
        R.drawable.dbz8,
    )
    val pagerState = rememberPagerState { pictures.size }
    val coroutineScope = rememberCoroutineScope()

    Box {
        // renders horizontal imageview
        HorizontalPager(
            state = pagerState,

        ) { picture ->
            ImageComponent(
                modifier = Modifier.fillMaxSize(),
                currentImageId = pictures[picture]
            )
        }

        // Button for navigating to previous image
        ButtonComponent(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage - 1,
                    )
                }
            },
            modifier = Modifier.align(Alignment.BottomStart),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            enabled = pagerState.currentPage > 0
        )

        // Button for navigating to next image
        ButtonComponent(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1,
                    )
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd),
            icon = Icons.AutoMirrored.Default.KeyboardArrowRight,
            enabled = pagerState.currentPage < pictures.size - 1
        )
    }
}

@Composable
fun ImageComponent(modifier: Modifier, currentImageId: Int) {
    Image(
        painter = painterResource(currentImageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Composable
fun ButtonComponent(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    enabled: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black).padding(16.dp),
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntroToAndroidTheme {
        ImageGallery()
    }
}