/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.PawnPattern;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.RookPattern;

/**
 *
 * @author alex
 */
public class Pawn extends Piece
{
    public Pawn(Team team)
    {
        super(Type.PAWN, team, new PawnPattern(team), new RookPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return checkAllForReplacement(cells);
    }
}
