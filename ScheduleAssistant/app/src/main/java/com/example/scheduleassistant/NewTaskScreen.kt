package com.example.scheduleassistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.ui.theme.MainThColor

@Preview
@Composable
fun NewTaskScreen(){

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .background(color = MainThColor.BlackBackGround)
                .padding(horizontal = 20.dp)
                .padding(top = 25.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                Text("Add new habit", color = MainThColor.TextGrey)
            }

            Spacer(Modifier.padding(20.dp))

            TextField(value = "Habit name", onValueChange = {}, colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MainThColor.BlackBackGround , unfocusedTextColor = MainThColor.TextWhite), modifier = Modifier.fillMaxWidth().background(color = MainThColor.BlackBackGround)
            )

            Spacer(Modifier.padding(20.dp))

            TextField(value = "Habit details", onValueChange = {}, colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MainThColor.BlackBackGround , unfocusedTextColor = MainThColor.TextWhite), modifier = Modifier.fillMaxWidth().background(color = MainThColor.BlackBackGround)
            )

            Spacer(Modifier.padding(20.dp))

            Text("Set Periodicity" , fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MainThColor.TextWhite)

            Spacer(Modifier.padding(15.dp))
            DisplayWeek()

            Spacer(Modifier.padding(20.dp))

            Text("Set Remainder Time" , fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MainThColor.TextWhite)

            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly , modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)){
                TimeSlots("8")
                TimeSlots("9")
            }

        }
    }
}