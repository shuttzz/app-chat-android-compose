package br.com.badbit.droidchat.ui.feature.signin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.ui.components.AppDialog
import br.com.badbit.droidchat.ui.components.PrimaryButton
import br.com.badbit.droidchat.ui.components.PrimaryTextField
import br.com.badbit.droidchat.ui.theme.BackgroundGradient
import br.com.badbit.droidchat.ui.theme.DroidChatTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInRouteUI(
    viewModel: SignInViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val formState = viewModel.formState

    val genericErrorMessage = stringResource(id = R.string.common_generic_error_message)
    var showUnauthorizedError by remember { mutableStateOf(false) }
    /**
     * O LaunchEffect previne que essa função seja chamada novamente quando houver recomposição
     * ou seja esse estado só pode ser alterado pela viewmodel,como colocamos a key como true
     * então o código dentro desse LaunchEffect só sera executado apenas uma vez se quisessemos
     * que ele mudasse as vezes colocariamos um valor ali que poderia mudar
     */
    LaunchedEffect(true) {
        viewModel.signInActionFlow.collectLatest { action ->
            when (action) {
                SignInViewModel.SignInAction.Success -> {
                    navigateToHome()
                }
                is SignInViewModel.SignInAction.Error -> {
                    when(action) {
                        SignInViewModel.SignInAction.Error.GenericError -> {
                            Toast.makeText(
                                context,
                                genericErrorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        SignInViewModel.SignInAction.Error.UnauthorizedError -> {
                            showUnauthorizedError = true
                        }
                    }
                }
            }
        }
    }

    SignInScreen(
        formState = formState,
        onFormEvent = viewModel::onFormEvent,
        onRegisterClick = navigateToSignUp
    )

    if (showUnauthorizedError) {
        AppDialog(
            onDismissRequest = {
                showUnauthorizedError = false
            },
            onConfirmButtonClick = {
                showUnauthorizedError = false
            },
            title = stringResource(R.string.common_generic_error_title),
            message = stringResource(R.string.error_message_invalid_username_or_password)
        )
    }
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onFormEvent: (SignInFormEvent) -> Unit,
    onRegisterClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(brush = BackgroundGradient),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(78.dp))

        PrimaryTextField(
            value = formState.email,
            onValueChange = {
                onFormEvent(SignInFormEvent.EmailChanged(it))
            },
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            placeholder = stringResource(R.string.feature_login_email),
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Email,
            errorMessage = formState.emailError?.let {
                stringResource(id = it)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        PrimaryTextField(
            value = formState.password,
            onValueChange = {
                onFormEvent(SignInFormEvent.PasswordChanged(it))
            },
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            placeholder = stringResource(R.string.feature_login_password),
            leadingIcon = R.drawable.ic_lock,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            errorMessage = formState.passwordError?.let {
                stringResource(id = it)
            }
        )

        Spacer(modifier = Modifier.height(98.dp))

        PrimaryButton(
            text = stringResource(R.string.feature_login_button),
            onClick = {
                onFormEvent(SignInFormEvent.Submit)
            },
            isLoading = formState.isLoading,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium))
        )

        Spacer(modifier = Modifier.height(56.dp))

        val noAccountText = stringResource(R.string.feature_login_no_account)
        val registerText = stringResource(R.string.feature_login_register)
        val noAccountRegisterText = "$noAccountText $registerText"

        val annotatedString = buildAnnotatedString {
            val registerTextStartIndex = noAccountRegisterText.indexOf(registerText)
            val registerTextEndIndex = registerTextStartIndex + registerText.length

            append(noAccountRegisterText)

            addStyle(
                style = SpanStyle(
                    color = Color.White
                ),
                start = 0,
                end = registerTextStartIndex
            )

            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = "register_text",
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    linkInteractionListener = {
                        onRegisterClick()
                    }
                ),
                start = registerTextStartIndex,
                end = registerTextEndIndex
            )
        }

        Text(text = annotatedString)
    }
}

@Preview(device = "id:pixel")
@Composable
private fun SignInScreenPreview() {
    DroidChatTheme {
        SignInScreen(
            formState = SignInFormState(),
            onFormEvent = {},
            onRegisterClick = {}
        )
    }
}