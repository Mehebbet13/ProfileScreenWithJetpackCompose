package com.example.profileapp

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.example.profileapp.extension.currentFraction
import com.example.profileapp.model.Profile
import com.example.profileapp.ui.theme.Blue
import com.example.profileapp.ui.theme.Gray
import com.example.profileapp.ui.theme.Green
import com.example.profileapp.ui.theme.ProfileAppTheme
import com.example.profileapp.utils.Constants.PROFILE_PIC_ID
import com.example.profileapp.utils.Constants.TYPE
import com.example.profileapp.utils.Constants.USERNAME

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
                    var progress by remember {
                        mutableStateOf(0f)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(PaddingValues(top = 20.dp))

                        ) {
                            ProfileHeader(
                                "${profile.firstName} ${profile.lastName}",
                                profile.avatarUrl,
                                checkAdmin(),
                                progress
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val sheetState = rememberBottomSheetState(
                                initialValue = BottomSheetValue.Collapsed
                            )
                            val scaffoldState = rememberBottomSheetScaffoldState(
                                bottomSheetState = sheetState
                            )
                            progress = scaffoldState.currentFraction
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

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(name: String, avatar: Int, type: String, progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(avatar),
            contentDescription = "Contact profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .layoutId(PROFILE_PIC_ID)
        )
        Text(
            text = name,
            modifier = Modifier.padding(PaddingValues(top = 20.dp, bottom = 10.dp))
                .layoutId(USERNAME),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = type, modifier = Modifier.layoutId(TYPE))
    }
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
            stringResource(R.string.bottomsheet_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            stringResource(R.string.bottomsheet_subtitle),
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        BottomSheetCell(stringResource(R.string.profile_type_title), checkAdmin())
        BottomSheetCell(stringResource(R.string.email_title), profile.email)
        BottomSheetCell(stringResource(R.string.phone_title), profile.telephone)
        BottomSheetCell(stringResource(R.string.gender_title), profile.gender)
        BottomSheetCell(stringResource(R.string.customer_number_title), profile.customerNo)
    }
}

@Composable
private fun BottomSheetCell(title: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 12.dp)) {
        Text("$title : ", color = Color.Black, fontWeight = FontWeight.Bold)
        Text(value, color = Color.Gray)
    }
    Divider(thickness = 1.dp, color = Gray)
    Spacer(modifier = Modifier.size(20.dp))
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
