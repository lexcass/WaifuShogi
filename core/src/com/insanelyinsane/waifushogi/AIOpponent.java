/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;

/**
 *
 * @author Alex Cassady
 */
public class AIOpponent 
{
    private RequestHandler _requestHandler;
    private Referee _referee;
    private GameState _gameState;
    
    private boolean _thinking;
    public boolean isThinking() { return _thinking; }
    
    
    public AIOpponent(RequestHandler handler, Referee ref, GameState state)
    {
        _requestHandler = handler;
        _referee = ref;
        _gameState = state;
        
        _thinking = false;
    }
    
    public void think()
    {
        _thinking = true;
        
        while (_thinking)
        {
            _thinking = false;
        }
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
        if (_thinking)
        {
            _thinking = false;
        }
        
        // 2 second delay to test
        try
        {
            Thread.sleep(2000);
            Gdx.app.debug("AI", "Finished!");
            _thinking = false;
        }
        catch (InterruptedException e) {}
    }
}
