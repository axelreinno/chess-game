package com.nostratech;

public class Knight extends Piece {
    public Knight(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_Knight.png");
        } else {
            image = getImage("/b_Knight.png");
        }
    }
}
