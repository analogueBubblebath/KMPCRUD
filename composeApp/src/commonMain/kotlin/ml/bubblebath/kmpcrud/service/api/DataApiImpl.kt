package ml.bubblebath.kmpcrud.service.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ml.bubblebath.kmpcrud.data.Data

class DataApiImpl(private val client: HttpClient) : DataApi {
    override suspend fun createData(data: Data): Boolean {
        val response = client.post("create") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun readData(): List<Data> {
        return client.get("read").body<List<Data>>()
    }

    override suspend fun updateData(data: Data): Boolean {
        val response = client.post("update") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun deleteData(uuid: String): Boolean {
        return client.get("delete/$uuid").status == HttpStatusCode.OK
    }
}