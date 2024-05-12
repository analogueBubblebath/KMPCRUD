package ml.bubblebath.kmpcrud.service.api

import ml.bubblebath.kmpcrud.data.Data

interface DataApi {
    suspend fun createData(data: Data): Boolean
    suspend fun readData(): List<Data>
    suspend fun updateData(data: Data): Boolean
    suspend fun deleteData(uuid: String): Boolean
}