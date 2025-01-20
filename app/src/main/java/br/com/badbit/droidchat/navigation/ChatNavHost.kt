package br.com.badbit.droidchat.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.badbit.droidchat.navigation.extension.slideInTo
import br.com.badbit.droidchat.navigation.extension.slideOutTo
import br.com.badbit.droidchat.ui.feature.signin.SignInRouteUI
import br.com.badbit.droidchat.ui.feature.splash.SplashRouteUI
import br.com.badbit.droidchat.ui.feature.signup.SignUpRouteUI
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
        composable<SignInRoute>(
            enterTransition = {
                this.slideInTo(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                this.slideOutTo(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            SignInRouteUI(
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                }
            )
        }
        composable<SignUpRoute>(
            enterTransition = {
                this.slideInTo(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                this.slideOutTo(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            SignUpRouteUI()
        }
    }

}