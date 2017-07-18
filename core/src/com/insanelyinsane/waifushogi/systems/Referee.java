/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

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
    private List<GameObject<Cell>> _cellObjects;
    
    // Listeners
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    
    
    public Referee(GameObject<Board> board)
    {
        _board = board;
        _cellObjects = new LinkedList<>();
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        
        _moveListeners.add(_board.getObject());
        
        // Get list of board's cells as cell objects
        Cell[][] boardCells = _board.getObject().getCells();
        for (int r = 0; r < boardCells.length; r++)
        {
            for (int c = 0; c < boardCells[r].length; c++)
            {
                _cellObjects.add(new GameObject<>(null, boardCells[r][c], Cell.WIDTH * c, Cell.HEIGHT * r));
            }
        }
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        // Board touched
        if (_board.containsPoint(e.getX(), e.getY()))
        {
            // Get piece touched on board
            int r = (int)e.getY() / Cell.HEIGHT;
            int c = (int)e.getX() / Cell.WIDTH;
            
            Piece piece = _board.getObject().getCellAt(r, c).getPiece();
            
            // Retrieve vaild moves
            List<Cell> validMoves = piece.getValidMoves(_board.getObject().getCells(), r, c);
            
            if (validMoves.size() > 0)
            {
                // Dispatch SelectionEvent
                for (SelectionListener l : _selectionListeners)
                {
                    l.onWaifuSelected(new SelectionEvent(validMoves, true));
                }
            }
        }
    }
}
