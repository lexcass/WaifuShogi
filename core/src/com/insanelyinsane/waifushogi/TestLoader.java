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
import com.insanelyinsane.waifushogi.screens.PlayScreen;

/**
 *
 * @author A Wild Popo Appeared
 */
public class TestLoader 
{
    private final PlayScreen _screen;
    
    public TestLoader(PlayScreen screen)
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
    
    
    public void loadTest(String test)
    {
        if (test.equalsIgnoreCase("pawn"))
        {
            loadPawnTest();
        }
        else if (test.equalsIgnoreCase("rook"))
        {
            loadRookTest();
        }
        else if (test.equalsIgnoreCase("bishop"))
        {
            loadBishopTest();
        }
        else if (test.equalsIgnoreCase("knight"))
        {
            loadKnightTest();
        }
        else if (test.equalsIgnoreCase("silver"))
        {
            loadSilverTest();
        }
        else if (test.equalsIgnoreCase("lance"))
        {
            loadLanceTest();
        }
        else if (test.equalsIgnoreCase("gold"))
        {
            loadGoldTest();
        }
        else if (test.equalsIgnoreCase("jade"))
        {
            loadJadeTest();
        }
    }
    
    
    private void loadPawnTest()
    {
        addPawn(Team.RED, 1, 1);
        addPawn(Team.RED, 2, 2);
        addPawn(Team.BLUE, 2, 1);
        addPawn(Team.BLUE, 1, 2);
        addPawn(Team.RED, 5, 5).promote();
        addPawn(Team.BLUE, 6, 6).promote();
    }
    
    private void loadLanceTest()
    {
        addLance(Team.RED, 0, 0);
        addLance(Team.BLUE, 8, 8);
        addLance(Team.RED, 1, 3);
        addLance(Team.RED, 2, 5);
        addPawn(Team.BLUE, 3, 5);
        addLance(Team.RED, 6, 4).promote();
        addLance(Team.BLUE, 7, 4).promote();
    }
    
    private void loadRookTest()
    {
        addRook(Team.RED, 0, 0);
        addRook(Team.RED, 1, 0);
        addRook(Team.RED, 0, 1);
        
        addRook(Team.RED, 0, 8).promote();
        addRook(Team.RED, 1, 8);
        addRook(Team.RED, 0, 7);
        
        addRook(Team.BLUE, 5, 8);
        addRook(Team.BLUE, 5, 0).promote();
    }
    
    private void loadBishopTest()
    {
        addBishop(Team.RED, 0, 0);
        addBishop(Team.RED, 1, 1);
        
        addBishop(Team.RED, 0, 8).promote();
        addBishop(Team.RED, 1, 7);
        
        addBishop(Team.BLUE, 4, 4).promote();
        addBishop(Team.BLUE, 3, 5);
    }
    
    private void loadKnightTest()
    {
        addKnight(Team.RED, 0, 0);
        addKnight(Team.RED, 2, 1);
        
        addKnight(Team.RED, 5, 5);
        addKnight(Team.BLUE, 7, 4);
        addPawn(Team.BLUE, 6, 5);
        addPawn(Team.RED, 7, 5);
        
        addKnight(Team.BLUE, 8, 7);
        addRook(Team.BLUE, 8, 8);
    }
    
    private void loadSilverTest()
    {
        addSilver(Team.RED, 1, 1);
        addPawn(Team.RED, 0, 0);
        addPawn(Team.RED, 0, 2);
        addPawn(Team.RED, 2, 0);
        addPawn(Team.RED, 2, 1);
        addPawn(Team.RED, 2, 2);
        
        addSilver(Team.BLUE, 5, 5).promote();
        addSilver(Team.BLUE, 7, 8);
        addSilver(Team.BLUE, 8, 1);
    }
    
    private void loadGoldTest()
    {
        addGold(Team.RED, 2, 2);
        addGold(Team.BLUE, 3, 3);
        addGold(Team.BLUE, 8, 8).promote();
    }
    
    private void loadJadeTest()
    {
        addJade(Team.RED, 0, 0);
        addPawn(Team.RED, 1, 0);
        addPawn(Team.RED, 0, 1);
        addPawn(Team.RED, 1, 1);
        
        addJade(Team.RED, 5, 5);
        addJade(Team.BLUE, 7, 8);
        addJade(Team.BLUE, 6, 3).promote();
    }
}
