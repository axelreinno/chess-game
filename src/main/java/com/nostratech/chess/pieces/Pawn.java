package com.nostratech.chess.pieces;

public class Pawn extends Piece {
    public Pawn(PieceColor color, int col, int row) {
        super(color,col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Pawn.png");
        } else {
            image = getImage("/b_Pawn.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            int move = color == PieceColor.WHITE ? -1 : 1;
            hittingPiece = getHittingPiece(targetCol, targetRow);

            if(targetCol == preCol && targetRow == preRow + move && hittingPiece == null) {
                return true;
            }

            if(targetCol == preCol && targetRow == preRow + move * 2 && hittingPiece == null && !moved && !isPieceOnStraightLine(targetCol, targetRow)) {
                return true;
            }

            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + move && hittingPiece != null && hittingPiece.color != color) { 
                return true;
            }
        }

        return false;
    }
}