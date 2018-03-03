/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.interfaces.ConnectionHandler;
import com.insanelyinsane.waifushogi.interfaces.ConnectionSetter;
import com.insanelyinsane.waifushogi.interfaces.HostHandler;
import com.insanelyinsane.waifushogi.interfaces.HostSetter;
import com.insanelyinsane.waifushogi.networking.MultiplayerServer;
import com.insanelyinsane.waifushogi.networking.NetworkParams;
import com.insanelyinsane.waifushogi.networking.ServerThread;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.ui.NetworkConnectionUI;
import com.insanelyinsane.waifushogi.ui.UI;
import com.insanelyinsane.waifushogi.ui.UIController;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Alex Cassady
 */
public class NetworkConnectionScreen extends Screen implements ConnectionHandler, HostHandler
{
    private ConnectionSetter _connectionSetter;
    private HostSetter _hostSetter;
    
    private boolean _ready;
    
    
    public NetworkConnectionScreen(WaifuShogi l, SpriteBatch b, UIController ui)
    {
        super(l, b, ui);
        _ready = false;
    }
    
    
    @Override
    public void create()
    {
        UI connectionUI = new NetworkConnectionUI(getStage(), this);
        _connectionSetter = (ConnectionSetter)connectionUI;
        _hostSetter = (HostSetter)connectionUI;
        
        _connectionSetter.setConnectionHandler(this);
        _hostSetter.setHostHandler(this);
        
        getUIController().loadUI(connectionUI);
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
    
    
    @Override
    public void connect(String ip, boolean isHost)
    {
        ServerThread thread = null;
        try
        {
            Socket socket = new Socket(ip, MultiplayerServer.PORT_NUMBER);
            
            thread = new ServerThread(socket);
            new Thread(thread).start();
        }
        catch (IOException e)
        {
            _connectionSetter.onConnectionError("Failed to connect to server.\nMake sure the server is running and that you\nhave an internet connection.");
        }
        
        
        changeScreen(ScreenType.NETWORK_MULTIPLAYER, thread, new NetworkParams(ip, isHost, Team.NONE));
    }
    
    
    @Override
    public void host(String ip)
    {
        try 
        {
            MultiplayerServer server = new MultiplayerServer();
            server.acceptConnections();
        }
        catch (IOException e)
        {
            _connectionSetter.onConnectionError("Failed to create server instance.");
        }
        
        connect(ip, true);
    }
}
