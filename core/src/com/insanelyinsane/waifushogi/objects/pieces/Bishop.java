/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.BishopPattern;

/**
 *
 * @author A Wild Popo Appeared
 */
public class Bishop extends Piece
{
    public Bishop(Team team)
    {
        super(Type.BISHOP, team, new BishopPattern(team), null);
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return checkAllForReplacement(cells);
    }
}
