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
import com.insanelyinsane.waifushogi.objects.pieces.Team;
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
    private Team _currentTeam;
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
        
        // Red goes first
        _currentTeam = Team.RED;
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
            
            // If no cell is selected
            if (_selectedCell == null)
            {
                _selectedCell = _board.getBoard().getCellAt(r, c);
                Piece piece = _selectedCell.getPiece();

                if (piece != null)
                {
                    if (piece.getTeam().equals(_currentTeam))
                    {
                        showValidMoves(piece, r, c);
                    }
                }
            }
            
            // If a cell is selected
            else
            {
                
                
                System.out.println(_selectedCell.getRow() + ", " + _selectedCell.getCol());
                
                
                // If the target cell has a piece on the same team, select that piece instead
                Cell target = _board.getBoard().getCellAt(r, c);
                
                if (target != null)
                {
                    // If the target is a valid move for the selected piece
                    if (target.equals(_validMoves[r][c]))
                    {
                        // Tell the capture listeners what piece was captured

                        // Move the selected piece to the new cell
                        _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent(_selectedCell.getPiece(), _selectedCell, target)));

                        // Reset the selection
                        _selectedCell = null;
                        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, false)));

                        // Switch to other player
                        //_currentTeam = _currentTeam.equals(Team.RED) ? Team.BLUE : Team.RED;
                    }
                    
                    // If the target is invalid, see if the target contains one of the player's pieces and select it
                    else if (target.getPiece() != null)
                    {
                        if (target.getPiece().getTeam().equals(_currentTeam))
                        {
                            _selectedCell = target;
                            showValidMoves(target.getPiece(), r, c);
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
