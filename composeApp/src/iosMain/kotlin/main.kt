import androidx.compose.ui.window.ComposeUIViewController
import com.woosuk.app.App
import com.woosuk.app.registerScreen
import com.woosuk.app.sharedModules
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(sharedModules)
        registerScreen()
    }
}
