//package com.example.chessgame
//
//
//import androidx.compose.runtime.Composable
//import java.util.Scanner;
//
//public class Pawn(isWhite : Boolean, b : Block, blocks: Array<Array<Block>>): ChessPiece(isWhite,blocks){
//
//    override var oldPosition = b;
//    override var oldXpoint = b.xPoint;
//    override var oldYpoint = b.yPoint;
//
//    var initial = 1
//    var max =7
//    var change =1
//
//
//    override val name: String = (if (isWhite){"W Pawn" } else{
//        initial = 6
//        max = 0
//        change = -1;
//        "B Pawn"
//    }).toString()
//
//
//
//
//    override var VALUE = 1;
//    override var possibleMoves: Array<Block> = emptyArray()
//
//    @Composable
//    fun promotion(board : ChessBoard, p : ChessPiece){
//        var newBlock : Block = p.oldPosition;
//        var isWhite : Boolean = p.isWhite;
//        newBlock.removePiece();
//
//        Scanner sc = Scanner(System.in);
//
//        while (true) {
//            System.out.println("\nEnter piece for promotion (Queen, Rook, Knight or Bishop) :");
//            var newPiece : String = "sc.nextLine().strip()";
//
//
//            when (newPiece.lowercase()) {
//
//                "queen" -> newBlock.addPiece(Queen(isWhite, newBlock, board.blocks));
//
//                "rook" -> newBlock.addPiece(Rook(isWhite, newBlock, board.blocks));
//
//                "knight" -> newBlock.addPiece(Knight(isWhite, newBlock, board.blocks));
//
//                "bishop" -> newBlock.addPiece(Bishop(isWhite, newBlock, board.blocks));
//
//
//                else -> continue;
//            }
//            break;
//        }
//    }
//
//    @Composable
//    override fun updatePossibleMovement(board : ChessBoard){
//
//        clearPossibleMovement(board);
//
//        var i = 0;
//
//        while (i < 4 && this.oldPosition.xPoint < 7 && this.oldPosition.xPoint > 0) {
//
//            when (i) {
//
//                0 -> {
//                    var pointer: Block = board.blocks[this.oldXpoint + change][this.oldYpoint];
//                    if (pointer.isOccupied) {
//                        break;
//                    } else {
//                        this.possibleMoves.plusElement(pointer);
//                    }
//                }
//
//                1 -> {
//                    if (this.oldXpoint == initial) {
//                        var pointer = board.blocks[this.oldXpoint + change + change][this.oldYpoint];
//                        if (pointer.isOccupied || board.blocks[this.oldXpoint + change][this.oldYpoint].isOccupied) {
//                            break;
//                        } else {
//                            this.possibleMoves.plusElement(pointer);
//                        }
//                        break;
//                    }
//                }
//                2 -> {
//                    try {
//                        var pointer = board.blocks[this.oldXpoint + change][this.oldYpoint + change];
//                    } catch (e : Exception) {
//                        i++;
//                        continue;
//                    }
//                    var pointer = board.blocks[this.oldXpoint + change][this.oldYpoint + change];
//
//                    if (pointer.isOccupied && (pointer.occupiedBy?.isWhite != this.isWhite)) {
//                        this.possibleMoves.plusElement(pointer);
//                        break;
//                    } else {
//                        break;
//                    }
//                }
//
//                3 -> {
//                    try {
//                        var pointer = board.blocks[this.oldXpoint + change][this.oldYpoint - change];
//                    } catch (e : Exception) {
//                        i++;
//                        continue;
//                    }
//                    var pointer = board.blocks[this.oldXpoint + change][this.oldYpoint - change];
//
//                    if (pointer.isOccupied && (pointer.occupiedBy?.isWhite != this.isWhite)) {
//                        this.possibleMoves.plusElement(pointer);
//                        break;
//                    } else {
//                        break;
//                    }
//                }
//            }
//            i++;
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
//    override fun occupyPlace(location : Block, blocks : Array<Array<Block>>) : Pawn{
//        var newPawn : Pawn = Pawn(this.isWhite, location,blocks);
//        return newPawn;
//    }
//}
