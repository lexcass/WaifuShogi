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
    public Cell[][] getValidMoves(final Cell[][] cells, int row, int col)
    {
        Cell[][] valid = new Cell[Board.ROWS][Board.COLS];
        
        if (row + 1 >= cells.length || col >= cells[0].length)
        {
            return valid;
        }
        
        Cell cell = cells[row + 1][col];
        if (cell != null)
        {
            Piece piece = cell.getPiece();
            if (piece != null)
            {
                if (piece.getTeam().equals(getTeam())) valid[row + 1][col] = cell;
            }
            else
            {
                valid[row + 1][col] = cell;
            }
        }
        
        return valid;
    }
    
    
    @Override
    public Cell[][] getValidReplacements(final Cell[][] cells)
    {
        return new Cell[0][0];
    }
}
