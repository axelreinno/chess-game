package com.nostratech.chess.pieces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.nostratech.chess.Board;
import com.nostratech.chess.GamePanel;

public class Piece {
    public int col, row, preCol, preRow, xPixel, yPixel;
    public boolean moved;
    public PieceColor color;
    public BufferedImage image;
    public Piece hittingPiece;

    public Piece(PieceColor color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        xPixel = getXPixel(col);
        yPixel = getYPixel(row);
        preCol = col;
        preRow = row;
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

    public int getXPixel(int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getYPixel(int row) {
        return row * Board.SQUARE_SIZE;
    }

    public int getCol(int x) {
        return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public int getRow(int y) {
        return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public int getIndex() {
        for (int index = 0; index < GamePanel.simulationPieces.size(); index++) {
            if (GamePanel.simulationPieces.get(index) == this) {
                return index;
            }
        }
        return 0;
    }

    public boolean canMove(int targetCol, int targetRow) {
        return false;
    }

    public Piece getHittingPiece(int targetCol, int targetRow) {
        for (Piece piece : GamePanel.simulationPieces) {
            if (piece.col == targetCol && piece.row == targetRow && piece != this) {
                return piece;
            }
        }
        return null;
    }

    public boolean isWithinBoard(int targetCol, int targetRow) {
        return targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7;
    }

    public boolean isSameSquare(int targetCol, int targetRow) {
        return targetCol == preCol && targetRow == preRow;
    }

    public boolean isValidSquare(int targetCol, int targetRow) {
        hittingPiece = getHittingPiece(targetCol, targetRow);

        if (hittingPiece == null) {
            return true;
        } else {
            if (hittingPiece.color != this.color) {
                return true;
            } else {
                hittingPiece = null;
            }
        }

        return false;
    }

    public boolean isPieceOnStraightLine(int targetCol, int targetRow) {
        for (int index = preCol - 1; index > targetCol; index--) {
            for (Piece piece : GamePanel.simulationPieces) {
                if (piece.col == index && piece.row == targetRow) {
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        for (int index = preCol + 1; index < targetCol; index++) {
            for (Piece piece : GamePanel.simulationPieces) {
                if (piece.col == index && piece.row == targetRow) {
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        for (int index = preRow - 1; index > targetRow; index--) {
            for (Piece piece : GamePanel.simulationPieces) {
                if (piece.col == targetCol && piece.row == index) {
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        for (int index = preRow + 1; index < targetRow; index++) {
            for (Piece piece : GamePanel.simulationPieces) {
                if (piece.col == targetCol && piece.row == index) {
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isPieceOnDiagonalLine(int targetCol, int targetRow) {
        if (targetRow < preRow) {
            for (int index = preCol - 1; index > targetCol; index--) {
                int difference = Math.abs(index - preCol);
                for (Piece piece : GamePanel.simulationPieces) {
                    if (piece.col == index && piece.row == preRow - difference) {
                        hittingPiece = piece;
                        return true;
                    }
                }
            }

            for (int index = preCol + 1; index < targetCol; index++) {
                int difference = Math.abs(index - preCol);
                for (Piece piece : GamePanel.simulationPieces) {
                    if (piece.col == index && piece.row == preRow - difference) {
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
        }

        if (targetRow > preRow) {
            for (int index = preCol - 1; index > targetCol; index--) {
                int difference = Math.abs(index - preCol);
                for (Piece piece : GamePanel.simulationPieces) {
                    if (piece.col == index && piece.row == preRow + difference) {
                        hittingPiece = piece;
                        return true;
                    }
                }
            }

            for (int index = preCol + 1; index < targetCol; index++) {
                int difference = Math.abs(index - preCol);
                for (Piece piece : GamePanel.simulationPieces) {
                    if (piece.col == index && piece.row == preRow + difference) {
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void updatePosition() {
        xPixel = getXPixel(col);
        yPixel = getYPixel(row);
        preCol = getCol(xPixel);
        preRow = getRow(yPixel);
        moved = true;
    }

    public void resetPosition() {
        col = preCol;
        row = preRow;
        xPixel = getXPixel(col);
        yPixel = getYPixel(row);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, xPixel, yPixel, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
