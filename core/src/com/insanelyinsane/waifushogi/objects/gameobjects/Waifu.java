/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.ReplaceEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.ReplaceListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author alex
 */
public class Waifu extends GameObject implements MoveListener, CaptureListener, SelectionListener, ReplaceListener
{   
    // Constants
    private final float BOARD_X;
    private final float BOARD_Y;
    private final float RED_HAND_X;
    private final float RED_HAND_Y;
    private final float BLUE_HAND_X;
    private final float BLUE_HAND_Y;
    
    final Color RED_TINT = new Color(1.0f, 0.8f, 0.8f, 1.0f);
    final Color BLUE_TINT = new Color(0.8f, 0.8f, 1.0f, 1.0f);
    //private final Color SELECTION_TINT = new Color(0.9f, 1.0f, 0.9f, 1.0f);
    
    
    private final ShapeRenderer _shapeRend = new ShapeRenderer();
    
    // Piece representing logical unit
    private Piece _piece;
    
    
    private boolean _selected;
    
    
    
    public Waifu(Texture tex, float x, float y, Piece piece, BoardObject board, HandObject red, HandObject blue)
    {
        super(tex, x, y);
        _piece = piece;
        _selected = false;
        
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
        // NOTHING SPECIAL YET!!
    }
    
    
    @Override
    public void draw(SpriteBatch batch)
    {
//        if (_selected)
//        {
//            _shapeRend.begin(ShapeType.Line);
//            _shapeRend.setColor(Color.YELLOW);
//            _shapeRend.rect(getX(), getY(), Board.CELL_WIDTH, Board.CELL_HEIGHT);
//            _shapeRend.end();
//        }
//        else
//        {
            batch.setColor(getPiece().getTeam() == Team.RED ? RED_TINT : BLUE_TINT);
        //}
        
        batch.draw(getTexture(), getX(), getY());
        batch.setColor(Color.WHITE);
    }
    
    
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        if (e.getPiece().equals(getPiece()))
        {
            _selected = e.isSelected();
        }
    }
    
    
    @Override
    public void onWaifuMoved(MoveEvent e)
    {
        if (e.getPiece().equals(getPiece()))
        {
            setX(BOARD_X + e.toCol() * Board.CELL_WIDTH);
            setY(BOARD_Y + e.toRow() * Board.CELL_HEIGHT);
        }
    }
    
    
    @Override
    public void onWaifuCaptured(CaptureEvent e)
    {
        Piece p = e.getPiece();
        
        if (p.equals(getPiece()))
        {
            float offset = p.getType().getIndex() * Board.CELL_HEIGHT;
            float x = p.getTeam() == Team.BLUE ? RED_HAND_X : BLUE_HAND_X;
            float y = p.getTeam() == Team.BLUE ? RED_HAND_Y + offset : Gdx.graphics.getHeight() - BLUE_HAND_Y - offset - Board.CELL_HEIGHT;
            
            setX(x);
            setY(y);
            p.setCaptured(true);
            p.setTeam(p.getTeam() == Team.RED ? Team.BLUE : Team.RED);
        }
    }
    
    
    
    @Override
    public void onWaifuReplaced(ReplaceEvent e)
    {
        Piece p = e.getPiece();
        
        if (p.equals(getPiece()))
        {
            setX(BOARD_X + e.toCol() * Board.CELL_WIDTH);
            setY(BOARD_Y + e.toRow() * Board.CELL_HEIGHT);
        }
    }
    
    
    public Piece getPiece() { return _piece; }
}
