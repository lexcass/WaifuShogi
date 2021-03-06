/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.GdxRuntimeException;
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
import com.insanelyinsane.waifushogi.actors.animation.Animator;
import com.insanelyinsane.waifushogi.listeners.DropListener;

/**
 *
 * @author Alex Cassady
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
    
    private final int WIDTH = 36;
    private final int HEIGHT = 36;
    
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
        
        setX(x + (Board.CELL_WIDTH / 2) - (WIDTH / 2));
        setY(y + (Board.CELL_HEIGHT / 2) - (HEIGHT / 2));
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
        
        
        _animator = new Animator(tex, WIDTH, HEIGHT);
        _animator.loadAnimationsFromFile(piece.getType().toString().toLowerCase());
        setAnimation("Idle");
    }
    
    
    /**
     * Runs in the game loop. Update the properties and systems of this waifu.
     * @param delta 
     */
    @Override
    public void act(float delta)
    {
        // Remove Waifu from stage if it has no piece to represent.
        if (_piece == null)
        {
            // Probably not best....
            //this.addAction(Actions.removeActor(this));
            
            // If the piece becomes null, crash the game. No piece should be null.
            throw new GdxRuntimeException("Waifu can't represent a null piece. Something went horribly wrong!");
        }
        
        _animator.update(delta);
        
//        _sprite.setX(getX());
//        _sprite.setY(getY());
    }
    
    
    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.draw(_animator.getFrame(), getX(), getY());
    }
    
    
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        if (e.getPiece() == _piece && !_selected)
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
        if (e.getPiece() == _piece)
        {
            setX((BOARD_X + e.toCol() * Board.CELL_WIDTH) + (Board.CELL_WIDTH / 2) - (WIDTH / 2));
            setY((BOARD_Y + e.toRow() * Board.CELL_HEIGHT) + (Board.CELL_HEIGHT / 2) - (HEIGHT / 2));
            
            _sprite.setX(getX());
            _sprite.setY(getY());
        }
    }
    
    
    @Override
    public void onWaifuCaptured(CaptureEvent e)
    {
        Piece p = e.getPiece();
        
        if (p == _piece)
        {
            float offsetX = p.getType().getIndex() * Board.CELL_WIDTH;
            float x = p.getTeam() == Team.BLUE ? RED_HAND_X + offsetX : BLUE_HAND_X + offsetX;
            float y = p.getTeam() == Team.BLUE ? RED_HAND_Y : BLUE_HAND_Y;
            
            setX(x);
            setY(y);
            _sprite.setX(getX());
            _sprite.setY(getY());
            p.setCaptured(true);
            p.setTeam(p.getTeam() == Team.RED ? Team.BLUE : Team.RED);
            _animPrefix = (p.getTeam() == Team.RED ? "up" : "down");
            
            demote();
        }
    }
    
    
    
    @Override
    public void onWaifuDropped(DropEvent e)
    {
        Piece p = e.getPiece();
        
        if (p == _piece)
        {
            setX((BOARD_X + e.toCol() * Board.CELL_WIDTH) + (Board.CELL_WIDTH / 2) - (WIDTH / 2));
            setY((BOARD_Y + e.toRow() * Board.CELL_HEIGHT) + (Board.CELL_HEIGHT / 2) - (HEIGHT / 2));
            
            _sprite.setX(getX());
            _sprite.setY(getY());
            
            setAnimation("Idle");
            p.setCaptured(false);
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
