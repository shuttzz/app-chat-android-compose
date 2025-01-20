package br.com.badbit.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.badbit.droidchat.navigation.ChatNavHost

@Composable
fun ChatApp(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {}
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPaddings)
                .padding(innerPaddings)
                .imePadding()
                .fillMaxSize()
        ) {
            ChatNavHost()
        }
    }
}