package com.unsw.digitalid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.unsw.digitalid.navigation.AppNavigation
import com.unsw.digitalid.ui.theme.AppTheme
import com.unsw.digitalid.ui.theme.UnswOffWhite

/**
 * Single-activity entry point.
 *
 * Hosts the full Navigation graph inside [AppTheme].
 * All screen transitions are managed by [AppNavigation].
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extend drawing behind system bars for full immersive look
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = UnswOffWhite,
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
