package ml.bubblebath.kmpcrud.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import ml.bubblebath.kmpcrud.javafx.JFXWebView
import java.awt.BorderLayout
import javax.swing.JPanel

@Composable
actual fun WebView(modifier: Modifier, url: String) {
    val jPanel = remember { JPanel(BorderLayout(0, 0)) }

    //Dirty temporal workaround for https://github.com/JetBrains/compose-multiplatform/issues/4642
    val jfxPanel = JFXWebView(url)
    DisposableEffect(url) {
        jPanel.add(jfxPanel)
        onDispose {
            jPanel.remove(jfxPanel)
        }
    }

    SwingPanel(
        factory = {
            jPanel
        },
        modifier = modifier,
    )
}
