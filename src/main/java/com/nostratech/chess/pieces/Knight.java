package com.nostratech.chess.pieces;

public class Knight extends Piece {
    public Knight(PieceColor color, int col, int row) {
        super(color, col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Knight.png");
        } else {
            image = getImage("/b_Knight.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow)) {
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2) { 
                if(isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        return false;
    }
}