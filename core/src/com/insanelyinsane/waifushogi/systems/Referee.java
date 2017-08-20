/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.ReplaceEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 *
 * @author alex
 */
public class Referee implements TouchListener
{
    private enum Containers { BOARD, HAND };
    
    // Board and Hands
    private final Board _board;
    private final Hand _redHand;
    private final Hand _blueHand;
    
    // Selection
    private Team _currentTeam;
    private Hand _currentHand;
    private Piece _selectedPiece;
    private int _selectedRow;
    private int _selectedCol;
    private boolean[][] _validMoves;
    private boolean[][] _validReplacements;
    private boolean _shouldReplace;
    
    
    public Referee(Board board, Hand redHand, Hand blueHand)
    {
        _board = board;
        _redHand = redHand;
        _blueHand = blueHand;
        
        // Red goes first
        _currentTeam = Team.RED;
        _currentHand = _redHand;
        _shouldReplace = false;
    }
    
    
    public SelectionEvent selectPieceOnBoard(Piece target, int r, int c)
    {
        if (target.getTeam() == _currentTeam)
        {
            _selectedPiece = target;
            _selectedRow = r;
            _selectedCol = c;
            _validMoves = target.getValidMoves(_board.getPieces(), r, c);
            _shouldReplace = false;

            return new SelectionEvent(_validMoves, _selectedPiece, true);
        }
        
        return null;
    }
    
    
    public SelectionEvent selectPieceInHand(Piece target)
    {
        if (target.getTeam() == _currentTeam)
        {
            _selectedPiece = target;
            _validReplacements = target.getValidReplacements(_board.getPieces());
            _shouldReplace = true;
            
            return new SelectionEvent(_validReplacements, _selectedPiece, true);
        }
        
        return null;
    }
    
    
    public MoveEvent movePieceTo(int r, int c)
    {
        boolean promo = false;
        
        MoveEvent e = null;
        
        if (_selectedPiece != null && !_shouldReplace)
        {
            if (_validMoves[r][c])
            {
                e = new MoveEvent(_selectedPiece, _selectedRow, _selectedCol, r, c, promo);
            }
        }
        
        return e;
    }
    
    
    public ReplaceEvent replacePieceTo(int r, int c)
    {
        ReplaceEvent e = null;
        
        if (_selectedPiece != null && _shouldReplace)
        {
            if (_validReplacements[r][c])
            {
                e = new ReplaceEvent(_selectedPiece, r, c);
            }
        }
        
        return e;
    }
    
    
    public Piece capturePieceAt(int r, int c)
    {
        Piece target = _board.getPieceAt(r, c);
        if (target != null)
        {
            if (target.getTeam() != _currentTeam) return target;
        }
        
        return null;
    }
    
    
    public void finishTurn()
    {
        // Reset the selection
        _selectedPiece = null;
        _selectedRow = -1;
        _selectedCol = -1;
        _validMoves = null;
        _validReplacements = null;
        _shouldReplace = false;


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
        
        System.out.println("TUrn complete");
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        
        //////////////////////////////////////////
        // If the board is touched
//        //////////////////////////////////////////
//        if (_board.getSprite().getBoundingRectangle().contains(e.getX(), e.getY()))
        {
            // The target is the piece in the row/col touched
//            int r = (int)(e.getY() - _board.getY()) / Board.CELL_HEIGHT;
//            int c = (int)(e.getX() - _board.getX()) / Board.CELL_WIDTH;
//            Piece target = _board.getBoard().getPieceAt(r, c);
            
            // If there is currently no piece selected
//            if (_selectedPiece == null)
//            {
//                // Select the piece if its on the team of the player whose turn it is
//                selectBoardPiece(target, r, c);
//            }
            
//            // If a piece is selected
//            else
//            {
//                // And the piece is on the board
//                if (!_shouldReplace)
//                {
//                    // Check if the move is valid
//                    if (_validMoves[r][c])
//                    {
//                        // And tell the capture listeners if a piece was captured
//                        if (target != null)
//                        {
//                            if (target.getTeam() != _currentTeam)
//                            {
//                                _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(target)));
//                            }
//                        }
//                        
//                        // Move the piece to the target row/col
//                        movePieceTo(r, c);
//                    }
//                    
//                    // If the move is invalid, select the piece at the touched cell
//                    // if the piece is on the current player's team.
//                    else
//                    {
//                        selectBoardPiece(target, r, c);
//                    }
//                }
//                
//                // If the piece is in a player's hand
//                else
//                {
//                    // And the replacement is valid for this piece
//                    if (_validReplacements[r][c])
//                    {
//                        // Move the piece from the player's hand to this row/col
//                        replacePieceTo(r, c);
//                    }
//                    else
//                    {
//                        selectBoardPiece(target, r, c);
//                    }
//                }
//            }
//            
//        }
//        
//        
//        //////////////////////////////////////////////////////////////
//        // If red hand is touched   (hand on right with red pieces)
//        //////////////////////////////////////////////////////////////
//        if (_redHand.containsPoint(e.getX(), e.getY()))
//        {
//            // Get the piece at the top of the captured piece stack given the type
//            int r = (int)(e.getY() - _redHand.getY()) / Board.CELL_HEIGHT;
//            if (r < 0 || r > Piece.Type.SIZE) return;
//            
//            selectHandPiece(Piece.Type.values()[r]);
//        }
//        
//        
//        
//        //////////////////////////////////////////////////////////////
//        // If blue hand is touched  (hand on left with blue pieces)
//        //////////////////////////////////////////////////////////////
//        if (_blueHand.containsPoint(e.getX(), e.getY()))
//        {
//            int r = (Piece.Type.SIZE - 1) - (int)(e.getY() - _blueHand.getY()) / Board.CELL_HEIGHT;
//            if (r < 0 || r > Piece.Type.SIZE) return;
//            
//            selectHandPiece(Piece.Type.values()[r]);
//        }
//        
    }
    }
        
        
    public Board getBoard() { return _board; }
    
