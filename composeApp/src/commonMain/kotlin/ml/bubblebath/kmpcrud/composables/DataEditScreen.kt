package ml.bubblebath.kmpcrud.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import ml.bubblebath.kmpcrud.data.Data

class DataEditScreen(
    private val data: Data,
    private val onNavigateBack: () -> Unit,
    private val onUpdateDataClicked: (String, String) -> Unit,
    private val onDeleteDataClicked: (Data) -> Unit,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var latitude by remember { mutableStateOf(data.geolocation.latitude.toString()) }
        var longitute by remember { mutableStateOf(data.geolocation.longitude.toString()) }
        var isLatitudeValidationError by remember { mutableStateOf(false) }
        var isLongituteValidationError by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                    title = { Text(data.uuid) },
                    actions = {
                        IconButton(onClick = { onDeleteDataClicked(data) }) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                        }
                    })
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Latitude")
                OutlinedTextField(
                    latitude,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newText ->
                        isLatitudeValidationError = newText.toDoubleOrNull() == null
                        latitude = newText
                    },
                    isError = isLatitudeValidationError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )
                Text("Longitude")
                OutlinedTextField(
                    longitute,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newText ->
                        isLongituteValidationError = newText.toDoubleOrNull() == null
                        longitute = newText
                    },
                    isError = isLongituteValidationError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (!isLatitudeValidationError && !isLongituteValidationError) {
                            onUpdateDataClicked(latitude, longitute)
                        }
                    })
                )
                Button(
                    onClick = { onUpdateDataClicked(latitude, longitute) },
                    enabled = !isLatitudeValidationError && !isLongituteValidationError
                ) {
                    Text("Update")
                }
            }
        }
    }
}