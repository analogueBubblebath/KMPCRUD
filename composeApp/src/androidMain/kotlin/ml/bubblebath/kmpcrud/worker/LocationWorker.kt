package ml.bubblebath.kmpcrud.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ml.bubblebath.kmpcrud.asDataEntity
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.service.repository.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.ConnectException

class LocationWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters), KoinComponent {

    companion object {
        const val TAG = "LocationWorker"
    }

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)
    private val repository: DataRepository by inject()
    private val coroutineScope: CoroutineScope by inject()

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }

        locationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener {
            sendCurrentLocation(it.asDataEntity())
        }
        return Result.success()
    }

    private fun sendCurrentLocation(data: Data) {
        coroutineScope.launch {
            try {
                repository.createData(data)
            } catch (e: ConnectException) {
                Log.e(TAG, "Skip by exception: ${e.message}")
            }
        }
    }
}