    public Hand getRedHand() { return _redHand; }
    
    public Hand getBlueHand() { return _blueHand; }
}
    
    
    /**
     * Helper method to select a piece on the board and store its valid moves in the validMoves array.
     * Informs selection listeners that the piece was selected.
     * @param piece
     * @param r
     * @param c 
     */
//    private void selectBoardPiece(Piece piece, int r, int c)
//    {
//        if (piece == null)
//        {
//            Gdx.app.debug("Warning", "board piece selected at (" + r + ", " + c + ") was null");
//            return;
//        }
//        else
//        {
//            if (piece.getTeam() != _currentTeam)
//            {
//                Gdx.app.debug("Warning", "selected board piece not on current team " + _currentTeam.toString());
//                return;
//            }
//        }
//        
//        // Set selections
//        _selectedPiece = piece;
//        _selectedRow = r;
//        _selectedCol = c;
//        
//        // Retrieve valid moves
//        _validMoves = piece.getValidMoves(_board.getBoard().getPieces(), r, c);
//
//        // Dispatch SelectionEvent
//        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validMoves, _selectedPiece, true)));
//        
//        _shouldReplace = false;
//    }
//    
//    
//    /**
//     * Helper method to select a piece in the current player's hand.
//     * Stores valid replacements in the validReplacements array, and informs Selection listeners of the piece's selection.
//     * @param type 
//     */
//    private void selectHandPiece(Piece.Type type)
//    {
//        // Peek at the stack to select piece from hand
//        // If stack is empty, return to cancel touch operation
//        Stack<Piece> stack = _currentHand.getHand().getPiecesOfType(type);
//        if (stack.empty()) { return; }
//
//        Piece piece = stack.peek();
//        
//        _selectedPiece = piece;
//        
//        _validReplacements = piece.getValidReplacements(_board.getBoard().getPieces());
//        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(_validReplacements, _selectedPiece, true)));
//        
//        _shouldReplace = true;
//    }
//    
//    
//    /**
//     * Inform Move listeners of the moved piece and where on the board it moved from and to.
//     * @param r
//     * @param c 
//     */
//    private void movePieceTo(int r, int c)
//    {
//        // Move the selected piece to the new cell
//        
//        // FIX LATER (shouldn't always be true)
//        _moveListeners.forEach(l -> l.onWaifuMoved(new MoveEvent(_selectedPiece, _selectedRow, _selectedCol, r, c, false)));
//
//        Gdx.app.debug("Move", _selectedPiece.getType().toString() + " moved from (" + _selectedRow + ", " + _selectedCol + ") to (" + r + ", " + c + ")");
//        
//        finishTurn();
//    }
//    
//    
//    /**
//     * Inform Replace listeners of the replaced piece and where it was placed on the board.
//     * @param r
//     * @param c 
//     */
//    private void replacePieceTo(int r, int c)
//    {
//        _replaceListeners.forEach(l -> l.onWaifuReplaced(new ReplaceEvent(_selectedPiece, r, c)));
//        
//        Gdx.app.debug("Replace", _selectedPiece.getType().toString() + " replaced from " + _currentTeam.toString() + "'s hand to (" + r + ", " + c + ")");
//        
//        finishTurn();
//    }
//    
//    
//    /**
//     * Tell Selection listeners the piece was deselected, switch teams, and set shouldReplace flag to false.
//     */
//    
