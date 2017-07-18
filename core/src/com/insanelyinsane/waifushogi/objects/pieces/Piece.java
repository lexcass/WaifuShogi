/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Cell;
import java.util.List;

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
     * Returns a list of cells that the piece can move to when in play.
     * @param cells
     * @param row
     * @param col
     * @return 
     */
    public abstract List<Cell> getValidMoves(final Cell[][] cells, int row, int col);
    
    /**
     * Returns a list of cells a place can be placed into after being captured.
     * @return 
     */
    public abstract List<Cell> getValidReplacements(final Cell[][] cells);
}
