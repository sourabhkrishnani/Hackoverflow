@file:JvmName("ChessBoardKt")

package com.example.chessgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun DrawChessBoard(blocks : Array<Array<Square>>){

//    var squareToPieceMap : MutableMap<Square, Piece?> by rememberSaveable { mutableMapOf() }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    )
    {
        repeat(8){

            val rowNumber = it
            Row(horizontalArrangement = Arrangement.Absolute.Center, verticalAlignment = Alignment.CenterVertically ){

                repeat(8){

                    val colNumber = it
                    val square = blocks[rowNumber][colNumber]
                    square.DrawSquare()
                }
            }
        }
    }
}


