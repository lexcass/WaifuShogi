/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces.movepatterns;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author A Wild Popo Appeared
 */
public class DragonHorsePattern extends MovePattern
{
    private final BishopPattern _bishop;
    
    public DragonHorsePattern(Team team)
    {
        super(team);
        _bishop = new BishopPattern(team);
    }
    
    @Override
    public boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = _bishop.getValidMoves(cells, row, col);
        
        addIfValidMove(cells, valid, row + 1, col);
        addIfValidMove(cells, valid, row - 1, col);
        addIfValidMove(cells, valid, row, col + 1);
        addIfValidMove(cells, valid, row, col - 1);
        
        return valid;
    }
}
