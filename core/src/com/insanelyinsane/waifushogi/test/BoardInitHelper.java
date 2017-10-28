/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.test;

import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Bishop;
import com.insanelyinsane.waifushogi.objects.pieces.GoldGeneral;
import com.insanelyinsane.waifushogi.objects.pieces.JadeGeneral;
import com.insanelyinsane.waifushogi.objects.pieces.Knight;
import com.insanelyinsane.waifushogi.objects.pieces.Lance;
import com.insanelyinsane.waifushogi.objects.pieces.Pawn;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Rook;
import com.insanelyinsane.waifushogi.objects.pieces.SilverGeneral;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author A Wild Popo Appeared
 */
public class BoardInitHelper 
{
    Board _board;
    
    public BoardInitHelper(Board b)
    {
        _board = b;
    }
    
    public void clearBoard() { _board.clear(); }
    
    public void removePieceAt(int r, int c) { _board.removePieceAt(r, c); }
    
    public Piece addPawn(Team team, int r, int c) 
    {
        Piece p = new Pawn(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addRook(Team team, int r, int c) 
    {
        Piece p = new Rook(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addBishop(Team team, int r, int c) 
    {
        Piece p = new Bishop(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addKnight(Team team, int r, int c) 
    {
        Piece p = new Knight(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addLance(Team team, int r, int c) 
    {
        Piece p = new Lance(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addSilver(Team team, int r, int c) 
    {
        Piece p = new SilverGeneral(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addGold(Team team, int r, int c) 
    {
        Piece p = new GoldGeneral(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
    
    public Piece addJade(Team team, int r, int c) 
    {
        Piece p = new JadeGeneral(team); 
        _board.addPiece(p, r, c);
        return p; 
    }
}
