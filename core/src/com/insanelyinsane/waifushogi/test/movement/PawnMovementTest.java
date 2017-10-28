/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.test.movement;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import com.insanelyinsane.waifushogi.test.Test;
import com.insanelyinsane.waifushogitest.integration.helpers.BoardInitHelper;

/**
 *
 * @author A Wild Popo Appeared
 */
public class PawnMovementTest implements Test
{
    Board _board;
    
    @Override
    public void test() 
    {
        _board = new Board();
        BoardInitHelper helper = new BoardInitHelper();
    }
    
    
    private void moveUnopposed()
    {
    }
    
    
    private void moveToAlly()
    {
        
    }
    
    private void moveToCapture()
    {
        
    }
}
