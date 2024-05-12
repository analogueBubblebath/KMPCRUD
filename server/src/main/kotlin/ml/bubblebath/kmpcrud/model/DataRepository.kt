package ml.bubblebath.kmpcrud.model

import ml.bubblebath.kmpcrud.data.Data

interface DataRepository {
    fun getAllData(): List<Data>
    fun addData(data: Data): Boolean
    fun deleteData(uuid: String): Boolean
    fun updateData(data: Data): Boolean
}