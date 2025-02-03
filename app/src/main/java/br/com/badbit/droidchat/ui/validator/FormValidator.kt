package br.com.badbit.droidchat.ui.validator

interface FormValidator<FormState> {
    fun validate(formState: FormState): FormState
}