/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces.movepatterns;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author A Wild Popo Appeared
 */
public class PawnPattern extends MovePattern
{
    public PawnPattern(Team team)
    {
        super(team);
    }
    
    @Override
    public boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        if (getTeam() == Team.RED)
            addIfValidMove(cells, valid, row + 1, col);
        else
            addIfValidMove(cells, valid, row - 1, col);
        
        return valid;
    }
}
