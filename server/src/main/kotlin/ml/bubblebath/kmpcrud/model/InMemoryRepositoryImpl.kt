package ml.bubblebath.kmpcrud.model

import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.data.Geolocation
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.random.Random

class InMemoryRepositoryImpl : DataRepository {
    private val dataList = CopyOnWriteArrayList<Data>().apply {
        repeat(5) {
            add(
                Data(
                    geolocation = Geolocation(Random.nextDouble(-90.0, 90.0), Random.nextDouble(-180.0, 180.0)),
                )
            )
        }
    }

    override fun getAllData(): List<Data> = dataList
    override fun addData(data: Data) = dataList.add(data)
    override fun deleteData(uuid: String): Boolean = try {
        dataList.removeIf { it.uuid == uuid }
    } catch (e: UnsupportedOperationException) {
        false
    }

    override fun updateData(data: Data): Boolean {
        val dataIndex = dataList.indexOfFirst { it.uuid == data.uuid }
        if (dataIndex == -1) return false
        dataList[dataIndex] = data
        return true
    }
}