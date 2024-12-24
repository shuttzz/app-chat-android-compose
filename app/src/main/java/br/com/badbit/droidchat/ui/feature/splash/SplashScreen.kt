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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.ui.theme.BackgroundGradient
import br.com.badbit.droidchat.ui.theme.DroidChatTheme

@Composable
fun SplashRoute(modifier: Modifier = Modifier) {

    SplashScreen()

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

            val context = LocalContext.current

            Text(
                text = context.getString(R.string.splash_safety_info),
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