package br.com.badbit.droidchat.ui.feature.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.badbit.droidchat.R
import br.com.badbit.droidchat.data.repository.AuthRepository
import br.com.badbit.droidchat.model.CreateAccount
import br.com.badbit.droidchat.model.NetworkException
import br.com.badbit.droidchat.ui.validator.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: FormValidator<SignUpFormState>,
    private val authRepository: AuthRepository
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
            }

            is SignUpFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                updatePasswordExtraText()
            }

            is SignUpFormEvent.PasswordConfirmationChanged -> {
                formState = formState.copy(passwordConfirmation = event.passwordConfirmation)
                updatePasswordExtraText()
            }

            SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = true)
            }

            SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = false)
            }

            SignUpFormEvent.Submit -> {
                doSignUp()
            }
        }
    }

    private fun updatePasswordExtraText() {
        formState = formState.copy(
            passwordExtraText = if (formState.password.isNotEmpty()
                && formState.password == formState.passwordConfirmation) {
                R.string.feature_sign_up_passwords_match
            } else {
                null
            }
        )
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(formState).also {
            formState = it
        }.hasError
    }

    private fun doSignUp() {
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                try {
                    authRepository.signUp(
                        createAccount = CreateAccount(
//                            username = formState.email,
                            username = "",
//                            password = formState.password,
                            password = "",
                            firstName = formState.firstName,
                            lastName = formState.lastName,
//                        profilePictureId = formState.profilePictureUri?.lastPathSegment,
                            profilePictureId = null,
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (e is NetworkException.ApiException) {

                    } else {

                    }
                }
            }
        }
    }

}