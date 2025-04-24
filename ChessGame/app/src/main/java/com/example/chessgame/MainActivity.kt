package com.example.chessgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chessgame.ui.theme.ChessGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessGameTheme {
                PlayScreen()
            }
        }
    }
}

@Composable
fun PlayScreen(){

    Scaffold{ innerPadding ->
        Column (modifier = Modifier.padding(innerPadding).wrapContentSize()){
            var tempBoard = ChessBoard()
            var temp1 by rememberSaveable { mutableStateOf(tempBoard.blocks) }
            tempBoard.CustomSetup()
            tempBoard.UpdateBoard(temp1)
//            tempBoard.ShowChessBoard(temp1)

            tempBoard.AddPiece(temp1[1][1], Piece(name = "x Queen",true))
            tempBoard.AddPiece(temp1[7][7], Piece(name = "x Queen",false))
            tempBoard.UpdateBoard(temp1)
            tempBoard.ShowChessBoard(temp1)


        }
    }
}

//@Preview
//@Composable
//fun PlayScreen(){
//    val square  = Square(1,1,null)
//    square.DrawSquare()
//    val piece = Piece(name = "Queen" , true)
//    square.isOccupiedBy = piece
//    square.isOccupied = true
//    square.DrawSquare()
//}






@Preview
@Composable
fun PlayScreenPreview() {
    PlayScreen()
}