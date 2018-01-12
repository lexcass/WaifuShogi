/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.TurnEndEvent;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.TurnEndListener;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class AIOpponent implements TurnEndListener, QuitListener
{
    private final Team PLAYER_TEAM = Team.RED;
    
    private RequestHandler _requestHandler;
    private GameState _currentGameState;
    private RuleBook _ruleBook;
    
    private boolean _active;
    public boolean isActive() { return _active; }
    
    
    /**
     * 
     * @param handler The RequestHandler to relay the AI's final move to the game's other systems (Board, Hand, etc.)
     * @param state The current state of the game including the Board and Hands.
     */
    public AIOpponent(RequestHandler handler, GameState state)
    {
        _requestHandler = handler;
        
        // Keep a reference to the current state (position) of the game Board.
        _currentGameState = state;
        _ruleBook = new RuleBook();
        
        _active = false;
    }
    
    
    @Override
    public void onTurnEnd(TurnEndEvent e)
    {
        // Execute move only after the player moves
        if (e.getTeam() == PLAYER_TEAM)
        {
            think();
            execute();
        }
    }
    
    
    @Override
    public void handleGameQuit()
    {
        
    }
    
    
    public void think()
    {
        _active = true;
        
        try
        {
            Thread.sleep(2000);
            Gdx.app.debug("AI", "finished thinking.");
        }
        catch(InterruptedException e){}
    }
    
    
    private GameState createMockGameState(GameState gs)
    {
        return new GameState(gs.getBoard(), gs.getRedHand(), gs.getBlueHand());
    }
    
    
    private void generateMoveTree()
    {
        // Create a Search Tree of a specified depth containing
        // every possible move alternating between AI and opponent
    }
    
    private void analyzeMoveTree()
    {
        // Recursively traverse Search Tree and evaluate each node visited
        // to determine worth of move and pick the best one
    }
    
    private void move()
    {
        // 
    }
    
    private void undoMove()
    {
        // 
    }
    
    private void evaluate()
    {
        // Use an evaluation function (maybe an Evaluator based off of difficulty)
        // to analyze worth of a move by the AI
    }
    
    
    /**
     * This method will get the result of the AI's analysis and execute
     * its final decision through the RequestHandler.
     */
    public void execute()
    {
        // End AI turn
        _active = false;
    }
}
