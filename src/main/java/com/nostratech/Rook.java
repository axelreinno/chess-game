package com.nostratech;

public class Rook extends Piece {
    public Rook(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Rook.png");
        } else {
            image = getImage("/b_Rook.png");
        }
    }
}
