/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Board;

/**
 *
 * @author alex
 */
public class Referee implements SelectionListener
{
    private final Board _board;
    
    
    public Referee(Board board)
    {
        _board = board;
    }
    
    
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        
    }
}
