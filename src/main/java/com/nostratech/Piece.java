package com.nostratech;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Piece {
    public int col;
    public int row;
    public int xPixel;
    public int yPixel;
    protected PieceColor color;
    protected BufferedImage image;

    public Piece(int col, int row, PieceColor color) {
        this.col = col;
        this.row = row;
        this.color = color;
        xPixel = getXPixel();
        yPixel = getYPixel();
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getXPixel() {
        return col * Board.SQUARE_SIZE;
    }

    public int getYPixel() {
        return row * Board.SQUARE_SIZE;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, xPixel, yPixel, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
