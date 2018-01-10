/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.Sender;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import java.util.Stack;

/**
 *
 * @author alex
 */
public final class HandObject extends Actor
{
    private Hand _hand;
    private BitmapFont _font;
    
    private RequestHandler _handler;
    
    public HandObject(float x, float y, int w, int h, Hand hand, BitmapFont font, RequestHandler handler)
    {
        super();
        setBounds(x, y, w, h);
        _hand = hand;
        _font = font;
        
        // Proxy between touch input and event dispatching
        _handler = handler;
        
        // Touch event
        addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                int c = (int)(screenX / Board.CELL_WIDTH);
                
                if (c >= Piece.Type.SIZE) return false;
                
                
                Stack<Piece> st = _hand.getPiecesOfType(Piece.Type.values()[c]);
                if (st.empty()) { return false; }
                
                Piece target = st.peek();
                _handler.requestSelection(Sender.HAND, target, -1, -1);
                
                return true;
            }
        });
    }
    
    
    
    
    
    @Override
    public void draw(Batch batch, float a)
    {
        /////////////////////////////////////////////////
        // Draw quantity at corner of captured pieces
        final int xOffset = 4;
        Team team = _hand.getTeam();
        
        for (Stack<Piece> s  : getHand().getPieces().values())
        {
            if (!s.empty())
            {
                Piece p = s.peek();
                _font.draw(batch, s.size() + "", getX() + (p.getType().getIndex()) * Board.CELL_WIDTH, getY());
            }
        }
    }
    
    
     /**
     * Checks if the given coords intersect this object.
     * @param x
     * @param y
     * @return boolean
     */
    public boolean containsPoint(float x, float y)
    {
        return (x >= getX()) && (y >= getY()) && (x <= (getX() + getWidth()) && y <= (getY() + getHeight()));
    }
    
    
    public Hand getHand() { return _hand; }
}
