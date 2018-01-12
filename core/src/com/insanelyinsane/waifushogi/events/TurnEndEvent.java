/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class TurnEndEvent 
{
    Team _team;
    public Team getTeam() { return _team; }
    
    public TurnEndEvent(Team t)
    {
        _team = t;
    }
}
