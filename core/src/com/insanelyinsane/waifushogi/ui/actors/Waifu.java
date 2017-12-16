/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.PromotionListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Animator;
import com.insanelyinsane.waifushogi.listeners.DropListener;

/**
 *
 * @author alex
 */
public class Waifu extends Actor implements MoveListener, CaptureListener, SelectionListener, DropListener, PromotionListener
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
    
    // Sprite representing visuals
    private Sprite _sprite;
    
    // Piece representing logical unit
    private Piece _piece;
    
    // Animation
    private Animator _animator;
    private String _animPrefix;
    private String _animSuffix;
    
    // Tint
    private Color _tint;
    
    // Flags
    private boolean _selected;
    
    
    public Sprite getSprite() { return _sprite; }
    
    public Waifu(Texture tex, float x, float y, Piece piece, BoardObject board, HandObject red, HandObject blue)
    {
        super();
        
        setX(x);
        setY(y);
        setSize(tex.getWidth(), tex.getHeight());
        setTouchable(Touchable.disabled);
        
        _sprite = new Sprite(tex);
        _sprite.setX(getX());
        _sprite.setY(getY());
        _piece = piece;
        _selected = false;
        
        BOARD_X = board.getX();
        BOARD_Y = board.getY();
        RED_HAND_X = red.getX();
        RED_HAND_Y = red.getY();
        BLUE_HAND_X = blue.getX();
        BLUE_HAND_Y = blue.getY();
        
        _tint = (_piece.getTeam() == Team.RED ? RED_TINT : BLUE_TINT);
        _animPrefix = (_piece.getTeam() == Team.RED ? "up" : "down");
        _animSuffix = "";
        
        
        _animator = new Animator(tex, 36, 36);
        _animator.loadFromFile(piece.getType().toString().toLowerCase());
        setAnimation("Idle");
    }
    
    
    /**
     * Runs in the game loop. Update the properties and systems of this waifu.
     * @param delta 
     */
    @Override
    public void act(float delta)
    {
        _animator.update(delta);
        
        _sprite.setX(getX());
        _sprite.setY(getY());
    }
    
    
    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.draw(_animator.getFrame(), getX(), getY());
    }
    
    
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        if (e.getPiece().equals(getPiece()) && !_selected)
        {
            _selected = e.isSelected();
            setAnimation("Selected");
        }
        else
        {
            _selected = false;
            setAnimation("Idle");
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
            _animPrefix = (p.getTeam() == Team.RED ? "up" : "down");
            
            // Demote piece
            demote();
        }
    }
    
    
    
    @Override
    public void onWaifuDropped(DropEvent e)
    {
        Piece p = e.getPiece();
        
        if (p.equals(getPiece()))
        {
            setX(BOARD_X + e.toCol() * Board.CELL_WIDTH);
            setY(BOARD_Y + e.toRow() * Board.CELL_HEIGHT);
            
            setAnimation("Idle");
        }
    }
    
    
    @Override
    public void onWaifuPromoted(Piece p)
    {
        if (p == getPiece())
        {
            promote();
        }
    }
    
    
    public Piece getPiece() { return _piece; }
    
    
    public void promote() 
    {
        _animSuffix = "Promoted";
        _piece.promote();
        setAnimation("Idle");
    }
    
    
    public void demote()
    {
        _animSuffix = "";
        _piece.demote();
        setAnimation("Idle");
    }
    
    /**
     * Convenience method to set animation based on state.
     * @param anim 
     */
    private void setAnimation(String anim)
    {
        _animator.setAnimation(_animPrefix + anim + _animSuffix);
    }
}
