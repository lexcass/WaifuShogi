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
import com.insanelyinsane.waifushogi.ui.NetworkConnectionUI;
import com.insanelyinsane.waifushogi.ui.UI;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author Alex Cassady
 */
public class NetworkConnectionScreen extends Screen implements ConnectionHandler, HostHandler
{
    private ConnectionSetter _connectionSetter;
    private HostSetter _hostSetter;
    
    
    public NetworkConnectionScreen(WaifuShogi l, SpriteBatch b, UIController ui)
    {
        super(l, b, ui);
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
    
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        
        
        return true;
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
    
    
    @Override
    public void connect(String ip)
    {
        System.out.println("Joined game! " + ip);
    }
    
    
    @Override
    public void host(String ip)
    {
        System.out.println("Game created! " + ip);
    }
}
