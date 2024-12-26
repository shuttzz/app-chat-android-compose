package br.com.badbit.droidchat.navigation.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInTo(
    direction: AnimatedContentTransitionScope.SlideDirection,
    durationMillis: Int = 300
): EnterTransition {
    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(durationMillis = durationMillis)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutTo(
    direction: AnimatedContentTransitionScope.SlideDirection,
    durationMillis: Int = 300
): ExitTransition {
    return slideOutOfContainer(
        towards = direction,
        animationSpec = tween(durationMillis = durationMillis)
    )
}