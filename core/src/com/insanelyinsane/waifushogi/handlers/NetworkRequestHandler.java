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
import com.insanelyinsane.waifushogi.networking.NetworkParams;
import com.insanelyinsane.waifushogi.networking.NetworkRequestType;
import com.insanelyinsane.waifushogi.networking.Packet;
import com.insanelyinsane.waifushogi.networking.ServerThread;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.screens.MatchScreen;

/**
 *
 * @author Alex Cassady
 */
public class NetworkRequestHandler extends RequestHandler
{
    
    private ServerThread _serverThread;
    private NetworkParams _netParams;
    
    
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
    
    
    public void setTeam(Packet data)
    {
        _netParams.setTeam(Team.valueOf(data.getData()));
        System.out.println("You are on team " + _netParams.getTeam().toString());
    }
    
    
    public NetworkParams getNetworkParams() { return _netParams; }
    
    
    public void startGame(ServerThread thread, NetworkParams netParams)
    {
        _serverThread = thread;
        _netParams = netParams;
    }
    
    
    public NetworkRequestHandler(MatchScreen screen, Referee ref, SelectionListener highlighter, PromotionConfirmation c, WinConfirmation w)
    {
        super(screen, ref, highlighter, c, w);
//        
    }
}
