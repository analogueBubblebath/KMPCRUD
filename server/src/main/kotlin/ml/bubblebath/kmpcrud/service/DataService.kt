package ml.bubblebath.kmpcrud.service

import ml.bubblebath.kmpcrud.model.DataRepository
import ml.bubblebath.kmpcrud.data.Data

class DataService(private val dataRepository: DataRepository) {
    fun getAllData() = dataRepository.getAllData()
    fun addData(data: Data) = dataRepository.addData(data)
    fun deleteData(uuid: String) = dataRepository.deleteData(uuid)
    fun updateData(data: Data): Boolean = dataRepository.updateData(data)
}