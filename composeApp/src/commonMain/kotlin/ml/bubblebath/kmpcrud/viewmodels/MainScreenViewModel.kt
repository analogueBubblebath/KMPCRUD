package ml.bubblebath.kmpcrud.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.service.repository.DataRepository
import java.net.ConnectException

sealed interface MainScreenState {
    data object Loading : MainScreenState
    data class Success(val dataList: List<Data>) : MainScreenState
    data object Error : MainScreenState
}

class MainScreenViewModel(private val repository: DataRepository) : ScreenModel {
    private val _uiState: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState.Loading)
    val uiState: StateFlow<MainScreenState> = _uiState

    companion object {
        const val REFRESH_INTERVAL = 5000L
    }

    init {
        screenModelScope.launch {
            while (isActive) {
                try {
                    val data = repository.readData()
                    _uiState.value = MainScreenState.Success(data)
                    delay(REFRESH_INTERVAL)
                } catch (e: ConnectException) {
                    _uiState.value = MainScreenState.Error
                }
            }
        }
    }

    fun addRandomData() {
        screenModelScope.launch {
            repository.createRandomData()
        }
    }

    fun deleteData(data: Data) {
        screenModelScope.launch {
            repository.deleteData(data)
        }
    }

    fun updateData(data: Data, latitude: String, longitude: String) {
        screenModelScope.launch {
            repository.updateData(
                data.copy(
                    geolocation = data.geolocation.copy(
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble()
                    )
                )
            )
        }
    }
}