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
public class GoldGeneralPattern extends MovePattern
{
    public GoldGeneralPattern(Team team)
    {
        super(team);
    }
    
    @Override
    public boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        addIfValidMove(cells, valid, row + 1, col);
        addIfValidMove(cells, valid, row - 1, col);
        addIfValidMove(cells, valid, row, col + 1);
        addIfValidMove(cells, valid, row, col - 1);
        
        if (getTeam() == Team.RED)
        {
            addIfValidMove(cells, valid, row + 1, col - 1);
            addIfValidMove(cells, valid, row + 1, col + 1);
        }
        else
        {
            addIfValidMove(cells, valid, row - 1, col - 1);
            addIfValidMove(cells, valid, row - 1, col + 1);
        }
        
        return valid;
    }
}
