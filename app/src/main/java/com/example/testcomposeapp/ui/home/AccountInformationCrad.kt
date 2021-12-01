package com.example.testcomposeapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.R
import com.example.testcomposeapp.data.AccountSummary
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme

@Composable
fun accountInformationCard(
    accountSummary: AccountSummary,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.img_card_account_bg),
            contentDescription = "AccountInformationCard",
            contentScale = ContentScale.FillWidth
        )
        Column {
            Text(
                modifier = Modifier.padding(start = 120.dp, top = 70.dp),
                color = Color.White,
                text = accountSummary.accountName,
                style = typography.h5.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                modifier = Modifier.padding(start = 120.dp),
                color = Color.White,
                text = accountSummary.accountNumber,
                style = typography.body2
            )
            Text(
                modifier = Modifier.padding(start = 24.dp),
                color = Color.White,
                text = "you have ",
                style = typography.body2
            )
            Text(
                modifier = Modifier.padding(start = 24.dp),
                color = Color.White,
                text = accountSummary.balance,
                style = typography.h5.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                modifier = Modifier.padding(start = 24.dp),
                color = Color.White,
                text = "in your account",
                style = typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountInformationCardPreview() {
    TestComposeAppTheme {
        accountInformationCard(
            AccountSummary(
                "OCBC Savings Account",
                "123-565-3443",
                "SGD 100,000.00"
            )
        )
    }
}
