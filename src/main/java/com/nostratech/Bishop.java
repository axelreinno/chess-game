package com.nostratech;

public class Bishop extends Piece {
    public Bishop(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Bishop.png");
        } else {
            image = getImage("/b_Bishop.png");
        }
    }
}
