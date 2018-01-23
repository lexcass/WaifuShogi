/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.pieces.movepatterns.GoldGeneralPattern;

/**
 *
 * @author Alex Cassady
 */
public class GoldGeneral extends Piece
{
    public GoldGeneral(Team team)
    {
        super(Type.GOLD, team, new GoldGeneralPattern(team), null);
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        return checkAllForDrop(cells);
    }
    
    
    @Override
    public Piece copy()
    {
        GoldGeneral copy = new GoldGeneral(this.getTeam());
        this.cloneStateInto(copy);
        
        return copy;
    }
}
