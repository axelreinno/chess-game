package com.nostratech;

public class Pawn extends Piece {
    public Pawn(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Pawn.png");
        } else {
            image = getImage("/b_Pawn.png");
        }
    }
}
