package ml.bubblebath.kmpcrud

import SERVER_PORT
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.service.DataService
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(CIO, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(Koin) {
        modules(appModule)
    }

    val service by inject<DataService>()

    routing {
        get("/") {
            call.respondText { "Available endpoints: /create /read /update /delete" }
        }
        post("/create") {
            try {
                val data = call.receive<Data>()
                if (service.addData(data)) {
                    call.respondText(status = HttpStatusCode.Created) { "Successfully added" }
                } else {
                    call.respondText(status = HttpStatusCode.InternalServerError) { "Internal server error" }
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Content cannot be transformed to desired type")
            }
        }
        get("/read") {
            call.respond(service.getAllData())
        }
        post("/update") {
            try {
                val data = call.receive<Data>()
                if (service.updateData(data)) {
                    call.respondText(status = HttpStatusCode.OK) { "Successfully updated" }
                } else {
                    call.respondText(status = HttpStatusCode.InternalServerError) { "Internal server error" }
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Content cannot be transformed to desired type")
            }
        }
        get("/delete/{uuid}") {
            call.parameters["uuid"]?.let {
                if (service.deleteData(it)) {
                    call.respondText(status = HttpStatusCode.OK) { "Successfully deleted" }
                } else {
                    call.respondText(status = HttpStatusCode.InternalServerError) { "Internal server error" }
                }
            } ?: call.respond(HttpStatusCode.BadRequest, "Missing uuid parameter")
        }
    }
}