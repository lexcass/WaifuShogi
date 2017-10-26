/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.insanelyinsane.waifushogi.RequestHandler;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
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
                int r;
                
                if (_hand.getTeam() == Team.RED)
                {
                    r = (int)(screenY / Board.CELL_HEIGHT);
                }
                else
                {
                    r = (Piece.Type.SIZE - 1) - (int)(screenY / Board.CELL_HEIGHT);
                }
                
                if (r >= Piece.Type.SIZE) return false;
                
                
                Stack<Piece> st = _hand.getPiecesOfType(Piece.Type.values()[r]);
                if (st.empty()) { return false; }
                
                Piece target = st.peek();
                _handler.requestSelection(RequestHandler.Sender.HAND, target, -1, -1);
                
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
                
                if (team == Team.RED)
                {
                    _font.draw(batch, s.size() + "", getX() + xOffset, getY() + (p.getType().getIndex() + 1) * Board.CELL_HEIGHT );
                }
                else
                {
                    _font.draw(batch, s.size() + "", getX() + xOffset, Gdx.graphics.getHeight() - getY() - p.getType().getIndex() * Board.CELL_HEIGHT);
                }
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
