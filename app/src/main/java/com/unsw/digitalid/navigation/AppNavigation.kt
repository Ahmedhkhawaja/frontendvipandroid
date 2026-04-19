package com.unsw.digitalid.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unsw.digitalid.ui.screens.HomeScreen
import com.unsw.digitalid.ui.screens.LoginScreen

// ─── Route constants ─────────────────────────────────────────────────────────
object Routes {
    const val LOGIN = "login"
    const val HOME  = "home"
}

// ─── Root navigation graph ───────────────────────────────────────────────────
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController    = navController,
        startDestination = Routes.LOGIN,
    ) {
        // ── Login ────────────────────────────────────────────────────────────
        composable(
            route = Routes.LOGIN,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards       = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards       = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400),
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            },
        ) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        // Remove login from back-stack so pressing Back exits the app
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
            )
        }

        // ── Home ─────────────────────────────────────────────────────────────
        composable(
            route = Routes.HOME,
            enterTransition = {
                slideIntoContainer(
                    towards       = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400),
                ) + fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
        ) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
            )
        }
    }
}
