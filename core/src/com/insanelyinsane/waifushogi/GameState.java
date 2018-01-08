package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;

/**
 * Keeps track of the state of the game in progress. This will be used for
 * AI evaluation as well as network multiplayer.
 * 
 * @author Alex Cassady
 */
public class GameState 
{
    private final Hand _redHand;
    private final Hand _blueHand;
    private final Board _board;
    
    
    public GameState(final Board board, final Hand red, final Hand blue)
    {
        _redHand = red;
        _blueHand = blue;
        _board = board;
    }
    
    
    public Hand getRedHand() { return _redHand; }
    public Hand getBlueHand() { return _blueHand; }
    public Board getBoard() { return _board; }
}
