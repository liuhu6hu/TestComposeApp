package com.example.testcomposeapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcomposeapp.data.AccountSummary
import com.example.testcomposeapp.data.TransactionSummary
import com.example.testcomposeapp.ui.AppBarCollapsedHeight
import com.example.testcomposeapp.ui.AppBarExpendedHeight
import com.example.testcomposeapp.ui.swipetorefresh.SwipeToRefresh
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.max
import kotlin.math.min

class HomeActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context) =
            Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val isRefreshing = rememberSaveable { mutableStateOf(false) } //required
                    SwipeToRefresh(
                        isRefreshing = isRefreshing.value,
                        onRefresh = {
                            isRefreshing.value = true
                        },
                        refreshSectionBackgroundColor = MaterialTheme.colors.background,
                        content = {
                            CollapseToolBar()
                        }
                    )
                }
                BackHandler(true) {
                    finish()
                }
            }
        }
    }

    @Composable
    fun CollapseToolBar() {
        val scrollState = rememberLazyListState(1)
        Box {
            AccountInfo(scrollState)
            ProfileToolBar(scrollState)
        }
    }

    @Composable
    fun ProfileToolBar(scrollState: LazyListState) {
        val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight
        val maxOffset = with(LocalDensity.current) {
            imageHeight.roundToPx()
        } - LocalWindowInsets.current.systemBars.layoutInsets.top
        val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
        val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset
        TopAppBar(
            contentPadding = PaddingValues(),
            backgroundColor = Color.White,
            modifier = Modifier
                .height(
                    AppBarExpendedHeight
                )
                .offset {
                    IntOffset(x = 0, y = -offset)
                },
            elevation = if (offset == maxOffset) 4.dp else 0.dp
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .graphicsLayer {
                            alpha = 1f - offsetProgress
                        }
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.Red, Color.Red
                                )
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        accountInformationCard(
                            AccountSummary(
                                "OCBC Savings Account",
                                "123-565-3443",
                                "SGD 100,000.00"
                            )
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppBarCollapsedHeight),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Your Activity",
                        fontSize = 25.sp,
                        color = Color.Black,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = (16 + 28 * offsetProgress).dp)
                            .scale(1f - 0.25f * offsetProgress)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(AppBarCollapsedHeight)
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Gray
                ),
                elevation = ButtonDefaults.elevation(),
                modifier = Modifier
                    .width(38.dp)
                    .height(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    tint = Color.Black
                )
            }
        }
    }

    @Composable
    fun AccountInfo(scrollState: LazyListState) {
        val transactionSummary = TransactionSummary(
            "",
            "14 sep",
            "123",
            "OCBC Savings Account",
            "123-565-3443",
            "description"
        )
        val transactionSummary2 = TransactionSummary(
            "",
            "13 sep",
            "",
            "OCBC Savings Account",
            "123-565-3443",
            "description2"
        )
        val list = mutableListOf<TransactionSummary>(
            transactionSummary,
            transactionSummary,
            transactionSummary,
            transactionSummary,
            transactionSummary,
            transactionSummary2,
            transactionSummary2,
            transactionSummary2,
            transactionSummary2
        )
        TransAction(AppBarExpendedHeight, scrollState, list.groupBy { it.date })
    }

}


