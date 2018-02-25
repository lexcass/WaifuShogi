/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.insanelyinsane.waifushogi.interfaces.ConnectionHandler;
import com.insanelyinsane.waifushogi.interfaces.ConnectionSetter;
import com.insanelyinsane.waifushogi.interfaces.HostHandler;
import com.insanelyinsane.waifushogi.interfaces.HostSetter;
import com.insanelyinsane.waifushogi.screens.Screen;

/**
 *
 * @author Alex Cassady
 */
public class NetworkConnectionUI extends UI implements ConnectionSetter, HostSetter
{
    private ConnectionHandler _connectionHandler;
    private HostHandler _hostHandler;
    
    private TextField _ipTextField;
    private TextButton _hostButton;
    private TextButton _joinButton;
    private TextButton _cancelButton;
    

    public NetworkConnectionUI(Stage stage, Screen q)
    {
        super(stage, q);
    }
    
    @Override
    protected void createElements()
    {
        Table table = new Table();
        
        _ipTextField = new TextField("IP Adress", getSkin());
        
        _joinButton = new TextButton("Join", getSkin());
        _joinButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                _connectionHandler.connect(_ipTextField.getText());
            }
        });
        
        
        _hostButton = new TextButton("Host", getSkin());
        _hostButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                _hostHandler.host(_ipTextField.getText());
            }
        });
        
        
        _cancelButton = new TextButton("Cancel", getSkin());
        _cancelButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                getScreen().handleGameQuit();
            }
        });
        
        
        table.setFillParent(true);
        table.add(_ipTextField);
        table.row();
        table.add(_hostButton);
        table.row();
        table.add(_joinButton);
        table.row();
        table.add(_cancelButton);
        table.row();
        
        addElement(table);
    }
    
    
    @Override
    public void setConnectionHandler(ConnectionHandler h)
    {
        _connectionHandler = h;
    }
    
    
    @Override
    public void setHostHandler(HostHandler h)
    {
        _hostHandler = h;
    }
}
