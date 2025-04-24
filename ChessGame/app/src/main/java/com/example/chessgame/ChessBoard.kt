package com.example.chessgame


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.Stack;

class ChessBoard(){
//    var history = Stack<ChessBoardState>()

    var blocks : Array<Array<Square>> = Array(8){rowNumber -> Array<Square>(
        8,
        init = {colNumber -> Square(rowNumber+1,colNumber+1,null)}
    ) }

    var squareToPieceMap = mutableMapOf<Square, Piece?>()
    var pieceToSquareMap = mutableMapOf<Piece,Square>()

    @Composable
    fun SetUpBoard(){


//        AddPiece(blocks[7][4] , Piece(name = "W Queen",true))
//        AddPiece(blocks[8][5] , Piece(name = "W King",true))
//        AddPiece(blocks[8][3] , Piece(name = "W Bishop",true))
//        AddPiece(blocks[8][6] , Piece(name = "W Bishop",true))
//        AddPiece(blocks[8][1] , Piece(name = "W Rook",true))
//        AddPiece(blocks[8][8] , Piece(name = "W Rook",true))
//        AddPiece(blocks[8][2] , Piece(name = "W Knight",true))
//        AddPiece(blocks[8][7] , Piece(name = "W Knight",true))
//        AddPiece(blocks[7][1] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][2] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][3] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][4] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][5] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][6] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][7] , Piece(name = "W Pawn",true))
//        AddPiece(blocks[7][8] , Piece(name = "W Pawn",true))
//
//        AddPiece(blocks[1][4] , Piece(name = "B Queen",false))
//        AddPiece(blocks[1][5] , Piece(name = "B King",false))
//        AddPiece(blocks[1][3] , Piece(name = "B Bishop",false))
//        AddPiece(blocks[1][6] , Piece(name = "B Bishop",false))
//        AddPiece(blocks[1][1] , Piece(name = "B Rook",false))
//        AddPiece(blocks[1][8] , Piece(name = "B Rook",false))
//        AddPiece(blocks[1][2] , Piece(name = "B Knight",false))
//        AddPiece(blocks[1][7] , Piece(name = "B Knight",false))
//        AddPiece(blocks[2][1] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][2] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][3] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][4] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][5] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][6] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][7] , Piece(name = "B Pawn",false))
//        AddPiece(blocks[2][8] , Piece(name = "B Pawn",false))

//
//        this.blocks[0][3].addPiece(King(true, this.blocks[0][3],blocks));
//        this.blocks[7][3].addPiece(King(false, this.blocks[7][3],blocks));
//
//        this.blocks[0][4].addPiece(Queen(true, this.blocks[0][4],blocks));
//        this.blocks[7][4].addPiece(Queen(false, this.blocks[7][4],blocks));
//
//        this.blocks[0][5].addPiece(Bishop(true, this.blocks[0][5], blocks));
//        this.blocks[0][2].addPiece(Bishop(true, this.blocks[0][2], blocks));
//
//        this.blocks[7][5].addPiece(Bishop(false, this.blocks[7][5],blocks));
//        this.blocks[7][2].addPiece(Bishop(false, this.blocks[7][2],blocks));
//
//        this.blocks[0][6].addPiece(Knight(true, this.blocks[0][6], blocks));
//        this.blocks[0][1].addPiece(Knight(true, this.blocks[0][1], blocks));
//
//        this.blocks[7][6].addPiece(Knight(false, this.blocks[7][6],blocks));
//        this.blocks[7][1].addPiece(Knight(false, this.blocks[7][1],blocks));
//
//
//        this.blocks[0][7].addPiece(Rook(true, this.blocks[0][7],blocks));
//        this.blocks[0][0].addPiece(Rook(true, this.blocks[0][0],blocks));
//
//        this.blocks[7][0].addPiece(Rook(false, this.blocks[7][0],blocks));
//        this.blocks[7][7].addPiece(Rook(false, this.blocks[7][7],blocks));
//
//        // white pawn
//        for (i in IntRange(0,7)) {
//            this.blocks[1][i].addPiece(Pawn(true, this.blocks[1][i], blocks));
//        }
//
//        // black pawn
//        for (i in IntRange(0,7)) {
//            this.blocks[6][i].addPiece(Pawn(false, this.blocks[6][i], blocks));
//        }
//
//        updateBoard(this.blocks);
//        showChessBoard(this.blocks);
    }

    @Composable
    fun CustomSetup(){

        this.squareToPieceMap.put(blocks[1][1],Piece(name = "W Queen",true))
        this.squareToPieceMap.put(blocks[7][7],Piece(name = "W Queen",true))
        this.squareToPieceMap.put(blocks[2][5],Piece(name = "B Queen",false))
        this.squareToPieceMap.put(blocks[6][4],Piece(name = "B Queen",false))


        // while (true) {
        //     try {

        //         Scanner sc = new Scanner(System.in);
        //         String name = "";
        //         boolean isWhite;
        //         int[] coordinate = new int[2];

        //         System.out.print("\nEnter piece name : ");
        //         name = sc.next();

        //         if(name.equalsIgnoreCase("done") ){
        //             break;
        //         }
        //         System.out.print("\nEnter piece is white (true or false): ");
        //         isWhite = sc.nextBoolean();
        //         System.out.print("Enter piece coordinate (a b) : ");
        //         coordinate[0] = sc.nextInt();
        //         coordinate[1] = sc.nextInt();



        //         switch (name.toLowerCase()) {
        //             case "king":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new King(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;
        //             case "queen":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new Queen(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;
        //             case "rook":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new Rook(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;
        //             case "knight":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new Knight(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;
        //             case "bishop":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new Bishop(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;
        //                 case "pawn":
        //                 this.blocks[coordinate[0]][coordinate[1]].addPiece(new Pawn(isWhite, this.blocks[coordinate[0]][coordinate[1]],blocks));
        //                 break;

        //             default:
        //                 break;
        //         }

        //     } catch (Exception e) {
        //         System.out.println("\nTry again");
        //     }

        // }
//        updateBoard(this.blocks);
//        showChessBoard(this.blocks);
    }

