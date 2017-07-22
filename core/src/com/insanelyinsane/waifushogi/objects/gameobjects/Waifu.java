/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author alex
 */
public class Waifu extends GameObject implements MoveListener, CaptureListener
{   
    // Constants
    private final float BOARD_X;
    private final float BOARD_Y;
    private final float RED_HAND_X;
    private final float RED_HAND_Y;
    private final float BLUE_HAND_X;
    private final float BLUE_HAND_Y;
    
    
    // Piece representing logical unit
    private Piece _piece;
    
    public Waifu(Texture tex, float x, float y, Piece piece, BoardObject board, HandObject red, HandObject blue)
    {
        super(tex, x, y);
        _piece = piece;
        
        BOARD_X = board.getX();
        BOARD_Y = board.getY();
        RED_HAND_X = red.getX();
        RED_HAND_Y = red.getY();
        BLUE_HAND_X = blue.getX();
        BLUE_HAND_Y = blue.getY();
    }
    
    
    /**
     * Runs in the game loop. Update the properties and systems of this waifu.
     * @param delta 
     */
    public void update(float delta)
    {
        // NOTHING SPECIAL!!
    }
    
    
    @Override
    public void onWaifuMoved(MoveEvent e)
    {
        if (e.getPiece().equals(getPiece()))
        {
            setX(BOARD_X+ e.toCol() * Board.CELL_WIDTH);
            setY(BOARD_Y + e.toRow() * Board.CELL_HEIGHT);
        }
    }
    
    
    @Override
    public void onWaifuCaptured(CaptureEvent e)
    {
        Piece p = e.getPiece();
        
        if (p.equals(getPiece()))
        {
            float offset = p.getType().getValue() * Board.CELL_HEIGHT;
            float x = p.getTeam() == Team.BLUE ? RED_HAND_X : BLUE_HAND_X;//HandObject.X_POS : Gdx.graphics.getWidth() - HandObject.X_POS;
            float y = p.getTeam() == Team.BLUE ? RED_HAND_Y + offset : BLUE_HAND_Y - offset;//: (Gdx.graphics.getHeight() - HandObject.START_Y_POS) - p.getType().ordinal() * Board.CELL_HEIGHT;
            
            setX(x);
            setY(y);
            p.setCaptured(true);
            p.setTeam(p.getTeam() == Team.RED ? Team.BLUE : Team.RED);
        }
    }
    
    
    public Piece getPiece() { return _piece; }
}
