package com.example.testcomposeapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.testcomposeapp.R
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
fun PagerView(launchViewModel: LaunchViewModel, onClick: () -> Unit = {}) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        horizontalPagerIndicator(launchViewModel,onClick)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun horizontalPagerIndicator(launchViewModel: LaunchViewModel, onClick: () -> Unit = {}) {
    val drawableResId = launchViewModel.drawableResId.observeAsState()
    ConstraintLayout(
        modifier = Modifier.wrapContentSize()
    ) {
        val (button, pager, horizontalPagerIndicator) = createRefs()
        val pagerState = rememberPagerState()
        Button(
            modifier = Modifier
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }
                .width(330.dp)

                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            onClick = onClick
        ) {
            Text("GET START")
        }
        HorizontalPager(
            state = pagerState,
            count = drawableResId.value?.size ?: 0,
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .constrainAs(pager) {
                    centerTo(parent)
                    bottom.linkTo(button.top)
                }
                .width(330.dp)
                .height(510.dp)
        ) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxSize()
            ) {
                Box {
                    Image(
                        painter = painterResource(id = drawableResId.value?.get(page) ?: 0),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
        HorizontalPagerIndicator(
            indicatorWidth = 12.dp,
            pagerState = pagerState,
            activeColor = Color.White,
            modifier = Modifier
                .constrainAs(horizontalPagerIndicator) {
                    bottom.linkTo(pager.bottom)
                    centerHorizontallyTo(parent)
                }
                .padding(16.dp),
        )

    }
}