package ml.bubblebath.kmpcrud.di

import SERVER_IP
import SERVER_PORT
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ml.bubblebath.kmpcrud.service.api.DataApi
import ml.bubblebath.kmpcrud.service.api.DataApiImpl
import ml.bubblebath.kmpcrud.service.repository.DataRepository
import ml.bubblebath.kmpcrud.service.repository.InMemoryDataRepository
import ml.bubblebath.kmpcrud.viewmodels.MainScreenViewModel
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            install(DefaultRequest)

            defaultRequest {
                url("http://$SERVER_IP:$SERVER_PORT/")
            }
        }
    }
    single { CoroutineScope(Dispatchers.IO) }
    single<DataApi> { DataApiImpl(get()) }
    single<DataRepository> { InMemoryDataRepository(get()) }
    factory { MainScreenViewModel(get()) }
}
