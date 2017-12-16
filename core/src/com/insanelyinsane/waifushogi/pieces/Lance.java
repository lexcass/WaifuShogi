/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.movepatterns.GoldGeneralPattern;
import com.insanelyinsane.waifushogi.pieces.movepatterns.LancePattern;

/**
 *
 * @author Alex Cassady
 */
public class Lance extends Piece
{
    public Lance(Team team)
    {
        super(Piece.Type.LANCE, team, new LancePattern(team), new GoldGeneralPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];

        // Iterate over each cell on the board (ignore 9th row since lance can never be dropped there)
        int start = (getTeam() == Team.RED ? 0 : 1);
        int end   = (getTeam() == Team.RED ? 8 : 9);
        
        for (int r = start; r < end; r++)
        {
            for (int c = 0; c < cells[r].length; c++)
            {
                // If there is no piece in the current cell
                addIfValidDrop(cells, valid, r, c);
            }
        }
        
        return valid;
    }
}