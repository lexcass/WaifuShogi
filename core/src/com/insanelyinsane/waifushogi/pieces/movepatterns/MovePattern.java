/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces.movepatterns;

import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public abstract class MovePattern 
{
    private Team _team;
    
    public MovePattern(Team team)
    {
        _team = team;
    }
    
    
    public Team getTeam() { return _team; }
    public void setTeam(Team newTeam) { _team = newTeam; }
    
    
    public abstract boolean[][] getValidMoves(final Piece[][] cells, int r, int c);
    
    /**
     * A helper method for checking validity of a move. Returns true if the cell is empty
     * or contains a piece from the other team.
     * @param board
     * @param valid
     * @param r
     * @param c
     * 
     * @return boolean
     */
    public final boolean addIfValidMove(final Piece[][] board, boolean[][] valid, int r, int c)
    {
        if (Piece.inBounds(r, c))
        {
            Piece toCheck = board[r][c];

            if (toCheck != null)
            {
                valid[r][c] = (toCheck.getTeam() != _team);
            }
            else
            {
                valid[r][c] = true;
            }
            
            return valid[r][c];
        }
        
        return false;
    }
}
