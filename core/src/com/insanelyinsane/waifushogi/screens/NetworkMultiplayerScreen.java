/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.gamecomponents.GameComponentType;
import com.insanelyinsane.waifushogi.handlers.NetworkRequestHandler;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.networking.NetworkParams;
import com.insanelyinsane.waifushogi.networking.ServerThread;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author Alex Cassady
 */
public class NetworkMultiplayerScreen extends MatchScreen
{
    private Team _myTeam;
    private NetworkRequestHandler _netRequestHandler;
    private ServerThread _serverThread;
    private NetworkParams _params;
    
    
    public NetworkMultiplayerScreen(WaifuShogi game, SpriteBatch batch, UIController ui, Object serverThread, Object netParams)
    {
        super(game, batch, ui);
        _serverThread = (ServerThread)serverThread;
        _params = (NetworkParams)netParams;
        
        if (_serverThread == null) { System.out.println("Null serverThread."); this.handleGameQuit(); }
        if (_params == null) { System.out.println("Null netParams."); }
        
        
    }
    
    
    @Override
    protected void setupMatch()
    {
        // assign a team to this player by requesting assignment from server
        
        _serverThread.setNetworkRequestHandler(_netRequestHandler);
        _netRequestHandler.startGame(_serverThread, _params);
        
        
    }
    
    
    @Override
    protected RequestHandler initRequestHandler()
    {
        _netRequestHandler = new NetworkRequestHandler(this, _referee, getComponent(GameComponentType.HIGHLIGHTER), _ui, _ui);
        return _netRequestHandler;
    }
    
    
    @Override
    protected Player getBluePlayer()
    {
        return null;
    }
}
