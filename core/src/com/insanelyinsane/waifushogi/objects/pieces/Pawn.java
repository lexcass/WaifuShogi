/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.PawnPattern;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.RookPattern;

/**
 *
 * @author alex
 */
public class Pawn extends Piece
{
    public Pawn(Team team)
    {
        super(Type.PAWN, team, new PawnPattern(team), new RookPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        // Iterate over each cell on the board (ignore 9th row since pawn can never be dropped there)
        int start = (getTeam() == Team.RED ? 0 : 1);
        int end   = (getTeam() == Team.RED ? 8 : 9);
        
        for (int r = start; r < end; r++)
        {
            for (int c = 0; c < cells[r].length; c++)
            {
                // If there is no piece in the current cell
                addIfValidReplacement(cells, valid, r, c);
            }
        }
        
        return valid;
    }
}
