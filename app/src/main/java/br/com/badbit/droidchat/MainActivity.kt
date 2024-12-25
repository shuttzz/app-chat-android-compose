package br.com.badbit.droidchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.badbit.droidchat.ui.ChatApp
import br.com.badbit.droidchat.ui.theme.DroidChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidChatTheme {
                ChatApp()
            }
        }
    }
}