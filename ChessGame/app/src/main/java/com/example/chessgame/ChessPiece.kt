//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//import java.util.ArrayList;
//
//public abstract class ChessPiece (val isWhite: Boolean, blocks : Array<Array<Block>>){
//
//    var hasMoved = false
//
//
//
//    abstract val VALUE : Int
//    abstract val name : String
//
//    abstract var oldPosition : Block
//    abstract var oldXpoint : Int
//    abstract var oldYpoint : Int
//
//    abstract var possibleMoves : Array<Block>
//    var noOfPossMoves : Int = possibleMoves.size
//
//
//    @Composable
//    open fun updatePossibleMovement(board : ChessBoard){}
//
//    @Composable
//    open fun clearPossibleMovement(board : ChessBoard){}
//
//    @Composable
//    fun move(newBlock : Block, board : ChessBoard){
//
//        for (block : Block in possibleMoves) {
//            if (block == null) {
//                System.out.println("\nInvalid move");
//                throw NullPointerException();
//            }
//            else if(block.equals(newBlock)){
//
//                board.saveState();
//                this.oldPosition.removePiece();
//                newBlock.addPiece(this);
//                board.updateBoard(board.blocks);
//                return;
//            }
//        }
//        System.out.println("\nInvalid move");
//        throw NullPointerException();
//    }
//
//    open fun occupyPlace(location : Block, blocks : Array<Array<Block>>) : ChessPiece{
//        var newChessPiece : ChessPiece = ChessPiece(isWhite, blocks);
//        return newChessPiece;
//    }
//
//
//
//}
