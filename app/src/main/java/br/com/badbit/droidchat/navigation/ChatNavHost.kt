package br.com.badbit.droidchat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.badbit.droidchat.ui.feature.signin.SignInRouteUI
import br.com.badbit.droidchat.ui.feature.splash.SplashRouteUI
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object SignInRoute

@Serializable
object SignUpRoute

@Composable
fun ChatNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashRouteUI(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = SignInRoute,
                        navOptions = navOptions {
                            popUpTo(SplashRoute) {
                                inclusive = true
                            }
                        })
                }
            )
        }
        composable<SignInRoute> {
            SignInRouteUI()
        }
        composable<SignUpRoute> {

        }
    }

}