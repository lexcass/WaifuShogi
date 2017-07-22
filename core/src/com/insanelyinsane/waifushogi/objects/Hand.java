/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author alex
 */
public class Hand implements CaptureListener
{
    public HashMap<Piece.Type, Stack<Piece>> _pieceMap;
    public Team _team;
    
    
    /**
     * Create a new hand with stacks for each piece type.
     * @param team 
     */
    public Hand(Team team)
    {
        _team = team;
        
        _pieceMap = new HashMap<>();
        
        _pieceMap.put(Piece.Type.PAWN, new Stack<>());
        _pieceMap.put(Piece.Type.ROOK, new Stack<>());
        _pieceMap.put(Piece.Type.BISHOP, new Stack<>());
        _pieceMap.put(Piece.Type.KNIGHT, new Stack<>());
        _pieceMap.put(Piece.Type.LANCE, new Stack<>());
        _pieceMap.put(Piece.Type.SILVER, new Stack<>());
        _pieceMap.put(Piece.Type.GOLD, new Stack<>());
        _pieceMap.put(Piece.Type.JADE, new Stack<>());
    }
    
    
    /**
     * Add a piece to the hand.
     * @param piece 
     */
    public void addPiece(Piece piece)
    {
        _pieceMap.get(piece.getType()).push(piece);
    }
    
    
    /**
     * Remove and return a piece of the given type.
     * @param type
     * @return 
     */
    public Piece removePiece(Piece.Type type)
    {
        return _pieceMap.get(type).pop();
    }
    
    
    /**
     * Return the stack of pieces with the given type.
     * @param type
     * @return 
     */
    public Stack<Piece> getPiecesOfType(Piece.Type type)
    {
        return _pieceMap.get(type);
    }
    
    
    public Map<Piece.Type, Stack<Piece>> getPieces() { return _pieceMap; }
    
    
    
    @Override
    public void onWaifuCaptured(CaptureEvent e)
    {
        Piece piece = e.getPiece();
        
        if (piece.getTeam() != _team)
        {
            _pieceMap.get(piece.getType()).push(piece);
        }
    }
}
