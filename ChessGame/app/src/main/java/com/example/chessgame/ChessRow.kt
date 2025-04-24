//@file:JvmName("ChessBoardKt")
//
//package com.example.chessgame
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//
//public class ChessBoard(firstSquareIsWhite : Boolean, colName : Int){
//
//    val rowName : String = "row$colName"
//
//    val chessRow : Array<Square> = arrayOf(
//
//        Square(firstSquareIsWhite,1, colName,null),
//        Square(!firstSquareIsWhite,2, colName,null),
//        Square(firstSquareIsWhite,3, colName,null),
//        Square(!firstSquareIsWhite,4, colName,null),
//        Square(firstSquareIsWhite,5, colName,null),
//        Square(!firstSquareIsWhite,6, colName,null),
//        Square(firstSquareIsWhite,7, colName,null),
//        Square(!firstSquareIsWhite,8, colName,null)
//    )
//
//    @Composable
//    fun DrawRow(){
//
//
//        Row {
//            chessRow[0].DrawSquare()
//            chessRow[1].DrawSquare()
//            chessRow[2].DrawSquare()
//            chessRow[3].DrawSquare()
//            chessRow[4].DrawSquare()
//            chessRow[5].DrawSquare()
//            chessRow[6].DrawSquare()
//            chessRow[7].DrawSquare()
//        }
//    }
//
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewDrawRow(){
//    val temp = ChessRow(false,1)
//    temp.DrawRow()
//}