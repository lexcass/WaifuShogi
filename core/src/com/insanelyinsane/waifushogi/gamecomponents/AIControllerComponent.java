/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.gamecomponents;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.insanelyinsane.waifushogi.AIOpponent;
import com.insanelyinsane.waifushogi.events.TurnEndEvent;
import com.insanelyinsane.waifushogi.listeners.TurnEndListener;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class AIControllerComponent extends GameComponent implements TurnEndListener
{
    private final Team PLAYER_TEAM = Team.RED;
    
    private AIOpponent _ai;
    private boolean _active;
    
    
    public AIControllerComponent(AIOpponent ai)
    {
        super(GameComponentType.AI_CONTROLLER);
        
        _ai = ai;
        _active = false;
    }

    
    @Override
    public void update(float deltaTime)
    {
        if (_active)
        {
            _ai.think();
            _ai.execute();
            
            _active = false;
        }
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        
    }
    
    
    @Override
    public void onTurnEnd(TurnEndEvent e)
    {
        // Start thinking of move to execute after the player moves
        if (e.getTeam() == PLAYER_TEAM)
        {
            _active = true;
        }
    }
}
