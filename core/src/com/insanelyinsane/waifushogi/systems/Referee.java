/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.ReplaceEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.pieces.Pawn;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;

/**
 * The rule enforcer that keeps track of the state of the game. Operates on the
 * level of raw objects (Board and Hand) as opposed to GameObjects (BoardObject and HandObject).
 * The Referee generates events that will be sent to other components while keeping
 * track of the current state of the game (selected Piece, valid moves, etc.) It determines
 * what is valid and what is not.
 * @author alex
 */
public class Referee
{
    // Column that starts promotion zone for each team
    private final int PROMO_COL_RED = 6;
    private final int PROMO_COL_BLUE = 2;
    
    
    // Board and Hands
    private final Board _board;
    private final Hand _redHand;
    private final Hand _blueHand;
    
    public Board getBoard() { return _board; }
    
    public Hand getRedHand() { return _redHand; }
    
    public Hand getBlueHand() { return _blueHand; }
    
    
    // Selection
    private Team _currentTeam;
    private Hand _currentHand;
    private Piece _selectedPiece;
    private int _selectedRow;
    private int _selectedCol;
    private boolean[][] _validMoves;
    private boolean[][] _validReplacements;
    private boolean _shouldReplace;
    
    
    /**
     * Store a reference to the Board and Hands (Red and Blue). Initialize
     * the game state setting the current team to RED.
     * @param board
     * @param redHand
     * @param blueHand 
     */
    public Referee(Board board, Hand redHand, Hand blueHand)
    {
        _board = board;
        _redHand = redHand;
        _blueHand = blueHand;
        
        // Red goes first
        _currentTeam = Team.RED;
        _currentHand = _redHand;
    }
    
    
    /**
     * Select a Piece on the board given the piece and its row and column.
     * If the Piece is on the current team's team, change the game's state
     * to reflect the new selection. Returns a generated SelectionEvent to reflect
     * the new selection that should be handled by the calling object.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Returns null if the selection is invalid. The calling object
     * should be able to handle this case.
     * @param target
     * @param r
     * @param c
     * @return 
     */
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
    
    
    /**
     * Selects a Piece in the current team's Hand given the Piece.
     * If the Piece is on the current team's team, change the game's state
     * to reflect the new selection. Generate and return a new SelectionEvent
     * to be handled by the calling object.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Returns null if the selection is invalid. The calling object
     * should be able to handle this case.
     * @param target
     * @return 
     */
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
    
    
    /**
     * Moves the currently selected Piece to the specified row and column if
     * the Piece is on the Board and the move is valid as determined by 
     * Referee::selectPieceOnBoard(). Generates and returns a MoveEvent to be
     * handled by the calling object.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Returns null if the move is invalid. The calling object
     * should be able to handle this case.
     * @param r
     * @param c
     * @return 
     */
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
    
    
    /**
     * Moves the currently selected Piece from the Hand to the specified row and column on the Board.
     * If the replacement (drop) is valid as determined by Referee::selectPieceInHand(), this method
     * will generate and return a ReplaceEvent to be handled by the calling object.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Returns null if the selection is invalid. The calling object
     * should be able to handle this case.
     * @param r
     * @param c
     * @return 
     */
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
    
    
    /**
     * Returns the Piece at the specified row and columns if it should be
     * captured and null otherwise. The calling object should be able to handle
     * null cases.
     * @param r
     * @param c
     * @return 
     */
    public Piece capturePieceAt(int r, int c)
    {
        Piece target = _board.getPieceAt(r, c);
        if (target != null)
        {
            if (target.getTeam() != _currentTeam) return target;
        }
        
        return null;
    }
    
    
    public Piece promotePieceAt(int r, int c)
    {
        if (_currentTeam == Team.RED && r < PROMO_COL_RED)
        {
            return null;
        }
        else if (_currentTeam == Team.BLUE && r > PROMO_COL_BLUE)
        {
            return null;
        }
        
        Piece p = _board.getPieceAt(r, c);
        
        
        // Jade and Gold Generals can't be promoted, so ignore them.
        if (p.getType() == Piece.Type.JADE || p.getType() == Piece.Type.GOLD)
        {
            return null;
        }
        
        if (p == null) return p;
        
        if (!p.isPromoted())
        {
            return p;
        }
        
        return null;
    }
    
    
    
    public boolean isPieceStuck(Piece p, int r, int c)
    {
        // Pawn, Lance, and Knight are special cases.
        // If they are in the last row (or second to last row for Knight),
        // they have no future moves and must promoted.
        if (p.getType() == Piece.Type.PAWN || p.getType() == Piece.Type.LANCE)
        {
            int lastRow = p.getTeam() == Team.RED ? Board.COLS - 1 : 0;
            return (r == lastRow);
        }
        else if (p.getType() == Piece.Type.KNIGHT)
        {
            if (p.getTeam() == Team.RED)
            {
                return r >= Board.COLS - 2;
            }
            else
            {
                return r <= 1;
            }
        }
        
        return false;
    }
    
    
    /**
     * End the current player's turn by resetting the game state's selection
     * and validity flags. Change the current team to the next team (RED or BLUE).
     */
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
        
        System.out.println("Turn complete");
    }
    
}