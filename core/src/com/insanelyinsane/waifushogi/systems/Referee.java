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
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.gameobjects.BoardObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.Waifu;
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
    private final BoardObject _board;
    
    // Selection
    private Cell _selectedCell;
    private Cell[][] _validMoves; 
    
    // Listeners
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    //private final List<CaptureListener> _captureListeners;
    
    
    public Referee(BoardObject board, Highlighter h, List<Waifu> waifus)
    {
        _board = board;
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        
        _moveListeners.add(_board.getBoard());
        waifus.forEach(w -> _moveListeners.add(w));
        
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
            
            if (_selectedCell == null)
            {
                _selectedCell = _board.getBoard().getCellAt(r, c);
                Piece piece = _selectedCell.getPiece();

                if (piece != null)
                {
                    showValidMoves(piece, r, c);
                }
            }
            else
            {
                // If the target cell has a piece on the same team, select that piece instead
                Cell target = _board.getBoard().getCellAt(r, c);
                
                if (target != null)
                {
                    if (target.getPiece() != null)
                    {
                        _selectedCell = target;
                        showValidMoves(target.getPiece(), r, c);
                    }
                    
                    else
                    {
                        if (target.equals(_validMoves[r][c]))
                        {
                            // Tell the capture listeners what piece was captured

                            // Move the selected piece to the new cell
                            _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent(_selectedCell.getPiece(), _selectedCell, target)));

                            // Reset the selection
                            _selectedCell = null;
                            _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, false)));
                        }
                    }
                }
                
            }
            
        }
    }
    
    
    public void showValidMoves(Piece piece, int r, int c)
    {
        // Retrieve valid moves
        _validMoves = piece.getValidMoves(_board.getBoard().getCells(), r, c);

        // Dispatch SelectionEvent
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validMoves, true)));
    }
}
