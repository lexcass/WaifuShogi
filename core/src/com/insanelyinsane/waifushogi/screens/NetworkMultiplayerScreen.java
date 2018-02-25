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
import com.insanelyinsane.waifushogi.networking.MultiplayerServer;
import com.insanelyinsane.waifushogi.networking.NetworkParams;
import com.insanelyinsane.waifushogi.networking.ServerThread;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.ui.UIController;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Alex Cassady
 */
public class NetworkMultiplayerScreen extends MatchScreen
{
    private Team _myTeam;
    private boolean _isHost;
    private String _ip;
    private ServerThread _serverThread;
    private NetworkParams _netParams;
    
    
    public NetworkMultiplayerScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
//        _ip = ip;
//        _isHost = isHost;
//        
//        if (_isHost)
//        {
//            try
//            {
//                new MultiplayerServer().acceptConnections();
//            }
//            catch (IOException e)
//            {
//                System.err.println("Could not initialize the game server. Please restart the game and try again.");
//            }
//        }
//        
//        try
//        {
//            Socket socket = new Socket(_ip, MultiplayerServer.PORT_NUMBER);
//            _serverThread = new ServerThread(socket);
//            new Thread(_serverThread).start();
//        }
//        catch (UnknownHostException e)
//        {
//            System.err.println("Unknown host.");
//        }
//        catch (IOException e)
//        {
//            System.err.println("Failed to connect to host.");
//        }
//        
//        _netParams = new NetworkParams();
    }
    
    
    @Override
    protected void setupMatch()
    {
        // assign a team to this player by requesting assignment from server
        
        
        
        
    }
    
    
    @Override
    protected RequestHandler initRequestHandler()
    {
        return new NetworkRequestHandler(this, _referee, getComponent(GameComponentType.HIGHLIGHTER), _ui, _ui);
    }
    
    
    @Override
    protected Player getBluePlayer()
    {
        return null;
    }
}
