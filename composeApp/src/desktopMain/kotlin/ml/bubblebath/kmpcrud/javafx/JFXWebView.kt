package ml.bubblebath.kmpcrud.javafx

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView

class JFXWebView(url: String) : JFXPanel() {

    init {
        createWebView(url)
    }

    @Suppress("SetJavaScriptEnabled")
    private fun createWebView(url: String) {
        Platform.runLater {
            val webView = WebView()
            with(webView.engine) {
                isJavaScriptEnabled = true
                load(url)
            }
            scene = Scene(webView)
        }
    }
}
