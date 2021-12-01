package com.example.testcomposeapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.data.TransactionSummary
import com.example.testcomposeapp.ui.AppBarExpendedHeight
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransAction(
    top: Dp = 0.dp,
    scrollState: LazyListState,
    grouped: Map<String, List<TransactionSummary>>
) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState) {
        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                grouped.forEach { (initial, contactsForInitial) ->
                        CharacterHeader(initial)
                    contactsForInitial.forEach {
                        ContactListItem(it)
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterHeader(initial: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Black,
            text = initial,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(1.dp)
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.DarkGray)
        )
    }
}

@Composable
fun ContactListItem(contactsForInitial: TransactionSummary) {
    Row(Modifier.padding(8.dp)) {
        Column(
            Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(
                color = Color.Black,
                text = contactsForInitial.payeeName,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                color = Color.DarkGray,
                text = contactsForInitial.payeeAccountNumber,
                style = MaterialTheme.typography.body2
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .border(width = 1.dp, color = Color.White)
            )
            Text(
                color = Color.DarkGray,
                text = contactsForInitial.description,
                style = MaterialTheme.typography.body2
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Magenta,
            text = contactsForInitial.amount,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun transActionPreview() {
    TestComposeAppTheme {
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
            transactionSummary2
        )
        TransAction(
            0.dp,
            rememberLazyListState(),
            list.groupBy { it.date }
        )
    }
}
