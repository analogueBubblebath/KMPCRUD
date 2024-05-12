package ml.bubblebath.kmpcrud

import android.location.Location
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.data.Geolocation

fun Location.asDataEntity() = Data(geolocation = Geolocation(latitude = this.latitude, longitude = this.longitude))
