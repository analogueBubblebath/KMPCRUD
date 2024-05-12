package ml.bubblebath.kmpcrud.data

import kotlinx.serialization.Serializable

@Serializable
data class Geolocation(val latitude: Double, val longitude: Double)
