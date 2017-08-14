/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;

/**
 *
 * @author alex
 */
public class Rook extends Piece
{
    public Rook(Team team)
    {
        super(Type.ROOK, team);
    }
    
    @Override
    protected boolean[][] findValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        boolean stopUp = false;
        boolean stopDown = false;
        boolean stopLeft = false;
        boolean stopRight = false;
        
        for (int r = 1; r < Board.ROWS; r++)
        {
            if (!stopUp)
            {
                // If not valid, stop looking this direction
                if (!addIfValidMove(cells, valid, row + r, col))
                {
                    stopUp = true;
                }
                // If valid but has an opponent's piece, also stop
                else if (inBounds(row + r, col))
                {
                    stopUp = (cells[row + r][col] != null);
                }
            }
            
            if (!stopDown)
            {
                if (!addIfValidMove(cells, valid, row - r, col))
                {
                    stopDown = true;
                }
                else if (inBounds(row - r, col))
                {
                    stopDown = (cells[row - r][col] != null);
                }
            }
        }
        
        for (int c = 1; c < Board.COLS; c++)
        {
            if (!stopRight)
            {
                if (!addIfValidMove(cells, valid, row, col + c))
                {
                    stopRight = true;
                }
                else if (inBounds(row, col + c))
                {
                    stopRight = (cells[row][col + c] != null);
                }
            }
            
            if (!stopLeft)
            {
                if (!addIfValidMove(cells, valid, row, col - c))
                {
                    stopLeft = true;
                }
                else if (inBounds(row, col - c))
                {
                    stopLeft = (cells[row][col - c] != null);
                }
            }
        }
        
        return valid;
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return checkAllForReplacement(cells);
    }
    
    
    @Override
    public Piece getPromotedVersion() { return this; }
}
