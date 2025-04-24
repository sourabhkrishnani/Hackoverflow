package com.example.scheduleassistant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultStrokeLineCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.ui.theme.MainThColor

@Preview
@Composable
fun WelcomeScreen2(){

    var currentProgress by remember { mutableFloatStateOf(0.5f) }
    var loading by remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()

    Box(modifier = Modifier.background(color = MainThColor.BlackBackGround).padding(top = 25.dp)){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
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

            repeat(4){
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly , modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)){
                    HabitCategory("hello", Icons.Default.Star)
                    HabitCategory("hello", Icons.Default.Star)
                }
            }




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


@Composable
fun HabitCategory(text : String , icn : ImageVector ){

    var isSelected  by rememberSaveable { mutableStateOf(false) }
    var btn : Modifier = Modifier.size(150.dp).border(1.dp, color = MainThColor.ActiveRedOrange, shape = RoundedCornerShape(20.dp))
    var color : Color = Color.Transparent

    if(!isSelected){
        btn = Modifier.size(150.dp).border(1.dp, color = MainThColor.InactiveGrey, shape = RoundedCornerShape(20.dp))
        color = MainThColor.InactiveGrey
    }
    Surface(onClick = {isSelected = !isSelected} , color = color,shape = RoundedCornerShape(20.dp), modifier = btn ) {
        text
        icn
    }
}

@Composable
fun TimeSlots(text : String  ){

    var isSelected  by rememberSaveable { mutableStateOf(false) }
    var btn : Modifier = Modifier.size(150.dp).border(1.dp, color = MainThColor.ActiveRedOrange, shape = RoundedCornerShape(20.dp))
    var color : Color = Color.Transparent

    if(!isSelected){
        btn = Modifier.size(150.dp).border(1.dp, color = MainThColor.InactiveGrey, shape = RoundedCornerShape(20.dp))
        color = MainThColor.InactiveGrey
    }
    Surface(onClick = {isSelected = !isSelected} , color = color,shape = RoundedCornerShape(20.dp), modifier = btn ) {
        Text(text = text, fontSize = 30.sp, color = if(isSelected){ MainThColor.ActiveRedOrange} else { MainThColor.TextWhite}, modifier = Modifier.padding(16.dp).wrapContentSize() , textAlign = TextAlign.Center )
    }
}