//package com.example.chessgame
//
//import androidx.compose.runtime.Composable
//
//public abstract class Block(xPoint : Int, yPoint : Int){
//
//    val xPoint = xPoint
//    val yPoint = yPoint
//    var isOccupied = false;
//
//
//
//    abstract var occupiedBy : ChessPiece?
//
//    @Composable
//    fun addPiece(p: ChessPiece){
//        if (this.isOccupied && (p.isWhite == this.occupiedBy?.isWhite)) {
//            System.out.println("Invalid move");
//            throw NullPointerException();
//        }
//        else if(isOccupied){
//            removePiece();
//            addPiece(p);
//        }
//        else{
//            this.occupiedBy = p;
//            this.isOccupied = true;
//            p.oldPosition = this;
//            p.oldXpoint = this.xPoint;
//            p.oldYpoint = this.yPoint;
//        }
//    }
//
//    fun removePiece(){
//        occupiedBy = null;
//        isOccupied = false;
//    }
//
//
//}