package simple.github.sponsor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}