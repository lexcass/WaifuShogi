/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.networking;

import com.insanelyinsane.waifushogi.ai.Turn;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class NetworkParams 
{
    private Team _team;
    private Turn _lastTurn;
    
    public Team getTeam() { return _team; }
    public Turn getLastTurn() { return _lastTurn; }
    
    
    public NetworkParams setTeam(Team team)
    {
        _team = team;
        return this;
    }
}
