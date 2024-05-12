package ml.bubblebath.kmpcrud.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Data(
    val uuid: String = UUID.randomUUID().toString(),
    val geolocation: Geolocation,
) {
    override fun toString() = uuid
    fun yandexMapsUrl() =
        "https://maps.yandex.ru/?text=${geolocation.latitude}+${geolocation.longitude}"
}
