/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ai;

import com.insanelyinsane.waifushogi.pieces.Piece;

/**
 *
 * @author Alex Cassady
 */
public class Turn 
{
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
    
    private boolean _promoted;
    public boolean isPromoted() { return _promoted; }
    
    
    public Turn(int fromRow, int fromCol, int toRow, int toCol, Piece captured, boolean promoted)
    {
        _fromRow = fromRow;
        _fromCol = fromCol;
        _toRow = toRow;
        _toCol = toCol;
        _capturedPiece = captured;
        _promoted = promoted;
    }
}
