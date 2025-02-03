package br.com.badbit.droidchat.ui.feature.signin

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.data.repository.AuthRepository
import br.com.badbit.droidchat.model.NetworkException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    /**
     * Essa é uma outra maneira de se tratar os estados sem colocar variáveis na nossa classe de estados
     */
    private val _signInActionFlow = MutableSharedFlow<SignInAction>()
    val signInActionFlow = _signInActionFlow.asSharedFlow()

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email, emailError = null)
            }
            is SignInFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password, passwordError = null)
            }
            SignInFormEvent.Submit -> doSignIn()
        }
    }

    private fun doSignIn() {
        var isFormValid = true
        //resetFormErrorState()
        if (formState.email.isBlank()) {
            formState = formState.copy(emailError = R.string.error_message_email_invalid)
            isFormValid = false
        }

        if (formState.password.isBlank()) {
            formState = formState.copy(passwordError = R.string.error_message_password_invalid)
            isFormValid = false
        }
        if (isFormValid) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                authRepository.signIn(
                    username = formState.email,
                    password = formState.password
                ).fold(
                    onSuccess = {
                        formState = formState.copy(isLoading = false)

                        _signInActionFlow.emit(SignInAction.Success)
                    },
                    onFailure = {
                        formState = formState.copy(isLoading = false)

                        val error = if (it is NetworkException.ApiException && it.statusCode == 401) {
                            SignInAction.Error.UnauthorizedError
                        } else {
                            SignInAction.Error.GenericError
                        }

                        _signInActionFlow.emit(error)
                    }
                )
            }
        }
    }

    private fun resetFormErrorState() {
        formState = formState.copy(
            emailError = null,
            passwordError = null
        )
    }

    /**
     * Essa é uma outra maneira de se tratar os estados sem colocar variáveis na nossa classe de estados
     */
    sealed interface SignInAction {
        data object Success : SignInAction
        sealed interface Error : SignInAction {
            data object GenericError : Error
            data object UnauthorizedError : Error
        }
    }
}