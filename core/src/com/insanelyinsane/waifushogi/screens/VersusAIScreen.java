/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.ai.AIOpponent;
import com.insanelyinsane.waifushogi.GameState;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.gamecomponents.GameComponentType;
import com.insanelyinsane.waifushogi.handlers.LocalRequestHandler;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.ui.UIController;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Cassady
 */
public class VersusAIScreen extends MatchScreen
{
    private AIOpponent _ai;
    private Player _bluePlayer;
    
    
    public VersusAIScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
        _bluePlayer = new Player(Player.Type.AI, false);
    }
    
    @Override
    protected void setupMatch()
    {
        RequestHandler handler = getRequestHandler();
        Referee referee = getReferee();
        GameState state = getGameState();
        
        // Create AIOpponent and start AI Thread
        _ai = new AIOpponent(handler, state);
        handler.registerTurnEndListener(_ai);
        getUI().registerQuitListener(_ai);
        _ai.start();
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
