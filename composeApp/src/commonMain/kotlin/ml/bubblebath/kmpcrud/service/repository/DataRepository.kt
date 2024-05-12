package ml.bubblebath.kmpcrud.service.repository

import ml.bubblebath.kmpcrud.data.Data

interface DataRepository {
    suspend fun createData(data: Data)
    suspend fun readData(): List<Data>
    suspend fun updateData(data: Data)
    suspend fun deleteData(data: Data)
    suspend fun createRandomData()
}