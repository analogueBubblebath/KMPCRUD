package ml.bubblebath.kmpcrud

import ml.bubblebath.kmpcrud.model.DataRepository
import ml.bubblebath.kmpcrud.model.InMemoryRepositoryImpl
import ml.bubblebath.kmpcrud.service.DataService
import org.koin.dsl.module

val appModule = module {
    single<DataRepository> { InMemoryRepositoryImpl() }
    single { DataService(get()) }
}
