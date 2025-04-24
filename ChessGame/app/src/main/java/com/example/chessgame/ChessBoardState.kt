//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//fun initialize(){
//    var blocks = copyBoardStatus(this.blocks);
//    updateBoard(blocks);
//}
//
//public class ChessBoardState(board: ChessBoard ) : ChessBoard() {
//
//
//    @Composable
//    fun copyBoardStatus(currentBoard : Array<Array<Block>>) : Array<Array<Block>>{
//        var blocks : Array<Array<Block>> = emptyArray()
//        for (i in IntRange(0,7)) {
//            for (j in IntRange(0,7)) {
//                var tempBlock : Block = Block(i, j);
//                tempBlock = currentBoard[i][j];
//                blocks[i][j] = Block(i, j);
//                if(tempBlock.isOccupied){
//                    blocks[i][j].addPiece(tempBlock.occupiedBy.occupyPlace(blocks[i][j],blocks));
//                    blocks[i][j].occupiedBy?.hasMoved = tempBlock.occupiedBy.hasMoved;
//                }
//            }
//        }
//
//        return blocks;
//
//    }
//
//}
