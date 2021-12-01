package com.example.testcomposeapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.R
import com.example.testcomposeapp.domain.Result
import com.example.testcomposeapp.domain.Result.Loading
import com.example.testcomposeapp.ui.ShowLoadingDialog
import com.example.testcomposeapp.ui.home.HomeActivity
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun loginView() {
    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        userNameAndPassword()
    }
}

@Composable
fun userNameAndPassword() {
    val cornerSize = CornerSize(40)
    val cornerSizeZero = CornerSize(0)
    val shape = RoundedCornerShape(cornerSize, cornerSizeZero, cornerSize, cornerSizeZero)
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var pasword by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility by remember { mutableStateOf(false) }
    val launchViewModel = getViewModel<LaunchViewModel>()
    val result = launchViewModel.login.observeAsState()
    if (result.value?.peekContent() is Result.Loading) {
        ShowLoadingDialog {}
    } else if (result.value?.peekContent() is Result.Success) {
        LocalContext.current.startActivity(HomeActivity.newIntent(LocalContext.current))
    }
    Column(Modifier.padding(horizontal = 16.dp, vertical = 120.dp)) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.logo_ocbc),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 50.dp)
        )
        Row(
            Modifier
                .padding(top = 32.dp)
                .background(Color.LightGray, shape),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_username_login),
                contentDescription = null
            )
            TextField(
                value = text,
                onValueChange = { newValue ->
                    launchViewModel.userName.value = newValue.text
                    text = newValue
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("UserName") },
                placeholder = { Text("UserName") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color.Red,
                    backgroundColor = Color.Transparent
                )
            )
        }
        Text("Enter your account username", Modifier.padding(start = 8.dp), color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            Modifier.background(Color.LightGray, shape),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_password_login),
                contentDescription = null
            )
            TextField(
                value = pasword,
                onValueChange = { newValue ->
                    launchViewModel.password.value = newValue.text
                    pasword = newValue
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Password") },
                placeholder = { Text("Password") },
                trailingIcon = {
                    val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector = image, "")
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color.Red,
                    backgroundColor = Color.Transparent
                )
            )
        }
        Text("Enter your account password", Modifier.padding(start = 8.dp), color = Color.White)
        loginButton(Modifier.padding(16.dp), onLogin = {
            launchViewModel.login()
        }, onFingerLogin = {})
    }
}

@Composable
fun loginButton(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onFingerLogin: () -> Unit = {}
) {
    val launchViewModel = getViewModel<LaunchViewModel>()
    val isLoginButtonEnable = launchViewModel.isLoginButtonEnable.observeAsState()
    Row(
        modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onLogin,
            Modifier
                .weight(1f)
                .padding(end = 24.dp),
            enabled = isLoginButtonEnable.value ?: false
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = null,
                )
                Text(text = "LOGIN IN")
            }
        }
        IconButton(onClick = onFingerLogin) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    "",
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    tint = MaterialTheme.colors.error.copy(alpha = LocalContentAlpha.current)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun loginViewPreview() {
    TestComposeAppTheme {
        loginView()
    }
}