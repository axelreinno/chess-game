package com.nostratech;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    public static int FPS = 60;
    Thread gameThread;
    Board board;
    List<Piece> pieces = new ArrayList<>();
    MouseListener mouseListener;
    Piece activePiece;

    public GamePanel() {
        setPreferredSize(new Dimension(Board.SQUARE_SIZE*8, Board.SQUARE_SIZE*8));
        setBackground(Color.WHITE);
        board = new Board();
        setPieces();
        mouseListener = new MouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces() {
        pieces.add(new Pawn(0, 1, PieceColor.WHITE));
        pieces.add(new Pawn(1, 1, PieceColor.WHITE));
        pieces.add(new Pawn(2, 1, PieceColor.WHITE));
        pieces.add(new Pawn(3, 1, PieceColor.WHITE));
        pieces.add(new Pawn(4, 1, PieceColor.WHITE));
        pieces.add(new Pawn(5, 1, PieceColor.WHITE));
        pieces.add(new Pawn(6, 1, PieceColor.WHITE));
        pieces.add(new Pawn(7, 1, PieceColor.WHITE));   
        pieces.add(new Rook(0, 0, PieceColor.WHITE));
        pieces.add(new Rook(7, 0, PieceColor.WHITE));
        pieces.add(new Knight(1, 0, PieceColor.WHITE));
        pieces.add(new Knight(6, 0, PieceColor.WHITE));
        pieces.add(new Bishop(2, 0, PieceColor.WHITE));
        pieces.add(new Bishop(5, 0, PieceColor.WHITE));
        pieces.add(new King(3, 0, PieceColor.WHITE));
        pieces.add(new Queen(4, 0, PieceColor.WHITE));

        pieces.add(new Pawn(0, 6, PieceColor.BLACK));
        pieces.add(new Pawn(1, 6, PieceColor.BLACK));
        pieces.add(new Pawn(2, 6, PieceColor.BLACK));
        pieces.add(new Pawn(3, 6, PieceColor.BLACK));
        pieces.add(new Pawn(4, 6, PieceColor.BLACK));
        pieces.add(new Pawn(5, 6, PieceColor.BLACK));
        pieces.add(new Pawn(6, 6, PieceColor.BLACK));
        pieces.add(new Pawn(7, 6, PieceColor.BLACK));
        pieces.add(new Rook(0, 7, PieceColor.BLACK));
        pieces.add(new Rook(7, 7, PieceColor.BLACK));
        pieces.add(new Knight(1, 7, PieceColor.BLACK));
        pieces.add(new Knight(6, 7, PieceColor.BLACK));
        pieces.add(new Bishop(2, 7, PieceColor.BLACK));
        pieces.add(new Bishop(5, 7, PieceColor.BLACK));
        pieces.add(new King(3, 7, PieceColor.BLACK));
        pieces.add(new Queen(4, 7, PieceColor.BLACK));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw((Graphics2D) g);
        for (Piece piece : pieces) {
            piece.draw((Graphics2D) g);
        }

    }

    public void update() {
        if(mouseListener.isPressed) {
            if(activePiece == null) {
                for(Piece piece : pieces) {
                    if((piece.col == mouseListener.x / Board.SQUARE_SIZE) && (piece.row == mouseListener.y / Board.SQUARE_SIZE)) {
                        activePiece = piece;
                        break;
                    }
                }
            } else {  
                activePiece.xPixel = mouseListener.x - Board.SQUARE_SIZE / 2;
                activePiece.yPixel = mouseListener.y - Board.SQUARE_SIZE / 2;
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
            }
        }
    }

}