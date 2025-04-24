package com.example.scheduleassistant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.ui.theme.MainThColor
import com.example.scheduleassistant.ui.theme.MainThColor.TextWhite

@Preview
@Composable
fun StatisticsScreen(){
    Box(modifier = Modifier.fillMaxSize()){

        Column (modifier = Modifier
            .background(color = MainThColor.BlackBackGround)
            .padding(horizontal = 20.dp)
            .padding(top = 25.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ){
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Statistics", color = MainThColor.TextWhite, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Surface(onClick = {} , shape = CircleShape, color = MainThColor.ActiveGrey, modifier = Modifier.size(35.dp)) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "Icon", tint = MainThColor.TextWhite , modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp))
                }
            }
            DropDownFilter()
            HorizontalDivider(thickness = 1.dp, color = MainThColor.ActiveGrey, modifier = Modifier.padding(top = 16.dp))

            Spacer(Modifier.size(30.dp))
            Surface(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), shape = RoundedCornerShape(20.dp)) {
                Column (verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.background(color = MainThColor.InactiveGrey).padding(16.dp)){
                    Text("Weekly Report", color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("67%", fontSize = 40.sp , color = TextWhite, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))
                    LinearProgressIndicator(
                        progress = { .67f },
                        color = MainThColor.TextGrey,
                        trackColor = MainThColor.ActiveGrey,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.size(30.dp))

            Surface(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), shape = RoundedCornerShape(20.dp)) {
                Column (verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.background(color = MainThColor.InactiveGrey).padding(16.dp)){
                    Text("Weekly Report", color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("67%", fontSize = 40.sp , color = TextWhite, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))
                    LinearProgressIndicator(
                        progress = { .67f },
                        color = MainThColor.TextGrey,
                        trackColor = MainThColor.ActiveGrey,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

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

@Composable
fun DropDownFilter() {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val usernames = listOf("Last 7 days", "Last 15 days", "Last Month", "All time")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .clickable {
                        isDropDownExpanded.value = true
                    }
            ) {
                Text(text = usernames[itemPosition.value], fontSize = 24.sp, color = MainThColor.TextWhite)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown Icon",
                    tint = MainThColor.TextWhite
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                },
                modifier = Modifier
                    .background(color = MainThColor.InactiveGrey)
                    .fillMaxWidth(.5f)
                    .wrapContentHeight()
            ) {
                usernames.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username , color = MainThColor.TextGrey)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                        })
                }
            }
        }

    }
}
