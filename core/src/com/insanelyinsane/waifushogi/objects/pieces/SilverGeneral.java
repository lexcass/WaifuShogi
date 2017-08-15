/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.GoldGeneralPattern;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.SilverGeneralPattern;

/**
 *
 * @author A Wild Popo Appeared
 */
public class SilverGeneral extends Piece
{
    public SilverGeneral(Team team)
    {
        super(Type.SILVER, team, new SilverGeneralPattern(team), new GoldGeneralPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return checkAllForReplacement(cells);
    }
}
