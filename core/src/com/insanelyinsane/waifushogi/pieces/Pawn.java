/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.movepatterns.GoldGeneralPattern;
import com.insanelyinsane.waifushogi.pieces.movepatterns.PawnPattern;

/**
 *
 * @author alex
 */
public class Pawn extends Piece
{
    public Pawn(Team team)
    {
        super(Type.PAWN, team, new PawnPattern(team), new GoldGeneralPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        // Columns that contain an unpromoted pawn from the same team.
        // Pawns can't be dropped on these columns.
        boolean[] forbiddenCols = new boolean[Board.COLS];
        for (int c = 0; c < cells[0].length; c++)
        {
            for (int r = 0; r < cells.length; r++)
            {
                if (cells[r][c] != null)
                {
                    if (cells[r][c].getType() == Piece.Type.PAWN && cells[r][c].getTeam() == getTeam())
                    {
                        forbiddenCols[c] = !cells[r][c].isPromoted();
                        break;
                    }
                }
            }
        }
        
        // Iterate over each cell on the board (ignore 9th row since pawn can never be dropped there)
        int start = (getTeam() == Team.RED ? 0 : 1);
        int end   = (getTeam() == Team.RED ? 8 : 9);
        
        for (int r = start; r < end; r++)
        {
            for (int c = 0; c < cells[r].length; c++)
            {
                // If there is no piece in the current cell
                addIfValidDrop(cells, valid, r, c);
                // And no pawn on the player's team is in the column
                if (forbiddenCols[c]) valid[r][c] = false;
            }
        }
        
        return valid;
    }
}
