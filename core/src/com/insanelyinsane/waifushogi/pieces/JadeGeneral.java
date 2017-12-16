/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.movepatterns.JadeGeneralPattern;

/**
 *
 * @author Alex Cassady
 */
public class JadeGeneral extends Piece
{
    public JadeGeneral(Team team)
    {
        super(Type.JADE, team, new JadeGeneralPattern(team), null);
    }
    
    
    @Override
    protected boolean[][] findValidDrops(final Piece[][] cells)
    {
        return new boolean[Board.ROWS][Board.COLS];
    }
}
