/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces.movepatterns;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.Piece;
import static com.insanelyinsane.waifushogi.pieces.Piece.inBounds;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class LancePattern extends MovePattern
{
    public LancePattern(Team team)
    {
        super(team);
    }
    
    @Override
    public boolean[][] getValidMoves(Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        boolean stop = false;
        
        for (int r = 1; r < Board.ROWS; r++)
        {
            int offset = (getTeam() == Team.RED ? r : -r);
            
            if (!stop)
            {
                // If not valid, stop looking this direction
                if (!addIfValidMove(cells, valid, row + offset, col))
                {
                    stop = true;
                }
                // If valid but has an opponent's piece, also stop
                else if (inBounds(row + offset, col))
                {
                    stop = (cells[row + offset][col] != null);
                }
            }
        }
        
        return valid;
    }
}
