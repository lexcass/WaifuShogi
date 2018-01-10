/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author Alex Cassady
 */
public class LocalMultiplayerScreen extends MatchScreen
{
    public LocalMultiplayerScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
    }
    
    
    @Override
    protected void setupMatch()
    {
        
    }
    
    
    @Override
    protected Player getBluePlayer()
    {
        return new Player(Player.Type.LOCAL_TWO);
    }
}
