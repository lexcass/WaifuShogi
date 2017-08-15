/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.GoldGeneralPattern;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.KnightPattern;

/**
 *
 * @author A Wild Popo Appeared
 */
public class Knight extends Piece
{
    public Knight(Team team)
    {
        super(Type.KNIGHT, team, new KnightPattern(team), new GoldGeneralPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        // Iterate over each cell on the board (ignore 8th and 9th rows since knight can never be dropped there)
        int start = (getTeam() == Team.RED ? 0 : 2);
        int end   = (getTeam() == Team.RED ? 7 : 9);
        
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
