/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.pieces.Team;

/**
 * An easier way to store information about what type of player is currently
 * acting. Used to allow/disallow actions by Players of certain types when it's
 * not their turn.
 * @author Alex Cassady
 */
public class Player 
{
    public enum Type { LOCAL_ONE, LOCAL_TWO, AI, NETWORK_TWO }
    
    private Type _type;
    
    /**
     * 
     * @return The type of the Player (specified by the Player.Type enum).
     */
    public Type getType() { return _type; }
    
    private Team _team;
    
    /**
     * 
     * @return Team the Player is on.
     */
    public Team getTeam() { return _team; }
    
    private boolean _acting;
    
    /**
     * 
     * @return Whether the player is currently doing their turn (true) or not (false).
     */
    public boolean isActing() { return _acting; }
    
    /**
     * Set the Player to acting (true) or not acting (false). This is synonymous to it being this
     * Player's turn.
     * @param b 
     */
    public void setActing(boolean b) { _acting = b; }
    
    
    public Player(Type type, boolean acting)
    {
        _type = type;
        _acting = acting;
        
        // RED player is always the local player
        if (_type == Type.LOCAL_ONE)
        {
            _team = Team.RED;
        }
        
        // Other player is BLUE
        else
        {
            _team = Team.BLUE;
        }
    }
}



