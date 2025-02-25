package com.nostratech;

public class Queen extends Piece {
    public Queen(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Queen.png");
        } else {
            image = getImage("/b_Queen.png");
        }
    }
}
