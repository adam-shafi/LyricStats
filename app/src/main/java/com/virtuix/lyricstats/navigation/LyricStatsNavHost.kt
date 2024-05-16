package com.virtuix.lyricstats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.virtuix.lyricstats.definitions.DefinitionsScreen
import com.virtuix.lyricstats.definitions.DefinitionsViewModel
import com.virtuix.lyricstats.main.MainScreen
import com.virtuix.lyricstats.main.MainViewModel

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = listOf()
) {
    object MainScreen : Screen("main_screen")
    object DefinitionsScreen : Screen(
        route = "definitions_screen/{longestWord}-{mostUsedWord}",
        navArguments = listOf(
            navArgument("longestWord") {
                type = NavType.StringType
            },
            navArgument("mostUsedWord") {
                type = NavType.StringType
            }
        )
    ) {
        fun createRoute(longestWord: String, mostUsedWord: String) =
            "definitions_screen/${longestWord}-${mostUsedWord}"
    }
}

@Composable
fun LyricStatsNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            val viewModel = viewModel<MainViewModel>()
            val uiState = viewModel.uiState.collectAsState().value
            MainScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onProcessLyrics = { longestWord, mostUsedWord ->
                    navController.navigate(
                        Screen.DefinitionsScreen.createRoute(
                            longestWord = longestWord,
                            mostUsedWord = mostUsedWord
                        )
                    )
                })
        }

        composable(
            route = Screen.DefinitionsScreen.route,
            arguments = Screen.DefinitionsScreen.navArguments
        ) { backStackEntry ->
            val viewModel =
                viewModel<DefinitionsViewModel>(
                    factory = viewModelFactory {
                        initializer {
                            DefinitionsViewModel(
                                longestWord = checkNotNull(backStackEntry.arguments?.getString("longestWord")),
                                mostUsedWord = checkNotNull(backStackEntry.arguments?.getString("mostUsedWord"))
                            )
                        }
                    }
                )
            val uiState = viewModel.uiState.collectAsState().value

            DefinitionsScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onBackClick = { navController.navigateUp() })
        }
    }
}