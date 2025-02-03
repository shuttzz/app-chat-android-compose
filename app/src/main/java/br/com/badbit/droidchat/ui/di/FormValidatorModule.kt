package br.com.badbit.droidchat.ui.di

import br.com.badbit.droidchat.ui.feature.signup.SignUpFormState
import br.com.badbit.droidchat.ui.feature.signup.SignUpFormValidator
import br.com.badbit.droidchat.ui.validator.FormValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FormValidatorModule {

    @Binds
    fun bindSignUpFormValidator(
        signUpFormValidator: SignUpFormValidator
    ): FormValidator<SignUpFormState>

}