package com.nostratech.chess.pieces;

public class Queen extends Piece {
    public Queen(PieceColor color, int col, int row) {
        super(color, col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Queen.png");
        } else {
            image = getImage("/b_Queen.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if(targetCol == preCol || targetRow == preRow) {
                if(isValidSquare(targetCol, targetRow) && !isPieceOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }

            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if(isValidSquare(targetCol, targetRow) && !isPieceOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        return false;
    }
}