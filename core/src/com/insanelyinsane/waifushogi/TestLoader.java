/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

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
import com.insanelyinsane.waifushogi.screens.TestScreen;

/**
 *
 * @author A Wild Popo Appeared
 */
public class TestLoader 
{
    private final TestScreen _screen;
    
    public TestLoader(TestScreen screen)
    {
        _screen = screen;
    }
    
    // Piece generators
    // Return piece to control promotion and demotion
    private Piece addPawn(Team team, int r, int c) { Piece p = new Pawn(team); _screen.addPiece(p, _screen.getAssets().get("textures/Pawn.png"), r, c); return p; }
    private Piece addRook(Team team, int r, int c) { Piece p = new Rook(team); _screen.addPiece(p, _screen.getAssets().get("textures/Rook.png"), r, c); return p; }
    private Piece addBishop(Team team, int r, int c) { Piece p = new Bishop(team); _screen.addPiece(p, _screen.getAssets().get("textures/Bishop.png"), r, c); return p; }
    private Piece addKnight(Team team, int r, int c) { Piece p = new Knight(team); _screen.addPiece(p, _screen.getAssets().get("textures/Knight.png"), r, c); return p; }
    private Piece addLance(Team team, int r, int c) { Piece p = new Lance(team); _screen.addPiece(p, _screen.getAssets().get("textures/Lance.png"), r, c); return p; }
    private Piece addSilver(Team team, int r, int c) { Piece p = new SilverGeneral(team); _screen.addPiece(p, _screen.getAssets().get("textures/SilverGeneral.png"), r, c); return p; }
    private Piece addGold(Team team, int r, int c) { Piece p = new GoldGeneral(team); _screen.addPiece(p, _screen.getAssets().get("textures/GoldGeneral.png"), r, c); return p; }
    private Piece addJade(Team team, int r, int c) { Piece p = new JadeGeneral(team); _screen.addPiece(p, _screen.getAssets().get("textures/JadeGeneral.png"), r, c); return p; }
    
    public void loadPawnTest()
    {
        addPawn(Team.RED, 1, 1);
        addPawn(Team.BLUE, 2, 2);
    }
    
    public void loadLanceTest()
    {
        addLance(Team.BLUE, 3, 3);
    }
    
    public void loadRookTest()
    {
        
    }
    
    public void loadBishopTest()
    {
        
    }
    
    public void loadKnightTest()
    {
        
    }
    
    public void loadSilverTest()
    {
        
    }
    
    public void loadGoldTest()
    {
        
    }
    
    public void loadJadeTest()
    {
        
    }
}
