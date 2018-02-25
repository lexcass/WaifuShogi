/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenType;

/**
 *
 * @author Alex Cassady
 */
public class MainMenuUI extends UI 
{
    private TextButton _playButton;
    private TextButton _networkButton;
    private TextButton _quitButton;
    private TextButton _aiButton;
    
    private String _ipText;
    
    
    public MainMenuUI(Stage stage, Screen q)
    {
        super(stage, q);
    }
    
    @Override
    protected void createElements()
    {
        Table table = new Table();
        
        _playButton = new TextButton("Local Multiplayer", getSkin());
        _playButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                getScreen().changeScreen(ScreenType.LOCAL_MULTIPLAYER);
            }
        });
        
        
        _networkButton = new TextButton("Network Multiplayer", getSkin());
        _networkButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                Gdx.input.getTextInput
                (
                    new TextInputListener()
                    {
                        @Override
                        public void input (String text) 
                        {
                            MainMenuUI.this.getScreen().changeScreen(ScreenType.NETWORK_MULTIPLAYER);
                        }

                        @Override
                        public void canceled () 
                        {}
                    }, 
                    "IP Address", 
                    "XXX.XXX.XX.XX", 
                    ""
                );
            }
        });
        
        
        _aiButton = new TextButton("Practice Vs. AI", getSkin());
        _aiButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                getScreen().changeScreen(ScreenType.VERSUS_AI);
            }
        });
        
        
        _quitButton = new TextButton("Quit Game", getSkin());
        _quitButton.addListener(new InputListener()
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
        table.add(_playButton);
        table.row();
        table.add(_networkButton);
        table.row();
        table.add(_aiButton);
        table.row();
        table.add(_quitButton);
        table.row();
        
        addElement(table);
    }
}
