/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.ReplaceEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.ReplaceListener;
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
import java.util.Stack;

/**
 *
 * @author alex
 */
public class Referee implements TouchListener
{
    private enum Containers { BOARD, HAND };
    
    // Board and Hands
    private final BoardObject _board;
    private final HandObject _redHand;
    private final HandObject _blueHand;
    
    // Selection
    private Team _currentTeam;
    private HandObject _currentHand;
    private Piece _selectedPiece;
    private int _selectedRow;
    private int _selectedCol;
    private boolean[][] _validMoves;
    private boolean[][] _validReplacements;
    private boolean _shouldReplace;
    
    // Listeners
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    private final List<ReplaceListener> _replaceListeners;
    private final List<CaptureListener> _captureListeners;
    
    
    public Referee(BoardObject board, Highlighter h, List<Waifu> waifus, HandObject redHand, HandObject blueHand)
    {
        _board = board;
        _redHand = redHand;
        _blueHand = blueHand;
        
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _replaceListeners = new LinkedList<>();
        _captureListeners = new LinkedList<>();
        
        // Add move event listeners
        _moveListeners.add(_board.getBoard());
        waifus.forEach(w -> _moveListeners.add(w));
        
        // Add replace event listeners
        _replaceListeners.add(_board.getBoard());
        _replaceListeners.add(_blueHand.getHand());
        _replaceListeners.add(_redHand.getHand());
        waifus.forEach(w -> _replaceListeners.add(w));
        
        // Add selection event listeners
        _selectionListeners.add(h);
        waifus.forEach(w -> _selectionListeners.add(w));
        
        // Add capture event listeners
        _captureListeners.add(_redHand.getHand());
        _captureListeners.add(_blueHand.getHand());
        waifus.forEach(w -> _captureListeners.add(w));
        
        // Red goes first
        _currentTeam = Team.RED;
        _currentHand = _redHand;
        _shouldReplace = false;
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        
        //////////////////////////////////////////
        // If the board is touched
        //////////////////////////////////////////
        if (_board.getSprite().getBoundingRectangle().contains(e.getX(), e.getY()))
        {
            // The target is the piece in the row/col touched
            int r = (int)(e.getY() - _board.getY()) / Board.CELL_HEIGHT;
            int c = (int)(e.getX() - _board.getX()) / Board.CELL_WIDTH;
            Piece target = _board.getBoard().getPieceAt(r, c);
            
            // If there is currently no piece selected
            if (_selectedPiece == null)
            {
                // Select the piece if its on the team of the player whose turn it is
                selectBoardPiece(target, r, c);
            }
            
            // If a piece is selected
            else
            {
                // And the piece is on the board
                if (!_shouldReplace)
                {
                    // Check if the move is valid
                    if (_validMoves[r][c])
                    {
                        // And tell the capture listeners if a piece was captured
                        if (target != null)
                        {
                            if (target.getTeam() != _currentTeam)
                            {
                                _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(target)));
                            }
                        }
                        
                        // Move the piece to the target row/col
                        movePieceTo(r, c);
                    }
                    
                    // If the move is invalid, select the piece at the touched cell
                    // if the piece is on the current player's team.
                    else
                    {
                        selectBoardPiece(target, r, c);
                    }
                }
                
                // If the piece is in a player's hand
                else
                {
                    // And the replacement is valid for this piece
                    if (_validReplacements[r][c])
                    {
                        // Move the piece from the player's hand to this row/col
                        replacePieceTo(r, c);
                    }
                    else
                    {
                        selectBoardPiece(target, r, c);
                    }
                }
            }
            
        }
        
        
        //////////////////////////////////////////////////////////////
        // If red hand is touched   (hand on right with red pieces)
        //////////////////////////////////////////////////////////////
        if (_redHand.containsPoint(e.getX(), e.getY()))
        {
            // Get the piece at the top of the captured piece stack given the type
            int r = (int)(e.getY() - _redHand.getY()) / Board.CELL_HEIGHT;
            if (r < 0 || r > Piece.Type.SIZE) return;
            
            selectHandPiece(Piece.Type.values()[r]);
        }
        
        
        
        //////////////////////////////////////////////////////////////
        // If blue hand is touched  (hand on left with blue pieces)
        //////////////////////////////////////////////////////////////
        if (_blueHand.containsPoint(e.getX(), e.getY()))
        {
            int r = (Piece.Type.SIZE - 1) - (int)(e.getY() - _blueHand.getY()) / Board.CELL_HEIGHT;
            if (r < 0 || r > Piece.Type.SIZE) return;
            
            selectHandPiece(Piece.Type.values()[r]);
        }
        
    }
    
    
    /**
     * Helper method to select a piece on the board and store its valid moves in the validMoves array.
     * Informs selection listeners that the piece was selected.
     * @param piece
     * @param r
     * @param c 
     */
    private void selectBoardPiece(Piece piece, int r, int c)
    {
        if (piece == null)
        {
            Gdx.app.debug("Warning", "board piece selected at (" + r + ", " + c + ") was null");
            return;
        }
        else
        {
            if (piece.getTeam() != _currentTeam)
            {
                Gdx.app.debug("Warning", "selected board piece not on current team " + _currentTeam.toString());
                return;
            }
        }
        
        // Set selections
        _selectedPiece = piece;
        _selectedRow = r;
        _selectedCol = c;
        
        // Retrieve valid moves
        _validMoves = piece.getValidMoves(_board.getBoard().getPieces(), r, c);

        // Dispatch SelectionEvent
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validMoves, _selectedPiece, true)));
        
        _shouldReplace = false;
    }
    
    
    /**
     * Helper method to select a piece in the current player's hand.
     * Stores valid replacements in the validReplacements array, and informs Selection listeners of the piece's selection.
     * @param type 
     */
    private void selectHandPiece(Piece.Type type)
    {
        // Peek at the stack to select piece from hand
        // If stack is empty, return to cancel touch operation
        Stack<Piece> stack = _currentHand.getHand().getPiecesOfType(type);
        if (stack.empty()) { return; }

        Piece piece = stack.peek();
        
        _selectedPiece = piece;
        
        _validReplacements = piece.getValidReplacements(_board.getBoard().getPieces());
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validReplacements, _selectedPiece, true)));
        
        _shouldReplace = true;
    }
    
    
    /**
     * Inform Move listeners of the moved piece and where on the board it moved from and to.
     * @param r
     * @param c 
     */
    private void movePieceTo(int r, int c)
    {
        // Move the selected piece to the new cell
        
        // FIX LATER (shouldn't always be true)
        _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent(_selectedPiece, _selectedRow, _selectedCol, r, c, false)));

        Gdx.app.debug("Move", _selectedPiece.getType().toString() + " moved from (" + _selectedRow + ", " + _selectedCol + ") to (" + r + ", " + c + ")");
        
        finishTurn();
    }
    
    
    /**
     * Inform Replace listeners of the replaced piece and where it was placed on the board.
     * @param r
     * @param c 
     */
    private void replacePieceTo(int r, int c)
    {
        _replaceListeners.forEach(l -> l.onWaifuReplaced(new ReplaceEvent(_selectedPiece, r, c)));
        
        Gdx.app.debug("Replace", _selectedPiece.getType().toString() + " replaced from " + _currentTeam.toString() + "'s hand to (" + r + ", " + c + ")");
        
        finishTurn();
    }
    
    
    /**
     * Tell Selection listeners the piece was deselected, switch teams, and set shouldReplace flag to false.
     */
    private void finishTurn()
    {
        // Reset the selection
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _selectedPiece, false)));
        _selectedPiece = null;


        // Switch to other player
        if (_currentTeam == Team.RED)
        {
            _currentTeam = Team.BLUE;
            _currentHand = _blueHand;
        }
        else
        {
            _currentTeam = Team.RED;
            _currentHand = _redHand;
        }
        
        _shouldReplace = false;
    }
}
