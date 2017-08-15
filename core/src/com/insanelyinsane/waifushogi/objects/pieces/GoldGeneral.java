/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.GoldGeneralPattern;

/**
 *
 * @author A Wild Popo Appeared
 */
public class GoldGeneral extends Piece
{
    public GoldGeneral(Team team)
    {
        super(Type.GOLD, team, new GoldGeneralPattern(team), null);
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return checkAllForReplacement(cells);
    }
}
