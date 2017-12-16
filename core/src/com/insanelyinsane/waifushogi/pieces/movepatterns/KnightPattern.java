/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces.movepatterns;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class KnightPattern extends MovePattern
{
    public KnightPattern(Team team)
    {
        super(team);
    }
    
    
    @Override
    public boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        int offset = (getTeam() == Team.RED ? 2 : -2);
        
        addIfValidMove(cells, valid, row + offset, col + 1);
        addIfValidMove(cells, valid, row + offset, col - 1);
        
        return valid;
    }
}
