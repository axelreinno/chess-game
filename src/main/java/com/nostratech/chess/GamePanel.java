package com.nostratech.chess;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.nostratech.chess.pieces.Bishop;
import com.nostratech.chess.pieces.King;
import com.nostratech.chess.pieces.Knight;
import com.nostratech.chess.pieces.Pawn;
import com.nostratech.chess.pieces.Piece;
import com.nostratech.chess.pieces.PieceColor;
import com.nostratech.chess.pieces.Queen;
import com.nostratech.chess.pieces.Rook;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    boolean canMove;
    boolean validSquare;

    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simulationPieces = new ArrayList<>();

    Thread gameThread;
    Board board = new Board();
    MouseListener mouseListener = new MouseListener();

    Piece activePiece;
    PieceColor currentColor = PieceColor.WHITE;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);

        setPieces();
        copyPieces(pieces, simulationPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public static void setPieces() {
        pieces.add(new Pawn(PieceColor.WHITE, 0, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 1, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 2, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 3, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 4, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 5, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 6, 6));
        pieces.add(new Pawn(PieceColor.WHITE, 7, 6));
        pieces.add(new Rook(PieceColor.WHITE, 0, 7));
        pieces.add(new Rook(PieceColor.WHITE, 7, 7));
        pieces.add(new Knight(PieceColor.WHITE, 1, 7));
        pieces.add(new Knight(PieceColor.WHITE, 6, 7));
        pieces.add(new Bishop(PieceColor.WHITE, 2, 7));
        pieces.add(new Bishop(PieceColor.WHITE, 5, 7));
        pieces.add(new Queen(PieceColor.WHITE, 3, 7));
        pieces.add(new King(PieceColor.WHITE, 4, 7));

        pieces.add(new Pawn(PieceColor.BLACK, 0, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 1, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 2, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 3, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 4, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 5, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 6, 1));
        pieces.add(new Pawn(PieceColor.BLACK, 7, 1));
        pieces.add(new Rook(PieceColor.BLACK, 0, 0));
        pieces.add(new Rook(PieceColor.BLACK, 7, 0));
        pieces.add(new Knight(PieceColor.BLACK, 1, 0));
        pieces.add(new Knight(PieceColor.BLACK, 6, 0));
        pieces.add(new Bishop(PieceColor.BLACK, 2, 0));
        pieces.add(new Bishop(PieceColor.BLACK, 5, 0));
        pieces.add(new Queen(PieceColor.BLACK, 3, 0));
        pieces.add(new King(PieceColor.BLACK, 4, 0));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();

        for (Piece piece : source) {
            target.add(piece);
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (mouseListener.pressed) {
            if (activePiece == null) {
                for (Piece piece : simulationPieces) {
                    if (piece.color == currentColor
                            && piece.col == mouseListener.x / Board.SQUARE_SIZE
                            && piece.row == mouseListener.y / Board.SQUARE_SIZE) {
                        activePiece = piece;
                    }
                }
            } else {
                simulateMove();
            }
        } else {
            if (activePiece != null) {
                if (validSquare) {
                    copyPieces(simulationPieces, pieces);
                    activePiece.updatePosition();

                    changePlayer();
                } else {
                    copyPieces(pieces, simulationPieces);
                    activePiece.resetPosition();
                    activePiece = null;
                }
            }
        }
    }

    private void simulateMove() {
        canMove = false;
        validSquare = false;

        copyPieces(pieces, simulationPieces);

        activePiece.xPixel = mouseListener.x - Board.HALF_SQUARE_SIZE;
        activePiece.yPixel = mouseListener.y - Board.HALF_SQUARE_SIZE;
        activePiece.col = activePiece.getCol(activePiece.xPixel);
        activePiece.row = activePiece.getRow(activePiece.yPixel);

        if (activePiece.canMove(activePiece.col, activePiece.row)) {
            canMove = true;

            if (activePiece.hittingPiece != null) {
                simulationPieces.remove(activePiece.hittingPiece.getIndex());
            }

            validSquare = true;
        }
    }

    private void changePlayer() {
        currentColor = currentColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        activePiece = null;
    }   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        board.draw(g2);

        for (Piece piece : simulationPieces) {
            piece.draw(g2);
        }

        if (activePiece != null) {
            if (canMove) {
                g2.setColor(Color.WHITE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
                g2.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            activePiece.draw(g2);
        }

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.ITALIC, 40));
        g2.setColor(Color.WHITE);
        if(currentColor == PieceColor.WHITE) {
            g2.drawString("White's turn", 840, 400);
        } else {
            g2.drawString("Black's turn", 840, 400);
        }
    }
}
