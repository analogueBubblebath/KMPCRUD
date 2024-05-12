import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ml.bubblebath.kmpcrud.di.appModule
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(appModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "KMPCRUD",
    ) {
        App()
    }
}