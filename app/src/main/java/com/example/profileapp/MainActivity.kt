package com.example.profileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.profileapp.ui.theme.Blue
import com.example.profileapp.ui.theme.ProfileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Blue
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "Contact profile picture",
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.fotor.com%2Fprofile-picture-maker%2F&psig=AOvVaw2DKt6RXVi2ZZ9v7VLtpDYM&ust=1672138119550000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCLDD3oaOl_wCFQAAAAAdAAAAABAJ")
                            .crossfade(true)
                            .build(),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileAppTheme {
        Greeting("Android")
    }
}
