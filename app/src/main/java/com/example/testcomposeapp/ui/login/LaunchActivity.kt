package com.example.testcomposeapp.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import org.koin.androidx.compose.getViewModel

class LaunchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TestComposeAppTheme {
                val navController = rememberNavController()
                val launchViewModel = getViewModel<LaunchViewModel>()
                NavigationComponent(navController)

                launchViewModel.launchApplication.observe(this) {
                    launchViewModel.isCustomerOnboarded.observe(this) {
                        if (it == true) {
                            navController.navigate("login")
                        } else {
                            navController.navigate("welcome")
                        }
                    }
                }
                navController.navigate("launch")
            }
            BackHandler(true) {
                finish()
            }
        }
    }
}


@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "launch"
    ) {
        composable("launch") {
            LaunchView()
        }
        composable("welcome") {
            val launchViewModel = getViewModel<LaunchViewModel>()
            launchViewModel.setUserOnboarded()
            PagerView(launchViewModel) {
                navController.navigate("login") { popUpTo("welcome") }
            }
        }
        composable("login") {
            loginView()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestComposeAppTheme {
        LaunchView()
    }
}

