package com.nostratech;

public class King extends Piece {
    public King(int col, int row, PieceColor color) {
        super(col, row, color);

        if (color == PieceColor.WHITE) {
            image = getImage("/w_King.png");
        } else {
            image = getImage("/b_King.png");
        }
    }
}
