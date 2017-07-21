/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.objects.Board;

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
    protected boolean[][] findValidMoves(final Piece[][] cells, int row, int col)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        addIfValid(cells, valid, row + 1, col);
        
        return valid;
    }
    
    
    @Override
    protected Piece[][] findValidReplacements(final Piece[][] cells)
    {
        return new Piece[0][0];
    }
}
