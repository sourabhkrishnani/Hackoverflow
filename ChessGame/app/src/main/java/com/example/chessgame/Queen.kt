//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//class Queen(pieceIsWhite : Boolean , square: Square) : Piece(name = "Queen",pieceIsWhite){
//
//    var VALUE = 9;
//    var possibleMoves: Array<Square> = emptyArray()
//
//    @Composable
//    fun UpdatePossibleMovement(blocks : Array<Array<Square>>){
//
////        clearPossibleMovement(board);
//
//        for(i in arrayOf(-1,0,1)) {
//            for (j in arrayOf(-1,0,1)) {
//
//                if(i==0 && j==0){
//                    continue;
//                }
//                try {
//                    var pointer = blocks[this.oldXpoint+i ][this.oldYpoint+j];
//
//                } catch (Exception e) {
//                    continue;
//                }
//
//                var pointer = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//
//                while(0 <= pointer.xPoint && pointer.xPoint <= 7 && 0 <= pointer.yPoint && pointer.yPoint <= 7 && (pointer.isOccupied==false || (pointer.occupiedBy.isWhite != this.isWhite)) ){
//
//                    if (pointer.isOccupied) {
//                        if(pointer.occupiedBy.isWhite != this.isWhite){
//                            this.possibleMoves.plusElement(pointer);
//                            break;
//                        }
//                    }
//
//                    this.possibleMoves.plusElement(pointer);
//
//                    try {
//                        pointer = board.blocks[pointer.xPoint+i][pointer.yPoint+j];
//
//                    } catch (Exception e) {
//                        break;
//                    }
//
//
//                }
//            }
//        }
//        this.noOfPossMoves = this.possibleMoves.size;
//    }
////
////    @Composable
////    override fun clearPossibleMovement(board : ChessBoard){
////        this.possibleMoves.clear();
////        this.noOfPossMoves = this.possibleMoves.size;
////    }
////
////    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>) : Queen{
////        var newQueen = Queen(this.isWhite, location,blocks);
////        return newQueen;
////    }
//
//
//
//}
