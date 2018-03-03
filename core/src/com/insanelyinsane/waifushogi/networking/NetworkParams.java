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
    private String _hostIP;
    private boolean _isHost;
    private Team _team;
    //private Turn _lastTurn;
    
    public String getHostIP() { return _hostIP; }
    public boolean isHost() { return _isHost; }
    public Team getTeam() { return _team; }
    
    public void setTeam(Team t) { _team = t; }
    
    
    public NetworkParams(String hostIP, boolean isHost, Team team)
    {
        _hostIP = hostIP;
        _isHost = isHost;
        _team = team;
    }
}
