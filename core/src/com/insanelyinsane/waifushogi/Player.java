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
    
    Type _type;
    public Type getType() { return _type; }
    
    Team _team;
    public Team getTeam() { return _team; }
    
    
    public Player(Type type)
    {
        _type = type;
        
        if (_type == Type.LOCAL_ONE)
        {
            _team = Team.RED;
        }
        else
        {
            _team = Team.BLUE;
        }
    }
}
