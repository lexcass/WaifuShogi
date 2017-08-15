/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.movepatterns.JadeGeneralPattern;

/**
 *
 * @author A Wild Popo Appeared
 */
public class JadeGeneral extends Piece
{
    public JadeGeneral(Team team)
    {
        super(Type.JADE, team, new JadeGeneralPattern(team), null);
    }
    
    
    @Override
    protected boolean[][] findValidReplacements(final Piece[][] cells)
    {
        return new boolean[Board.ROWS][Board.COLS];
    }
}
