//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//class Bishop(isWhite : Boolean , b : Block , blocks : Array<Array<Block>>) : ChessPiece(isWhite,blocks){
//
//    override var oldPosition = b;
//    override var oldXpoint = b.xPoint;
//    override var oldYpoint = b.yPoint;
//    override val name: String =if (isWhite) {"W Bishop"}else{"B Bishop"}
//    override val VALUE: Int = 3
//
//    override var possibleMoves: Array<Block> = emptyArray()
//
//    @Composable
//    override fun updatePossibleMovement(board : ChessBoard){
//
//        clearPossibleMovement(board);
//
//        for (i in arrayOf(-1,1)) {
//            for (j in arrayOf(-1,1)) {
//
//                try {
//                    var pointer  : Block = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//                } catch (e : Exception) {
//                    continue;
//                }
//                var pointer : Block = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//
//                while(0 <= pointer.xPoint && pointer.xPoint <= 7 && 0 <= pointer.yPoint && pointer.yPoint <= 7 && (pointer.isOccupied==false || (pointer.occupiedBy?.isWhite != this.isWhite)) ){
//
//                    if (pointer.isOccupied) {
//                        if(pointer.occupiedBy?.isWhite != this.isWhite){
//
//                            this.possibleMoves.plusElement(pointer);
//                            break;
//                        }
//                    }
//                    this.possibleMoves.plusElement(pointer);
//
//                    try {
//                        pointer = board.blocks[pointer.xPoint+i][pointer.yPoint+j];
//
//                    } catch (e : Exception) {
//                        break;
//                    }
//
//
//                }
//            }
//
//        }
//            this.noOfPossMoves = this.possibleMoves.size;
//    }
//
//    @Composable
//    override fun clearPossibleMovement(board : ChessBoard){
//        possibleMoves = emptyArray();
//        noOfPossMoves = possibleMoves.size;
//    }
//
//    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>): Bishop{
//        var newBishop : Bishop = Bishop(this.isWhite, location,blocks);
//        return newBishop;
//    }
//}
