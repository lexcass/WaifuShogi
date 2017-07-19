/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

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
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that the piece can move to when in play.
     * @param cells
     * @param row
     * @param col
     * @return 
     */
    protected abstract Cell[][] findValidMoves(final Cell[][] cells, int row, int col);
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that a piece can be placed into after being captured.
     * @param cells
     * @return 
     */
    protected abstract Cell[][] findValidReplacements(final Cell[][] cells);
    
    
    public final Cell[][] getValidMoves(final Cell[][] cells, int row, int col)
    {
        return findValidMoves(cells, row, col);
    }
    
    
    public final Cell[][] getValidReplacements(final Cell[][] cells)
    {
        return findValidReplacements(cells);
    }
}
