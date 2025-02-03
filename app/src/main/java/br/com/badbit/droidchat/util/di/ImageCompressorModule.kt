package br.com.badbit.droidchat.util.di

import br.com.badbit.droidchat.util.image.ImageCompressor
import br.com.badbit.droidchat.util.image.ImageCompressorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ImageCompressorModule {

    @Binds
    fun bindImageCompressor(imageCompressor: ImageCompressorImpl): ImageCompressor

}