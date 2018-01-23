/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.pieces.movepatterns.GoldGeneralPattern;
import com.insanelyinsane.waifushogi.pieces.movepatterns.SilverGeneralPattern;

/**
 *
 * @author Alex Cassady
 */
public class SilverGeneral extends Piece
{
    public SilverGeneral(Team team)
    {
        super(Type.SILVER, team, new SilverGeneralPattern(team), new GoldGeneralPattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        return checkAllForDrop(cells);
    }
    
    
    @Override
    public Piece copy()
    {
        SilverGeneral copy = new SilverGeneral(this.getTeam());
        this.cloneStateInto(copy);
        
        return copy;
    }
}
