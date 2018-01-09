/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces.movepatterns;

import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class DragonKnightPattern extends MovePattern
{
    private final RookPattern _rook;
    
    public DragonKnightPattern(Team team)
    {
        super(team);
        _rook = new RookPattern(team);
    }
    
    @Override
    public void setTeam(Team newTeam)
    {
        super.setTeam(newTeam);
        _rook.setTeam(newTeam);
    }
    
    
    @Override
    public boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = _rook.getValidMoves(cells, row, col);
        
        addIfValidMove(cells, valid, row + 1, col + 1);
        addIfValidMove(cells, valid, row + 1, col - 1);
        addIfValidMove(cells, valid, row - 1, col + 1);
        addIfValidMove(cells, valid, row - 1, col - 1);
        
        return valid;
    }
}
