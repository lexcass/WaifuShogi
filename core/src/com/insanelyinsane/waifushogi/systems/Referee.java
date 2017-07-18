/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.GameObject;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alex
 */
public class Referee implements TouchListener
{
    // Board and Hands
    private final GameObject<Board> _board;
    
    // Listeners
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    
    
    public Referee(GameObject<Board> board, Highlighter h)
    {
        _board = board;
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        
        _moveListeners.add(_board.getObject());
        _selectionListeners.add(h);
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        // Board touched
        if (_board.containsPoint(e.getX(), e.getY()))
        {
            // Get piece touched on board
            int r = (int)(e.getY() - _board.getY()) / Cell.HEIGHT;
            int c = (int)(e.getX() - _board.getX()) / Cell.WIDTH;
            
            Piece piece = _board.getObject().getCellAt(r, c).getPiece();
            
            if (piece != null)
            {
                // Retrieve valid moves
                Cell[][] validMoves = piece.getValidMoves(_board.getObject().getCells(), r, c);

                // Dispatch SelectionEvent
                for (SelectionListener l : _selectionListeners)
                {
                    l.onWaifuSelected(new SelectionEvent(validMoves, true));
                }
            }
            else
            {
                Gdx.app.debug("Piece", "is null at " + r + ", " + c + ".");
                Gdx.app.debug("Touch", "at " + e.getX() + ", " + e.getY() + ".");
            }
        }
    }
}
