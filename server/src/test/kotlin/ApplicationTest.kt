import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import ml.bubblebath.kmpcrud.appModule
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.data.Geolocation
import ml.bubblebath.kmpcrud.module
import ml.bubblebath.kmpcrud.service.DataService
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(appModule)
    }

    private val service by inject<DataService>()

    @Test
    fun `create should be 201 Created`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            module()
        }

        val response = client.post("/create") {
            contentType(ContentType.Application.Json)
            setBody(
                Data(
                    geolocation = Geolocation(Random.nextDouble(), Random.nextDouble()),
                )
            )
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `data should be equal with data from service`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            module()
        }

        val response = client.get("/read")
        assertEquals(service.getAllData(), response.body<List<Data>>())
    }

    @Test
    fun `update should return 200 OK`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            module()
        }

        val data = client.get("/read").body<List<Data>>().last()
            .copy(geolocation = Geolocation(111.0, 222.0))
        val response = client.post("/update") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `delete should return 200 OK`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            module()
        }

        val data = client.get("/read").body<List<Data>>()
        val response = client.get("/delete/${data.last().uuid}")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}