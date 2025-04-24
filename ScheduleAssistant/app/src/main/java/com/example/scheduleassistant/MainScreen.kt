package com.example.scheduleassistant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.DisplayDay
import com.example.scheduleassistant.ui.theme.MainThColor
import com.example.scheduleassistant.ui.theme.MainThColor.TextWhite
import java.util.Calendar

@Preview
@Composable
fun MainScreen(){
    Box(modifier = Modifier.fillMaxSize()){

        Column (modifier = Modifier
            .background(color = MainThColor.BlackBackGround)
            .padding(horizontal = 20.dp)
            .padding(top = 25.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ){

            var day = Calendar.DAY_OF_MONTH

            Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Today,", color = MainThColor.TextWhite, fontSize = 24.sp , fontWeight = FontWeight.SemiBold)
                Text("February 7" , color = MainThColor.TextWhite, fontSize = 30.sp , fontWeight = FontWeight.Bold )
                Surface(onClick = {} , shape = CircleShape, color = MainThColor.ActiveGrey, modifier = Modifier.size(35.dp)) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "Icon", tint = MainThColor.TextWhite , modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            DisplayWeek(startingDay = 7 , 13)

            Text(text = "My habits", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MainThColor.TextWhite, modifier = Modifier.padding(vertical = 16.dp))

            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
            TaskCard()
        }

        Column (verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ){
                IconButton(onClick = {})
                {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon", tint = MainThColor.TextWhite)
                }
                Surface (color = MainThColor.ActiveRedOrange , shape = CircleShape, modifier = Modifier.size(50.dp)){
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon", tint = MainThColor.TextWhite )
                }
                IconButton(onClick = {})
                {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Info Icon", tint = MainThColor.TextWhite)
                }
            }
        }

    }
}

//@Preview
@Composable
fun DisplayDay(isActive : Boolean = false ,day : Char = 'F',date : Int = 14){


    val BGColor = if (isActive) { Color.Transparent } else{MainThColor.ActiveGrey}
    val TXTColor = if (isActive) { MainThColor.TextWhite } else{MainThColor.TextGrey}
    val BdrColor = if (isActive) { MainThColor.ActiveRedOrange } else{MainThColor.ActiveGrey}


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = day.toString(), fontSize = 18.sp , color = TXTColor)
        Spacer(Modifier.height(5.dp))
        Surface (color = BGColor , shape = CircleShape , border = BorderStroke(1.dp, color = BdrColor), modifier = Modifier.size(35.dp)){
            Text(text = date.toString(), textAlign = TextAlign.Center, fontSize = 18.sp, color = TXTColor, modifier = Modifier.padding(5.dp) )
        }
    }
}

@Preview
@Composable
fun DisplayDay(isActive: Boolean = false ,day : Char = 'F'){
    
    var isSelected by rememberSaveable { mutableStateOf(isActive) }

    val BGColor = if (isSelected) { Color.Transparent } else{MainThColor.ActiveGrey}
    val TXTColor = if (isSelected) { MainThColor.TextWhite } else{MainThColor.TextGrey}
    val BdrColor = if (isSelected) { MainThColor.ActiveRedOrange } else{MainThColor.ActiveGrey}


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Surface (color = BGColor , shape = CircleShape , border = BorderStroke(1.dp, color = BdrColor), modifier = Modifier
            .size(35.dp)
            .clickable(onClick = { isSelected = !isSelected })){
            Text(text = day.toString(), textAlign = TextAlign.Center, fontSize = 18.sp, color = TXTColor, modifier = Modifier.padding(5.dp) )
        }
    }
}

@Preview
@Composable
fun DisplayWeek(startingDay : Int = 23, todayDate : Int = 24 ){

    var date = startingDay
    var dateSelected : Int by rememberSaveable { mutableStateOf(todayDate) }

    Row(horizontalArrangement = Arrangement.SpaceBetween ,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ){
        var DaysOfWeek = listOf(
            DisplayDay(day = 'S', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'M', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'T', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'W', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'T', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'F', date = date++, isActive = date-1 == dateSelected),
            DisplayDay(day = 'S', date = date++, isActive = date-1 == dateSelected),
        )
    }

}

@Preview
@Composable
fun DisplayWeek(){

    var selected = rememberSaveable { mutableStateOf(true) }

    Row(horizontalArrangement = Arrangement.SpaceBetween ,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ){
        var DaysOfWeek = listOf(
            DisplayDay(day = 'S', isActive = false),
            DisplayDay(day = 'M', isActive = false),
            DisplayDay(day = 'T', isActive = false),
            DisplayDay(day = 'W', isActive = false),
            DisplayDay(day = 'T', isActive = false),
            DisplayDay(day = 'F', isActive = false),
            DisplayDay(day = 'S', isActive = false),
        )
    }

}

@Preview
@Composable
fun TaskCard(){

    var check by rememberSaveable { mutableStateOf(true) }
    var checkColor = if (check){
        MainThColor.ActiveGrey
    }else{
        MainThColor.InactiveGrey
    }

    Column (verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ){
        val titleTxt = "Read 10 pages of a book"
        val detailTxt = "Read 10 pages of a book"
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = titleTxt, fontSize = 25.sp, fontWeight = FontWeight.SemiBold, color = MainThColor.TextGrey, modifier = Modifier.padding(vertical = 8.dp))
            Surface (modifier = Modifier.size(25.dp), shape = CircleShape, color = checkColor, onClick = {check = !check}){
                var checkIcon = if (check){
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Tick Icon", tint = TextWhite, modifier = Modifier.padding(2.dp))
                }else{
                }
            }
        }
        Text(text = detailTxt, fontSize = 20.sp, color = MainThColor.ActiveGrey)
        HorizontalDivider(thickness = 1.dp, color = MainThColor.TextGrey, modifier = Modifier.padding(top = 16.dp))
    }
}