    @Composable
    fun ShowChessBoard(blocks : Array<Array<Square>>){
        DrawChessBoard(blocks)

    }
//        for (i in IntRange(0,7)) {
//            for (j in IntRange(0,7)) {
//                if (blocks[i][j].isOccupied) {
//
//                    System.out.print("\t[" + blocks[i][j].occupiedBy.name + "] ");
//                    continue;
//                }
//                System.out.print("\t["+blocks[i][j].xPoint + ","+ blocks[i][j].yPoint+"]\t");
//            }
//                System.out.println();
//                System.out.println();
//        }
//        System.out.println();
//    }
//
//    @Composable
//    fun updateBoard(blocks : Array<Array<Block>>){
//
//        for ( row : Array<Block> in blocks) {
//            for (block : Block in row) {
//                if(block.isOccupied){
//                    block.occupiedBy?.updatePossibleMovement(this);
//                }
//        }
//        }
//    }
//
//    @Composable
//    fun movePiece(board: ChessBoard, p: ChessPiece?, newPosition: Block): Boolean{
//        p.move(newPosition,board);
//        p.hasMoved = true;
//        board.updateBoard(board.blocks);
//
//        if (!board.validMove(p,board)) {
//            board.undo();
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    fun saveState(){
//        history.push(ChessBoardState(this));
//    }
//
//    @Composable
//    fun undo(){
//        if(!history.isEmpty()){
//            var previousState : ChessBoardState = history.pop();
//            this.blocks = previousState.blocks;
//            this.updateBoard(this.blocks);
//        }
//    }
//
//    fun validMove(p : ChessPiece, board : ChessBoard): Boolean{
//        return !inCheck(p.isWhite, this);
//    }
//
//    fun inCheck(isWhite: Boolean, board : ChessBoard): Boolean{
//        for ( row : Array<Block> in blocks) {
//            for (block : Block in row) {
//            if (block.isOccupied) {
//                if ((isWhite != block.occupiedBy.isWhite)) {
//
//                    for (pointer : Block in block.occupiedBy.possibleMoves){
//                        if (pointer == null) {
//                            continue;
//                        }
//                        if (pointer.isOccupied && pointer.occupiedBy instanceof King) {
//                            return true;
//                        }
//
//                    }
//                }
//            }
//        }
//        }
//        return false;
//    }
//
//    @Composable
//    fun inCheckMate(whitesTurn : Boolean, board : ChessBoard) : Boolean{
//        for (i in IntRange(0,7)) {
//            for (j in IntRange(0,7)) {
//            if (board.blocks[i][j].isOccupied) {
//                if ((whitesTurn == board.blocks[i][j].occupiedBy.isWhite)) {
//
//                    // int i=0;
//                    for ( k in IntRange(0,board.blocks[i][j].occupiedBy.noOfPossMoves)){
//                        var pointer : Block= board.blocks[i][j].occupiedBy.possibleMoves.get(k);
//                        if (pointer == null) {
//                            continue;
//                        }
//                        if (board.movePiece(board, board.blocks[i][j].occupiedBy, pointer)) {
//                            board.undo();
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
//        }
//        return true;
//    }
//
//    // Block[] validatePossibleMove(ChessBoard board){
//
//    //     for (int i = 0; i < 8; i++) {
//    //         for (int j = 0; j < 8; j++) {
//    //             if (board.blocks[i][j].isOccupied) {
//
//    //                 ChessPiece p = board.blocks[i][j].occupiedBy;
//    //                 Block[] temp = p.possibleMoves;
//    //                 int tempNoOfPossMoves = p.noOfPossMoves;
//    //                 p.clearPossibleMovement(board);
//    //                 for (int k = 0; k < tempNoOfPossMoves; k++) {
//    //                     if (board.movePiece(board, p, null)) {
//
//    //                     }
//    //                 }
//
//    //             }
//    //         }
//    //     }
//    // }
//
//    fun totalPossibleMoves(whitesTurn : Boolean, board : ChessBoard): Int{
//        var count = 0;
//        for (row in board.blocks) {
//            for (block in row) {
//                if (block.isOccupied) {
//                    if (whitesTurn == block.occupiedBy?.isWhite) {
//                        count += block.occupiedBy?.noOfPossMoves;
//                    }
//                }
//            }
//        }
//        return count;
//    }

    @Composable
    fun UpdateBoard(blocks : Array<Array<Square>> ){


        for ( row : Array<Square> in blocks) {
            for (square : Square in row) {
                if(square in squareToPieceMap.keys){
                    square.isOccupied = true
                    square.isOccupiedBy = squareToPieceMap[square]
                }
        }
        }
    }

    @Composable
    fun AddPiece(square: Square , piece: Piece){
        squareToPieceMap.put(blocks[square.rowName][square.rowName],piece)
//        square.isOccupied = true
//        square.isOccupiedBy = piece
    }

}
