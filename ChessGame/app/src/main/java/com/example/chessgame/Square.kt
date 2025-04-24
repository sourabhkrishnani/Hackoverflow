package com.example.chessgame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class TempBox (val rowName: Int, val colName: Int )

data class Square (val rowName: Int, val colName: Int , var isOccupiedBy : Piece? ){

    val squareName : String = "$rowName , $colName"

    val isWhite = (rowName + colName)%2 == 0
    var squareColor: Color = if (isWhite) Color.LightGray else Color.DarkGray
    var isOccupied : Boolean = isOccupiedBy != null



    @Composable
    fun DrawSquare(){

        Box (
            modifier = Modifier
                .size(40.dp)
                .background(this.squareColor)
                .clickable(enabled = isOccupied, onClick = {}),

        ){
            Text("$rowName,$colName", textAlign = TextAlign.Left, color = Color.Gray, fontSize = 5.sp, modifier = Modifier.size(40.dp))
            if (isOccupied){
                Text(text = isOccupiedBy?.name ?: "" ,
                    color = if (isOccupiedBy?.pieceIsWhite == true) Color.White else Color.Black,
                    textAlign = TextAlign.Center , modifier = Modifier.fillMaxWidth(.8f).fillMaxHeight())
            }
        }
    }



}




@Preview
@Composable
fun PreviewDrawSquare(){

    var piece = Piece("queen" , false)
    var temp = Square(1,1, isOccupiedBy = piece )

    Row ( modifier = Modifier.fillMaxSize()){
        temp.DrawSquare()
    }

}