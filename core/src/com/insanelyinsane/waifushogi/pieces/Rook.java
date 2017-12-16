/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.pieces.movepatterns.DragonKnightPattern;
import com.insanelyinsane.waifushogi.pieces.movepatterns.RookPattern;

/**
 *
 * @author alex
 */
public class Rook extends Piece
{
    public Rook(Team team)
    {
        super(Type.ROOK, team, new RookPattern(team), new DragonKnightPattern(team));
    }
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        return checkAllForDrop(cells);
    }
}
