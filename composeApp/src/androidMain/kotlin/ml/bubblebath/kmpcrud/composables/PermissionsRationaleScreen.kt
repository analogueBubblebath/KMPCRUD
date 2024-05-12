package ml.bubblebath.kmpcrud.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ml.bubblebath.kmpcrud.PermissionScreenState

@Composable
fun PermissionsRationaleScreen(
    modifier: Modifier = Modifier,
    permissionsState: PermissionScreenState,
    showPermissionsCallback: () -> Unit,
    openAppSettingsCallback: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            Icons.Filled.Warning,
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text("Background permissions needed for LocationWorker")
        Button(onClick = {
            showPermissionsCallback()
        }) {
            if (permissionsState.isFineLocationGranted && permissionsState.isCoarseLocationGranted) {
                Text("Show background permissions dialog")
            } else {
                Text("Show foreground permissions dialog")
            }
        }
        Button(onClick = {
            openAppSettingsCallback()
        }) {
                Text("Open app settings")
        }
    }
}