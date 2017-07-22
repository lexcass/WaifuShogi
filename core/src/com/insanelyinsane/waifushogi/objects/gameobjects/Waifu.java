/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Waifu extends GameObject implements MoveListener, CaptureListener
{   
    // Piece representing logical unit
    private Piece _piece;
    
    public Waifu(Texture tex, float x, float y, Piece piece)
    {
        super(tex, x, y);
        _piece = piece;
    }
    
    
    /**
     * Runs in the game loop. Update the properties and systems of this waifu.
     * @param delta 
     */
    public void update(float delta)
    {
//        setX(_board.getX() + _cell.getCol() * Cell.WIDTH);
//        setY(_board.getY() + _cell.getRow() * Cell.HEIGHT);
    }
    
    
    @Override
    public void onWaifuMoved(MoveEvent e)
    {
        if (e.getPiece().equals(getPiece()))
        {
            setX(BoardObject.X_POS + e.toCol() * Board.CELL_WIDTH);
            setY(BoardObject.Y_POS + e.toRow() * Board.CELL_HEIGHT);
        }
    }
    
    
    @Override
    public void onWaifuCaptured(CaptureEvent e)
    {
        if (e.getPiece().equals(getPiece()))
        {
            setX(HandObject.X_POS);
            setY(HandObject.START_Y_POS - e.getPiece().getType().ordinal() * Board.CELL_HEIGHT);
            e.getPiece().setCaptured(true);
        }
    }
    
    
    public Piece getPiece() { return _piece; }
}
