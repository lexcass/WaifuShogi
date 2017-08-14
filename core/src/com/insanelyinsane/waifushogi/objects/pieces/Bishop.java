/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;

/**
 *
 * @author A Wild Popo Appeared
 */
public class Bishop extends Piece
{
    public Bishop(Team team)
    {
        super(Type.BISHOP, team);
    }
    
    @Override
    protected boolean[][] findValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        // Flags to indicate that cells in this direction
        // should no longer be checked for validity.
        boolean stopUpLeft = false;
        boolean stopUpRight = false;
        boolean stopDownLeft = false;
        boolean stopDownRight = false;
        
        
        for (int i = 1; i < Board.ROWS; i++)
        {
            // If check shouldn't stop,
            if (!stopUpLeft)
            {
                // then check will continue as long as this cell is a valid move.
                stopUpLeft = !addIfValidMove(cells, valid, row + i, col - i);
                
                // The check will continue if the valid move contains an opponent's piece.
                // If the cell is in bounds and it contains a piece, stop checking regardless of team affiliation.
                if (inBounds(row + i, col - i)) stopUpLeft = (cells[row + i][col - i] != null);
            }
            
            if (!stopUpRight)
            {
                stopUpRight = !addIfValidMove(cells, valid, row + i, col + i);
                if (inBounds(row + i, col + i)) stopUpRight = (cells[row + i][col + i] != null);
            }
            
            if (!stopDownLeft)
            {
                stopDownLeft = !addIfValidMove(cells, valid, row - i, col - i);
                if (inBounds(row - i, col - i)) stopDownLeft = (cells[row - i][col - i] != null);
            }
            
            if (!stopDownRight)
            {
                stopDownRight = !addIfValidMove(cells, valid, row - i, col + i);
                if (inBounds(row - i, col + i)) stopDownRight = (cells[row - i][col + i] != null);
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
