package com.nostratech.chess.pieces;

public class Rook extends Piece {
    public Rook(PieceColor color, int col, int row) {
        super(color, col, row);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Rook.png");
        } else {
            image = getImage("/b_Rook.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if(targetCol == preCol || targetRow == preRow) {
                if(isValidSquare(targetCol, targetRow) && !isPieceOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        return false;
    }
}