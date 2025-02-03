package br.com.badbit.droidchat.ui.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.ui.components.AppDialog
import br.com.badbit.droidchat.ui.theme.BackgroundGradient
import br.com.badbit.droidchat.ui.theme.DroidChatTheme
import kotlinx.coroutines.delay

@Composable
fun SplashRouteUI(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
    onCloseApp: () -> Unit
) {
    SplashScreen()

    /**
     * Dessa maneira como apresentado no método abaixo a gente consegue trabalhar com o ciclo de vida
     */
    LifecycleStartEffect(Unit) {
        viewModel.checkSession()

        /**
         * Esse método é executado quando o ciclo de vida em questão é finalizado
         */
        onStopOrDispose {  }
    }

    LaunchedEffect(Unit) {
        viewModel.authenticationState.collect { authenticationState ->
            when (authenticationState) {
                SplashViewModel.AuthenticationState.UserAuthenticated -> {
                    onNavigateToHome()
                }
                SplashViewModel.AuthenticationState.UserNotAuthenticated -> {
                    onNavigateToSignIn()
                }
            }
        }
    }

    val showErrorDialog = viewModel.showErrorDialogState
    if (showErrorDialog) {
        AppDialog(
            onDismissRequest = {},
            onConfirmButtonClick = {
                viewModel.dismissErrorDialog()
                onCloseApp()
            },
            message = stringResource(R.string.error_message_when_opening_app),
            confirmButtonText = stringResource(R.string.error_confirm_button_close_app)
        )
    }
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = BackgroundGradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo do aplicativo"
        )

        Spacer(modifier = Modifier.height(78.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_safety),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.splash_safety_info),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

@Preview(device = "id:pixel", showBackground = true, backgroundColor = 0xFF181414)
@Composable
private fun SplashScreenPreview() {
    DroidChatTheme {
        SplashScreen()
    }
}