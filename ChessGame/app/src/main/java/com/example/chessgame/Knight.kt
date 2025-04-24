//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//class Knight(isWhite: Boolean, b: Block, blocks: Array<Array<Block>>) : ChessPiece(isWhite, blocks){
//
//    override var oldPosition = b;
//    override var oldXpoint = b.xPoint;
//    override var oldYpoint = b.yPoint;
//    override val name: String =if (isWhite) {"W Knight"}else{"B Knight"}
//
//
//    override val VALUE = 3;
//    override var possibleMoves: Array<Block> = emptyArray()
//
//
//    @Composable
//    override fun updatePossibleMovement(board : ChessBoard){
//
//        clearPossibleMovement(board);
//
//        for(i in arrayOf(-1,1)) {
//            for (j in arrayOf(-1,1)) {
//
//                for (k in arrayOf(0,1)) {
//                    var x=0
//                    var y =0;
//                    if(k==0){
//                        x=2*i;
//                        y=j;
//                    }
//                    else{
//                        x=i;
//                        y=2*j;
//                    }
//                    try {
//                        var pointer : Block = board.blocks[this.oldXpoint+x][this.oldYpoint+y];
//
//                    } catch (e : Exception) {
//                        continue;
//                    }
//                    var pointer : Block= board.blocks[this.oldXpoint+x][this.oldYpoint+y];
//
//                    if (pointer.isOccupied) {
//                        if(pointer.occupiedBy?.isWhite != this.isWhite){
//                            this.possibleMoves.plusElement(pointer);
//                            break;
//                        }
//                        else{
//                            continue;
//                        }
//                    }
//                    this.possibleMoves.plusElement(pointer);
//                }
//            }
//        }
//        this.noOfPossMoves = this.possibleMoves.size;
//    }
//
//    @Composable
//    override fun clearPossibleMovement(board : ChessBoard){
//        possibleMoves = emptyArray();
//        noOfPossMoves = possibleMoves.size;
//    }
//
//    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>):Knight{
//        var newKnight = Knight(this.isWhite, location,blocks);
//        return newKnight;
//    }
//}
