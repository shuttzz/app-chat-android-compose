package br.com.badbit.droidchat.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
    val activity = LocalContext.current as? Activity

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
                },
                onNavigateToHome = {
                    Toast.makeText(
                        navController.context,
                        "Navigate to home",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCloseApp = {
                    activity?.finish()
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
            val context = LocalContext.current
            SignInRouteUI(
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                },
                navigateToHome = {
                    Toast.makeText(
                        context,
                        "Navigate to home",
                        Toast.LENGTH_SHORT
                    ).show()
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
            SignUpRouteUI(
                onSignUpSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }

}