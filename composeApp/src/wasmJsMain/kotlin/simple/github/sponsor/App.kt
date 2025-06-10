package simple.github.sponsor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DataConversion.install
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    MaterialTheme {

        var imageList by remember { mutableStateOf<List<String>>(emptyList()) }

        LaunchedEffect(Unit) {
            val url = "https://api.github.com/repos/simplepeng/Sponsor/contents/pictures"
            HttpClient() {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }.use {
                val response = it.get(url)
                val result: List<Result> = withContext(Dispatchers.Default) { response.body() }
//                val result: String = withContext(Dispatchers.Default) { response.body() }
//                println(result)
                imageList = result.map { it.download_url }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageList.isEmpty()) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Text(
                        text = "Thank you for your support !",
                        fontWeight = FontWeight.Medium,
                        fontSize = 45.sp,
                        fontFamily = FontFamily.Cursive
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        items(imageList) { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "image",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.2f)
                    .align(Alignment.TopStart),
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_alipay.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.FillBounds
                )
                AsyncImage(
                    model = "https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_wxpay.png",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .align(Alignment.TopEnd),
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_qqpay.png",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}