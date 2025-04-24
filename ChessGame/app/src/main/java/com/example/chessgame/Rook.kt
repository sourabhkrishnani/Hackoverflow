//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//class Rook(isWhite : Boolean,b : Block, blocks : Array<Array<Block>>) : ChessPiece(isWhite,blocks){
//
//
//
//    override var oldPosition = b;
//    override var oldXpoint = b.xPoint;
//    override var oldYpoint = b.yPoint;
//
//    override val name: String =
//        if (isWhite) {"W Rook";
//        }else{
//            "B Rook";
//        }
//
//    override val VALUE = 5;
//    override var possibleMoves: Array<Block> = emptyArray()
//
//
//    @Composable
//    override fun updatePossibleMovement(board : ChessBoard){
//
//        clearPossibleMovement(board);
//
//        for (i in arrayOf(-1,0,1)) {
//            for (j in arrayOf(-1,0,1)) {
//                if ((i == 0 || j == 0) && !(i == 0 && j == 0) ) {
//                    try {
//                        var pointer = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//                    } catch (e : Exception ) {
//                        continue;
//                    }
//                    var pointer = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//
//                    while(0 <= pointer.xPoint && pointer.xPoint <= 7 && 0 <= pointer.yPoint && pointer.yPoint <= 7 && (pointer.isOccupied==false || (pointer.occupiedBy?.isWhite != this.isWhite)) ){
//
//
//
//                        if (pointer.isOccupied) {
//                            if(pointer.occupiedBy?.isWhite != this.isWhite){
//                                this.possibleMoves.plusElement(pointer);
//                                break;
//                            }
//                        }
//
//                        possibleMoves.plusElement(pointer);
//
//                        try {
//                            pointer = board.blocks[pointer.xPoint+i][pointer.yPoint+j];
//
//                        } catch (e : Exception) {
//                            break;
//                        }
//
//                    }
//                }
//            }
//
//        }
//        this.noOfPossMoves = this.possibleMoves.size;
//    }
//
//    @Composable
//    override fun clearPossibleMovement(board : ChessBoard){
//        possibleMoves = emptyArray();
//        noOfPossMoves = possibleMoves.size;
//
//    }
//
//    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>):Rook{
//        var newRook =  Rook(this.isWhite, location, blocks);
//        return newRook;
//    }
//}
