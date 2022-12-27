package com.example.profileapp

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profileapp.model.Profile
import com.example.profileapp.ui.theme.Blue
import com.example.profileapp.ui.theme.Green
import com.example.profileapp.ui.theme.ProfileAppTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
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
                            AvatarAndName(
                                "${profile.firstName} ${profile.lastName}",
                                profile.avatarUrl,
                                checkAdmin()
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            BottomSheet()
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

private val profile = Profile()
fun checkAdmin(): String {
    return if (profile.isAdmin) "Admin" else "User"
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
            .height(400.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_drop_up),
                contentDescription = null
            )
        }
        Text(
            "More about me",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            "Swipe up to see more information about me",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        BottomSheetCell("Profile type", checkAdmin())
        BottomSheetCell("My e-mail", profile.email)
        BottomSheetCell("My phone number", profile.telephone)
        BottomSheetCell("My gender", profile.gender)
        BottomSheetCell("My customer number", profile.customerNo)
    }
}

@Composable
private fun BottomSheetCell(title: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 12.dp)) {
        Text("$title : ", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(value, color = Color.Gray)
    }
}

@ExperimentalMaterialApi
@Composable
private fun BottomSheet() {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            MoreInfoCard()
        },
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = (-1).dp,
        backgroundColor = Color.Transparent,
        sheetPeekHeight = 100.dp
    ) {
    }
}
