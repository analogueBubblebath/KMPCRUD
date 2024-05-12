package ml.bubblebath.kmpcrud.service.repository

import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.data.Geolocation
import ml.bubblebath.kmpcrud.service.api.DataApi
import kotlin.random.Random

class InMemoryDataRepository(private val api: DataApi) : DataRepository {
    //todo Cache
    override suspend fun createData(data: Data) {
        api.createData(data)
    }

    override suspend fun readData(): List<Data> {
        return api.readData()
    }

    override suspend fun updateData(data: Data) {
        api.updateData(data)
    }

    override suspend fun deleteData(data: Data) {
        api.deleteData(data.uuid)
    }

    override suspend fun createRandomData() {
        api.createData(Data(geolocation = Geolocation(Random.nextDouble(), Random.nextDouble())))
    }
}
