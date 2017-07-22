/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.gameobjects.BoardObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.HandObject;
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
    private final HandObject _redHand;
    private final HandObject _blueHand;
    
    // Selection
    private Team _currentTeam;
    private Piece _selectedPiece;
    private int _selectedRow;
    private int _selectedCol;
    private boolean[][] _validMoves; 
    
    // Listeners
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    private final List<CaptureListener> _captureListeners;
    
    
    public Referee(BoardObject board, Highlighter h, List<Waifu> waifus, HandObject redHand, HandObject blueHand)
    {
        _board = board;
        _redHand = redHand;
        _blueHand = blueHand;
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _captureListeners = new LinkedList<>();
        
        // Add move event listeners
        _moveListeners.add(_board.getBoard());
        waifus.forEach(w -> _moveListeners.add(w));
        
        // Add selection event listeners
        _selectionListeners.add(h);
        
        // Add capture event listeners
        _captureListeners.add(_redHand.getHand());
        //_captureListeners.add(_redHand);
        waifus.forEach(w -> _captureListeners.add(w));
        
        // Red goes first
        _currentTeam = Team.RED;
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        // If the board is touched
        if (_board.containsPoint(e.getX(), e.getY()))
        {
            // The target is the piece in the row/col touched
            int r = (int)(e.getY() - _board.getY()) / Board.CELL_HEIGHT;
            int c = (int)(e.getX() - _board.getX()) / Board.CELL_WIDTH;
            Piece target = _board.getBoard().getPieceAt(r, c);
            
            // If there is currently no piece selected
            if (_selectedPiece == null)
            {
                // Select the piece if its on the team of the player whose turn it is
                if (target != null)
                {
                    if (target.getTeam() == _currentTeam)
                    {
                        validateMoves(target, r, c);
                    }
                }
            }
            
            // If a piece is selected
            else
            {
                // And the target row and col is a valid move
                if (_validMoves[r][c])
                {
                    // Tell the capture listeners what piece was captured
                    Piece captured = target;
                    if (captured != null)
                    {
                        if (captured.getTeam() != _currentTeam)
                        {
                            //captured.setCaptured(true);
                            _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(captured)));
                        }
                    }

                    // Move the selected piece to the new cell
                    _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent(_selectedPiece, _selectedRow, _selectedCol, r, c)));

                    // Reset the selection
                    _selectedPiece = null;
                    _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, false)));

                    // Switch to other player
                    //_currentTeam = _currentTeam.equals(Team.RED) ? Team.BLUE : Team.RED;
                }
                
                // If the target is invalid, see if the target contains one of the player's pieces and select it
                else if (target != null)
                {
                    if (target.getTeam().equals(_currentTeam))
                    {
                        validateMoves(target, r, c);
                    }
                }
            }
            
        }
    }
    
    
    public void validateMoves(Piece piece, int r, int c)
    {
        // Set selections
        _selectedPiece = piece;
        _selectedRow = r;
        _selectedCol = c;
        
        // Retrieve valid moves
        _validMoves = piece.getValidMoves(_board.getBoard().getPieces(), r, c);

        // Dispatch SelectionEvent
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validMoves, true)));
    }
}
