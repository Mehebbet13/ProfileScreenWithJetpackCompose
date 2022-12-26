package com.example.profileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profileapp.ui.theme.Blue
import com.example.profileapp.ui.theme.Green
import com.example.profileapp.ui.theme.ProfileAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Blue,
                                Green
                            )
                        )
                    ),
                    color = Color.Transparent
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(PaddingValues(top = 20.dp))

                        ) {
                            AvatarAndName("Mahabbat Jomardli", R.drawable.profile, "Admin")
                        }
                        var offsetX by remember { mutableStateOf(0f) }
                        var offsetY by remember { mutableStateOf(-10f) }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consumeAllChanges()
//                                        offsetX += dragAmount.x
//                                        offsetY += dragAmount.y
                                        Log.e("mike", dragAmount.y.roundToInt().toString())
                                    }
                                }
                                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

                        ) {
                            MoreInfoCard()
                        }
                    }

//                    AsyncImage( // couldn't load image dunno why
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.fotor.com%2Fprofile-picture-maker%2F&psig=AOvVaw2DKt6RXVi2ZZ9v7VLtpDYM&ust=1672138119550000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCLDD3oaOl_wCFQAAAAAdAAAAABAJ")
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = ""
//                    )
                }
            }
        }
    }
}

@Composable
fun AvatarAndName(name: String, avatar: Int, type: String) {
    Image(
        painter = painterResource(avatar),
        contentDescription = "Contact profile picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
    Text(
        text = name,
        modifier = Modifier.padding(PaddingValues(top = 20.dp, bottom = 10.dp)),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Text(text = type)
}

@Composable
fun MoreInfoCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp)
            .height(100.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(painter = painterResource(R.drawable.ic_arrow_drop_up), contentDescription = null)
        }
        Text("Alfred Sisley", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("3 minutes ago", color = Color.Gray)
    }
}

@Composable
@ExperimentalMaterialApi
fun ModalBottomSheetLayout(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetShape: RoundedCornerShape = RoundedCornerShape(16.dp),
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = Color.DarkGray,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    content: @Composable () -> Unit
) {
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileAppTheme {
//        AvatarAndName("Mahabbat Jomardli", R.drawable.profile)
    }
}
