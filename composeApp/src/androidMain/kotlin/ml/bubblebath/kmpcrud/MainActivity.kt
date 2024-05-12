package ml.bubblebath.kmpcrud

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.tooling.preview.Preview
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.work.*
import ml.bubblebath.kmpcrud.composables.PermissionsRationaleScreen
import ml.bubblebath.kmpcrud.worker.LocationWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { startLocationWorker() }

    private val permissionScreenState: MutableState<PermissionScreenState> by lazy {
        mutableStateOf(getState())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLocationWorker()

        setContent {
            if (permissionScreenState.value.isBackgroundLocationGranted) {
                App()
            } else {
                PermissionsRationaleScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    showPermissionsCallback = ::requestPermissions,
                    openAppSettingsCallback = ::openAppSettings,
                    permissionsState = permissionScreenState.value
                )
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", application.packageName, null)
        }
        startActivity(intent)
    }

    private fun startLocationWorker() {
        val state = getState()
        when {
            state.isBackgroundLocationGranted -> {
                val workManager = WorkManager.getInstance(this)
                workManager.enqueueUniquePeriodicWork(
                    LocationWorker.TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES).build()
                )
            }
        }
    }

    private fun requestPermissions() {
        val state = getState()
        when {
            !state.isCoarseLocationGranted && !state.isFineLocationGranted -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            }

            !state.isBackgroundLocationGranted -> {
                locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            }
        }
    }

    private fun getState(): PermissionScreenState {
        val isCoarseLocationGranted = isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        val isFineLocationGranted = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        val isBackgroundLocationGranted = isPermissionGranted(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        return PermissionScreenState(isCoarseLocationGranted, isFineLocationGranted, isBackgroundLocationGranted)
    }

    private fun isPermissionGranted(name: String) = ContextCompat.checkSelfPermission(
        this, name
    ) == PackageManager.PERMISSION_GRANTED

    override fun onResume() {
        super.onResume()
        permissionScreenState.value = getState()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

data class PermissionScreenState(
    val isCoarseLocationGranted: Boolean,
    val isFineLocationGranted: Boolean,
    val isBackgroundLocationGranted: Boolean
)