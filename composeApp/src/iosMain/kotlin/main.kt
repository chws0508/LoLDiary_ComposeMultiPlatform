import androidx.compose.ui.window.ComposeUIViewController
import com.woosuk.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
