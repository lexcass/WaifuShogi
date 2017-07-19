/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;

/**
 *
 * @author alex
 */
public class Pawn extends Piece
{
    public Pawn(Team team)
    {
        super(team);
    }
    
    @Override
    protected Cell[][] findValidMoves(final Cell[][] cells, int row, int col)
    {
        Cell[][] valid = new Cell[Board.ROWS][Board.COLS];
        
        addIfValid(cells, valid, row + 1, col);
        
        return valid;
    }
    
    
    @Override
    protected Cell[][] findValidReplacements(final Cell[][] cells)
    {
        return new Cell[0][0];
    }
}
