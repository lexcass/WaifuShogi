/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.handlers;

import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.Sender;
import com.insanelyinsane.waifushogi.interfaces.PromotionConfirmation;
import com.insanelyinsane.waifushogi.interfaces.WinConfirmation;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.screens.MatchScreen;

/**
 *
 * @author Alex Cassady
 */
public class NetworkRequestHandler extends RequestHandler
{
    
    @Override
    public void requestSelection(Sender from, Piece target, int r, int c)
    {
        
    }
    
    @Override
    public void requestMove(Sender sender, int r, int c)
    {
        
    }
    
    @Override
    public void requestDrop(int r, int c)
    {
        
    }
    
    @Override
    protected void requestPromotion(Piece p, boolean auto)
    {
        
    }
    
    
    @Override
    public void handlePromotion(Piece p)
    {
        
    }
    
    
    @Override
    public void handleGameWon(Boolean playAgain)
    {
        
    }
    
    
    
    public NetworkRequestHandler(MatchScreen screen, Referee ref, SelectionListener highlighter, PromotionConfirmation c, WinConfirmation w)
    {
        super(screen, ref, highlighter, c, w);
    }
}
