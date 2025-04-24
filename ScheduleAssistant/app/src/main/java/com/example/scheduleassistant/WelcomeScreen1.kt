package com.example.scheduleassistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.DefaultStrokeLineCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scheduleassistant.ui.theme.MainThColor

@Preview()
@Composable
fun WelcomeScreen1(){

    var currentProgress by remember { mutableFloatStateOf(0.25f) }
    var loading by remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()

    Column (modifier = Modifier.background(color = MainThColor.BlackBackGround).padding(top = 25.dp)){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ){
            LinearProgressIndicator(
                progress = {currentProgress},
                color = MainThColor.ActiveGrey,
//            trackColor = Color.Red,
                strokeCap = DefaultStrokeLineCap,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            )

            TextField(value = "Name", onValueChange = {},
                modifier = Modifier
                    .alpha(0.5f)
                    .background(Color.Transparent, RectangleShape)
                    .padding(10.dp)
            )

            TextField(value = "Name", onValueChange = {},
                modifier = Modifier
                    .alpha(0.5f)
                    .background(Color.Transparent, RectangleShape)
                    .padding(10.dp)
            )

            TextField(value = "Name", onValueChange = {},
                modifier = Modifier
                    .alpha(0.5f)
                    .background(Color.Transparent, RectangleShape)
                    .padding(10.dp)
            )
        }
        Column (verticalArrangement = Arrangement.Bottom , modifier = Modifier.padding(10.dp).fillMaxSize()){
            Row (horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)){
                Button(onClick = {} , colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), shape = RoundedCornerShape(25.dp), border = ButtonDefaults.outlinedButtonBorder, modifier = Modifier.size(width = 150.dp, height = 50.dp)) {
                    Text("Skip" , fontWeight = FontWeight.Medium)
                }
                Button(onClick = {} ,colors = ButtonDefaults.buttonColors(containerColor = MainThColor.ActiveRedOrange), shape = RoundedCornerShape(25.dp), modifier = Modifier.size(width = 150.dp, height = 50.dp)){
                    Text("Proceed", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}