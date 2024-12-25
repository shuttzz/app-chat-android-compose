package br.com.badbit.droidchat.ui.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.badbit.droidchat.ui.theme.DroidChatTheme
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.ui.components.PrimaryTextField
import br.com.badbit.droidchat.ui.theme.BackgroundGradient

@Composable
fun SignInRouteUI() {
    SignInScreen()
}

@Composable
fun SignInScreen() {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().background(brush = BackgroundGradient),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(64.dp))

        PrimaryTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholder = stringResource(R.string.feature_login_email),
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholder = stringResource(R.string.feature_login_password),
            leadingIcon = R.drawable.ic_lock,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    }
}

@Preview(device = "id:pixel")
@Composable
private fun SignInScreenPreview() {
    DroidChatTheme {
        SignInScreen()
    }
}