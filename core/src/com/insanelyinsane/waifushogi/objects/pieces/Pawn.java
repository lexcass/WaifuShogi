/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Cell;
import java.util.LinkedList;
import java.util.List;

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
    public List<Cell> getValidMoves(final Cell[][] cells, int row, int col)
    {
        List<Cell> valid = new LinkedList<>();
        
        Cell cell = cells[row + 1][col];
        if (cell != null)
        {
            Piece piece = cell.getPiece();
            if (piece != null)
            {
                if (piece.getTeam().equals(getTeam())) valid.add(cell);
            }
            else
            {
                valid.add(cell);
            }
        }
        
        return valid;
    }
    
    
    @Override
    public List<Cell> getValidReplacements(final Cell[][] cells)
    {
        return new LinkedList<>();
    }
}
