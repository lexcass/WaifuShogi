/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.pieces.movepatterns.BishopPattern;
import com.insanelyinsane.waifushogi.pieces.movepatterns.DragonHorsePattern;

/**
 *
 * @author Alex Cassady
 */
public class Bishop extends Piece
{
    public Bishop(Team team)
    {
        super(Type.BISHOP, team, new BishopPattern(team), new DragonHorsePattern(team));
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        return checkAllForDrop(cells);
    }
    
    
    @Override
    public Piece copy()
    {
        Bishop copy = new Bishop(this.getTeam());
        this.cloneStateInto(copy);
        
        return copy;
    }
}
