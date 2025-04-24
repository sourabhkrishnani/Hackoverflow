//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//
//class King(isWhite : Boolean, b: Block , blocks : Array<Array<Block>>) : ChessPiece(isWhite,blocks){
//
//
//    override var oldPosition = b;
//    override var oldXpoint = b.xPoint;
//    override var oldYpoint = b.yPoint;
//    override val name: String = if (isWhite){"W King"}else{"B King"}
//    override var possibleMoves: Array<Block> = emptyArray()
//    override var VALUE = 999999;
//
//    @Composable
//    override fun updatePossibleMovement(board: ChessBoard){
//
//        clearPossibleMovement(board);
//
//        for(i in IntRange(-1,1)) {
//            for (j in IntRange(-1,1)) {
//
//                if(i==0 && j==0){
//                    continue;
//                }
//                try {
//                    var pointer : Block = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//
//                } catch (Exception e) {
//                    continue;
//                }
//                var pointer : Block = board.blocks[this.oldXpoint+i][this.oldYpoint+j];
//
//                if (pointer.isOccupied) {
//                    if(pointer.occupiedBy.isWhite != this.isWhite){
//                        this.possibleMoves.plusElement(pointer);
//                        continue;
//                    }
//                    else{
//                        continue;
//                    }
//                }
//
//                this.possibleMoves.plusElement(pointer);
//            }
//        }
//        this.noOfPossMoves = this.possibleMoves.size;
//    }
//
//    @Composable
//    override fun clearPossibleMovement(board: ChessBoard){
//        this.possibleMoves.clear();
//        this.noOfPossMoves = this.possibleMoves.size;
//    }
//
//
//    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>) : King{
//        var newKing : King = King(this.isWhite, location,blocks)
//        return newKing
//    }
//}
