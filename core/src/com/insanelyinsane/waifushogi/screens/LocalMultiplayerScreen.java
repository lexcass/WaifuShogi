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
import com.insanelyinsane.waifushogi.handlers.LocalRequestHandler;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author Alex Cassady
 */
public class LocalMultiplayerScreen extends MatchScreen
{
    private Player _bluePlayer;
    
    public LocalMultiplayerScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
        _bluePlayer = new Player(Player.Type.LOCAL_TWO, false);
    }
    
    
    @Override
    protected void setupMatch()
    {
        
    }
    
    
    @Override
    protected RequestHandler initRequestHandler()
    {
        return new LocalRequestHandler(this, _referee, getComponent(GameComponentType.HIGHLIGHTER), _ui, _ui);
    }
    
    
    @Override
    protected Player getBluePlayer()
    {
        return _bluePlayer;
    }
}
