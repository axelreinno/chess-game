package com.nostratech.chess.pieces;

public class King extends Piece {
    public King(PieceColor color, int col, int row) {
        super(color, col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_King.png");
        } else {
            image = getImage("/b_King.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow)) {
            if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 || Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) { 
                if(isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        return false;
    }
}