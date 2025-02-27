package com.nostratech.chess.pieces;

public class Bishop extends Piece {
    public Bishop(PieceColor color, int col, int row) {
        super(color, col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Bishop.png");
        } else {
            image = getImage("/b_Bishop.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if(isValidSquare(targetCol, targetRow) && !isPieceOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        return false;
    }
}
