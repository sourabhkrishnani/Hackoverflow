package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.PrimaryGreen

@Composable
fun GetStartedScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(Color.Black)){
                        append("Eat better, \nLive ")
                    }

                    withStyle(style = SpanStyle(color = PrimaryGreen)) {
                        append("healthier")
                    }
                },
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(horizontal = 24.dp).padding(top = 34.dp),
            )

            Text(
                text = "your nutrition, \nyour way.",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 40.dp)
            )

            // Get Started Button
            Button(
                onClick = {
                    try {
                        onGetStarted()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // In a real app, you would show an error message to the user
                    }
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Get started",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = "Arrow Forward",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            // Footer with teal background
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column (verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxSize()){
                    Surface (shape = RoundedCornerShape(topStart = 500.dp), modifier = Modifier.size(300.dp), color = PrimaryGreen){  }

                }
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {

//                  Small icons
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(60.dp))
                        Image(
                            painter = painterResource(id = R.drawable.get_started_main_food),
                            contentDescription = "Food 1",
                            modifier = Modifier
                                .size(75.dp)
                                .padding(2.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.get_started_sub1),
                            contentDescription = "Food 2",
                            modifier = Modifier
                                .size(75.dp)
                                .padding(2.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.get_started_sub2),
                            contentDescription = "Food 3",
                            modifier = Modifier
                                .size(75.dp)
                                .padding(2.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.get_started_sub3),
                            contentDescription = "Food 4",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(2.dp)
                        )
                    }

                    // Food bowl image
                    Image(
                        painter = painterResource(id = R.drawable.get_started_main_food),
                        contentDescription = "Healthy Food",
                        modifier = Modifier.size(900.dp)
                    )
                }

            }
            


            
            Spacer(modifier = Modifier.weight(1f))
            
            // Small images at the bottom

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GetStartedScreenPreview() {
    DrBhartiTheme {
        GetStartedScreen(onGetStarted = {})
    }
}
