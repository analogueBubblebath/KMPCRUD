package ml.bubblebath.kmpcrud.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import ml.bubblebath.kmpcrud.data.Data

class DataDetailsScreen(
    private val data: Data,
    private val onNavigateBack: () -> Unit,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                    title = { Text(data.uuid) })
            },
        ) {
            WebView(modifier = Modifier.fillMaxSize().padding(it), data.yandexMapsUrl())
        }
    }
}