/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 * The rule enforcer that keeps track of the state of the game. Operates on the
 * level of raw objects (Board and Hand) as opposed to GameObjects (BoardObject and HandObject).
 * The Referee generates events that will be sent to other components while keeping
 * track of the current state of the game (selected Piece, valid moves, etc.) It determines
 * what is valid and what is not.
 * @author alex
 */







/*

Note: Potential CONFLICT! _currentPlayer/team may allow Player to select pieces from network opponent's hand.
*/









public class Referee
{
    // Column that starts promotion zone for each team
    private final int PROMO_COLUMN_RED = 6;
    private final int PROMO_COLUMN_BLUE = 2;
    
    
    RuleBook _ruleBook;
    
    
    // Board and Hands
    private final Board _board;
    private final Hand _redHand;
    private final Hand _blueHand;
    
    public Board getBoard() { return _board; }
    
    public Hand getRedHand() { return _redHand; }
    
    public Hand getBlueHand() { return _blueHand; }
    
    
    // Selection
    private Team _currentTeam;
    private Selection _selection;
    private boolean[][] _validMoves;
    private boolean[][] _validDrops;
    
    // Players
    private Player _redPlayer;
    private Player _bluePlayer;
    private Player _currentPlayer;
    
    
    /**
     * Store a reference to the Board and Hands (Red and Blue). Initialize
     * the game state setting the current team to RED.
     * @param board
     * @param redHand
     * @param blueHand 
     */
    public Referee(Player red, Player blue, Board board, Hand redHand, Hand blueHand)
    {
        _board = board;
        _redHand = redHand;
        _blueHand = blueHand;
        
        _redPlayer = red;
        _bluePlayer = blue;
        
        // Red goes first
        _currentPlayer = red;
        _currentTeam = red.getTeam();
        
        _selection = new Selection(null, -1, -1);
        _ruleBook = new RuleBook();
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
        if (_ruleBook.canSelectPieceOnBoard(target, r, c, _currentTeam)) //(target.getTeam() == _currentTeam && !target.isCaptured())
        {
            _selection.setPiece(target);
            _selection.setCell(r, c);
            _validMoves = target.getValidMoves(_board.getPieces(), r, c);

            return new SelectionEvent(_validMoves, target, true);
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
        if (_ruleBook.canSelectPieceInHand(target, _currentTeam)) //(target.getTeam() == _currentTeam && target.isCaptured())
        {
            _selection.setPiece(target);
            _validDrops = target.getValidDrops(_board.getPieces());
            
            return new SelectionEvent(_validDrops, target, true);
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
        Piece selectedPiece = _selection.getPiece();
        int selectedRow = _selection.getRow();
        int selectedCol = _selection.getCol();
        MoveEvent e = null;
        
        if (_ruleBook.canMovePieceTo(_selection, _validMoves, r, c))
        {
            e = new MoveEvent(selectedPiece, selectedRow, selectedCol, r, c, promo);
        }
        
        return e;
    }
    
    
    /**
     * Moves the currently selected Piece from the Hand to the specified row and column on the Board.
     * If the Drop (drop) is valid as determined by Referee::selectPieceInHand(), this method
     * will generate and return a DropEvent to be handled by the calling object.
 
        ///////////////////////////////////////////////////////////////////////
        Important: Returns null if the selection is invalid. The calling object
        should be able to handle this case.
     * @param r
     * @param c
     * @return 
     */
    public DropEvent dropPieceAt(int r, int c)
    {
        DropEvent e = null;
        
        Piece selectedPiece = _selection.getPiece();
        
        if (_ruleBook.canDropPieceAt(_selection, _validDrops, r, c))
        {
            e = new DropEvent(selectedPiece, r, c);
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
        
        if (_ruleBook.canCapturePiece(target, _currentTeam))
        {
            return target;
        }
            
        return null;
    }
    
    
    /**
     * Promote the piece that was just moved if legal. Returns the promoted piece or null
     * if unpromoted.
     * @param r
     * @param c
     * @return Piece or null if unpromoted.
     */
    public Piece promotePieceAt(int r, int c)
    {        
        return _ruleBook.canPromotePieceAt(_selection, r, _currentTeam);
    }
    
    
    
    public boolean isPieceStuck(Piece p, int r, int c)
    {
        return _ruleBook.isPieceStuck(p, r, c);
    }
    
    
    /**
     * End the current player's turn by resetting the game state's selection
     * and validity flags. Change the current team to the next team (RED or BLUE).
     * 
     * @return Team who just finished their turn.
     */
    public Team finishTurn()
    {
        // Reset the selection
        _selection.reset();
        _validMoves = null;
        _validDrops = null;
        
        Team whoFinished = _currentTeam;


        // Switch to other player
        
        _currentPlayer.setActing(false);
        
        if (_currentPlayer == _redPlayer)
        {
            _currentPlayer = _bluePlayer;
            _currentTeam = _bluePlayer.getTeam();
        }
        else if (_currentPlayer == _bluePlayer)
        {
            _currentPlayer = _redPlayer;
            _currentTeam = _redPlayer.getTeam();
        }
        
        _currentPlayer.setActing(true);
        
        return whoFinished;
    }
    
    
    public Player whoseTurn() { return _currentPlayer; }
    
    
    public Player getBluePlayer()
    {
        return _bluePlayer;
    }
}