/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.listeners.UpdatePositionListener;
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
    
    // Selection
    private int _selectedRow;
    private int _selectedCol;
    private Cell _selectedCell;
    
    // Listeners
    private final List<UpdatePositionListener> _updateListeners;
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    //private final List<CaptureListener> _captureListeners;
    
    
    public Referee(GameObject<Board> board, Highlighter h)
    {
        _board = board;
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _updateListeners = new LinkedList<>();
        
        _moveListeners.add(_board.getObject());
        _selectionListeners.add(h);
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        // Unhighlight everything
        for (SelectionListener l : _selectionListeners)
        {
            l.onWaifuSelected(new SelectionEvent(null, false));
        }
        
        // Board touched
        if (_board.containsPoint(e.getX(), e.getY()))
        {
            // Get piece touched on board
            int r = (int)(e.getY() - _board.getY()) / Cell.HEIGHT;
            int c = (int)(e.getX() - _board.getX()) / Cell.WIDTH;
            
            if (_selectedCell == null)
            {
                _selectedCell = _board.getObject().getCellAt(r, c);
                Piece piece = _selectedCell.getPiece();

                if (piece != null)
                {
                    // Retrieve valid moves
                    Cell[][] validMoves = piece.getValidMoves(_board.getObject().getCells(), r, c);

                    // Dispatch SelectionEvent
                    _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(validMoves, true)));
                }
            }
            else
            {
                // Tell the capture listeners what piece was captured
//                for (CaptureListener l : _captureListeners)
//                {
//                    
//                }
                
                // Move the selected piece to the new cell
                _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent
                                        (
                                            _selectedCell.getPiece(),
                                            _selectedCell.getRow(),
                                            _selectedCell.getCol(),
                                            r, c
                                        )));
                
                // Reset the selection
                _selectedCell = null;
            }
            
        }
    }
}
