/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;

/**
 *
 * @author alex
 */
public abstract class Piece 
{
    private final Team _team;
    
    public Piece(Team team)
    {
        _team = team;
    }
    
    public Team getTeam() { return _team; }
    
    
    protected abstract Cell[][] findValidMoves(final Cell[][] cells, int row, int col);
    
    
    protected abstract Cell[][] findValidReplacements(final Cell[][] cells);
    
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that the piece can move to when in play.
     * @param cells
     * @param row
     * @param col
     * @return 
     */
    public final Cell[][] getValidMoves(final Cell[][] cells, int row, int col)
    {
        if (row >= 0 && col >= 0 && row < Board.ROWS && col < Board.COLS)
        {
            return findValidMoves(cells, row, col);
        }
        else
        {
            return new Cell[Board.ROWS][Board.COLS];
        }
    }
    
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that a piece can be placed into after being captured.
     * @param cells
     * @return 
     */
    public final Cell[][] getValidReplacements(final Cell[][] cells)
    {
        return findValidReplacements(cells);
    }
    
    
    /**
     * A helper method for checking validity of a move.
     * @param board
     * @param valid
     * @param r
     * @param c
     */
    public final void addIfValid(final Cell[][] board, Cell[][] valid, int r, int c)
    {
        if (r >= 0 && c >=0 && r < Board.ROWS && c < Board.COLS)
        {
            Cell toCheck = board[r][c];
            Cell toAdd = null;

            if (toCheck != null)
            {
                Piece piece = toCheck.getPiece();

                if (piece != null)
                {
                    if (piece.getTeam() != _team) toAdd = toCheck;
                }
                else
                {
                    toAdd = toCheck;
                }
            }
            
            valid[r][c] = toAdd;
        }
    }
}
