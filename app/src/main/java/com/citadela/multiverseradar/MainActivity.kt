package com.citadela.multiverseradar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.citadela.multiverseradar.ui.screen.CharacterScreen
import com.citadela.multiverseradar.ui.theme.MultiverseRadarTheme
import com.citadela.multiverseradar.ui.viewmodel.CharacterViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CharacterViewModel by viewModels { CharacterViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiverseRadarTheme {
                CharacterScreen(viewModel = viewModel)
            }
        }
    }
}
