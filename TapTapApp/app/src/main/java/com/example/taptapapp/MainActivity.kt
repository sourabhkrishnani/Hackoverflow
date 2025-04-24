package com.example.taptapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taptapapp.ui.theme.TapTapAppTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TapTapAppTheme {
                TapTapScreen()
            }
        }
    }


    @Composable
    fun TapTapScreen(){

        Column {

            Column (
                Modifier
                    .fillMaxWidth()
                    .weight(1f)){

                Row {
                    TopBar()
                }

                Row (modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)) {
                    PlayScreen()

                }
            }

            Column (Modifier.fillMaxWidth()){
                Row (verticalAlignment = Alignment.Bottom ,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.wrapContentHeight())
                {
                    BottomBar()
                }
            }


        }
    }

    @Composable
    fun PlayScreen(){


        var score by rememberSaveable{
            mutableStateOf(0)
        }

        var target = Random.nextInt(4)

        var active by rememberSaveable { mutableStateOf(false) }



        Column{

            Spacer(Modifier.height(20.dp))
            Score(score)

            Spacer(Modifier.height(20.dp))
            TapTarget(target)

            Spacer(Modifier.height(20.dp))
            PlayButton( active , score, target,  {score++}, {score-=2})

            Spacer(Modifier.height(20.dp))
            StartButton(active, { active = !active })

        }

    }

    @Composable
    fun Score(score : Int){
        Row(horizontalArrangement = Arrangement.Absolute.Center, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        {
            Text(text = "SCORE : $score",
                fontSize = 30.sp,
                modifier = Modifier.background(color = MaterialTheme.colorScheme.errorContainer)
            )
        }

    }

    @Composable
    fun TapTarget(target : Int){

        Row(horizontalArrangement = Arrangement.Absolute.Center, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp))
        {
            Text("Target = $target",
                fontSize = 30.sp,
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
            )
        }
    }

    @Composable
    fun PlayButton (active : Boolean , score: Int,target: Int, inc: () -> Unit , dec: () -> Unit) {


        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f))
        {


            Row (verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){

                var btn = Modifier.size(120.dp)
                if (active){

                    Button(onClick = validatingFunction (score , myChoice =1, target, inc, dec) , elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp, pressedElevation = 5.dp), modifier = btn)
                    {Text(text = "Red", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red) }

                    Button(onClick = validatingFunction (score , myChoice =2, target, inc, dec) , elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp, pressedElevation = 5.dp), modifier = btn)
                    {Text(text = "Green", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green) }

                    Button(onClick = validatingFunction (score , myChoice =3, target, inc, dec) , elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp, pressedElevation = 5.dp), modifier = btn)
                    {Text(text = "Blue", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Blue) }

                }
                else{
                    Text("Start the Game", fontSize = 50.sp)
                }



            }
        }

    }

    @Composable
    fun StartButton(active : Boolean , funFalse : () -> Unit  ){

        var tempText by rememberSaveable { mutableStateOf("Start...") }
        Button(onClick = funFalse , colors = ButtonDefaults.buttonColors(Color.DarkGray), modifier = Modifier.fillMaxWidth().padding(20.dp), elevation = ButtonDefaults.filledTonalButtonElevation(defaultElevation = 20.dp ,  0.dp)) {Text(tempText); if (active) { tempText = "End" } else { tempText = "Start"}  }

    }

    @Composable
    fun innerFunction(myChoice: Int , target: Int ) : Boolean{
        if (myChoice==target){
            return true
        }
        else{
            return false
        }
    }

    @Composable
    fun validatingFunction(score: Int, myChoice: Int , target: Int , inc:()-> Unit ,dec:()-> Unit): () -> Unit {
        if (innerFunction(myChoice, target )){
            return inc
        }
        else{
            return dec
        }
    }

    @Composable
    fun TopBar() {

        Surface (shape = RectangleShape, shadowElevation = 10.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()){
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

    }

    @Composable
    fun BottomBar(){
        Surface (shape = RectangleShape, shadowElevation = 10.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()){
            Text(
                text = stringResource(R.string.Description1),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TapTapAppTheme {
            TapTapScreen()
        }
    }

}



