/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ai;

import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class Turn 
{
    public enum Type { MOVE, DROP };
    
    private Type _type;
    public Type getType() { return _type; }
    
    private int _fromRow;
    private int _fromCol;
    
    public int getFromRow() { return _fromRow; }
    public int getFromCol() { return _fromCol; }
    
    private int _toRow;
    private int _toCol;
    
    public int getToRow() { return _toRow; }
    public int getToCol() { return _toCol; }
    
    private Piece _capturedPiece;
    public Piece getCapturedPiece() { return _capturedPiece; }
    
    // For undoing promotions
    private boolean _capturedWasPromoted;
    
    /**
     * Returns a boolean indicating whether the captured pieces was promoted or not.
     * @return 
     */
    public boolean wasCapturedPromoted() { return _capturedWasPromoted; }
    
    private boolean _promoted;
    public boolean isPromoted() { return _promoted; }
    public Turn setPromoted(boolean promo) { _promoted = promo; return this; }
    
    private Team _team;
    public Team getTeam() { return _team; }
    
    
    public Turn(Type type, Team team, int fromRow, int fromCol, int toRow, int toCol, Piece captured, boolean capturedPromoted)
    {
        _type = type;
        _team = team;
        _fromRow = fromRow;
        _fromCol = fromCol;
        _toRow = toRow;
        _toCol = toCol;
        _capturedPiece = captured;
        _capturedWasPromoted = capturedPromoted;
    }
    
    
    public Turn(Turn other)
    {
        _type = other._type;
        _team = other._team;
        _fromRow = other._fromRow;
        _fromCol = other._fromCol;
        _toRow = other._toRow;
        _toCol = other._toCol;
        _capturedPiece = other._capturedPiece;
        _capturedWasPromoted = other._capturedWasPromoted;
    }
}
