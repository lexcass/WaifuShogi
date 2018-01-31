/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ai;

import com.insanelyinsane.waifushogi.GameState;
import com.insanelyinsane.waifushogi.RuleBook;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.events.TurnEndEvent;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.TurnEndListener;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author Alex Cassady
 */
public class AIOpponent extends Thread implements TurnEndListener, QuitListener
{
    private final Team PLAYER_TEAM = Team.RED;
    private final int INIT_SEARCH_DEPTH = 1;
    private final int MAX_SEARCH_DEPTH = 4;
    
    private RequestHandler _requestHandler;
    private GameState _currentGameState;
    private RuleBook _ruleBook;
    private Selection _selection;
    
    private GameState _finalMove;
    
    private boolean _running;
    private boolean _active;
    public synchronized boolean isActive() { return _active; }
    public synchronized void setActive(boolean a) { _active = a; }
    
    
    /**
     * An AI-controlled opponent that will analyze the board and submit a move
 to be performed based on its evaluation.
     * @param handler The RequestHandler to relay the AI's final move to the game's other systems (Board, Hand, etc.)
     * @param state The current state of the game including the Board and Hands.
     */
    public AIOpponent(RequestHandler handler, GameState state)
    {
        _requestHandler = handler;
        
        // Keep a reference to the current state (position) of the game Board.
        _currentGameState = state;
        _ruleBook = new RuleBook();
        _selection = new Selection(null, -1, -1);
        
        _running = true;
        _active = false;
    }
    
    
    // BE CAREFUL OF POTENTIAL DEADLOCKS HERE!!!
    @Override
    public void run()
    {
        while (_running)
        {
            if (isActive())
            {
                think();
                
                if (isActive())
                {
                    execute();
                }
            }
        }
    }
    
    
    @Override
    public void onTurnEnd(TurnEndEvent e)
    {
        // Execute doMove only after the player moves
        if (e.getTeam() == PLAYER_TEAM)
        {
            setActive(true);
        }
    }
    
    
    @Override
    public void handleGameQuit()
    {
        terminate();
    }
    
    
    public void think()
    {
        GameState mockGameState = createMockGameState(_currentGameState);
        Turn startNode = new Turn(mockGameState, null);
        
        // final move will be decided here
        int score = miniMax(startNode, INIT_SEARCH_DEPTH);
        // Have the DecisionNode hold what move was performed (difference between
        // it and its parent) so that it can be analyzed and submitted to the 
        // RequestHandler.
    }
    
    
    private GameState createMockGameState(GameState gs)
    {
        return new GameState(new Board(gs.getBoard()), new Hand(gs.getRedHand()), new Hand(gs.getBlueHand()));
    }
    
    
    
    /**
     * Return the final move (DecisionNode) the AI decides to play.
     * @param startNode DecisionNode to start on
     * @param depth to start at
     * @return int score of AI's best move
     */
    private int miniMax(GameState mockState, int depth)
    {
        // Stop processing if game is interrupted (forfeit, etc.)
        //if (!isActive()) return 0;
        
        // Score and team
        int score, topScore;
        Team team = (depth % 2 == 0) ? Team.RED : Team.BLUE; // even depth = RED, odd depth = BLUE
        
        // Board and Hands
        Board parentBoard = mockState.getBoard();
        Hand parentHand;
        
        if (team == Team.BLUE) // BLUE is AI
        {
            parentHand = mockState.getBlueHand();
            topScore = Integer.MAX_VALUE;
        }
        else                   // RED is Human Player
        {
            parentHand = mockState.getRedHand();
            topScore = Integer.MIN_VALUE;
        }
        
        // When a leaf node is reached, a final call is made to evaluate the leaf node's position
        // and return the final DecisionNode to send to a RequestHandler.
        if (depth > MAX_SEARCH_DEPTH)
        {
            return evaluate(mockState);
        }
        
        
        // MOVES
        
        for (int r = 0; r < Board.ROWS; r++)
        {
            for (int c = 0; c < Board.COLS; c++)
            {
                // Select the piece
                Piece piece = parentBoard.getPieceAt(r, c);
                _selection.setPiece(piece);
                _selection.setCell(r, c);
                
                if (piece != null)
                {
                    boolean[][] validMoves = piece.getValidMoves(parentBoard.getPieces(), r, c);
                    for (int toRow = 0; toRow < Board.ROWS; toRow++)
                    {
                        for (int toCol = 0; toCol < Board.COLS; toCol++)
                        {
                            if (validMoves[toRow][toCol])
                            {
                                int scoreWithPromo;
                                
                                doMove(mockState, r, c, toRow, toCol);
                                score = miniMax(mockState, depth + 1);
                                doPromote(mockState, toRow, toCol);
                                scoreWithPromo = miniMax(mockState, depth + 1);
                                
                                // If the score with a promotion is better, use it.
                                // Otherwise, undo the promotion for the final move.
                                if ((scoreWithPromo < score && team == Team.BLUE) ||
                                   (scoreWithPromo > score && team == Team.RED))
                                {
                                    score = scoreWithPromo;
                                }
                                else
                                {
                                    undoPromote(mockState, toRow, toCol);
                                }
                                
                                if (team == Team.BLUE)  // BLUE (AI) is minimizer
                                {
                                    if (score < topScore)
                                    {
                                        topScore = score;
                                        if (depth == INIT_SEARCH_DEPTH) _finalMove = createMockGameState(mockState);
                                    }
                                }
                                else                    // RED (Human Player) is maximizer
                                {
                                    topScore = Integer.max(topScore, score);
                                }
                                
                                undoPromote(mockState, toRow, toCol);
                                undoMove(mockState, r, c, toRow, toCol);
                            }
                        }
                    }
                }
            }
        }
        
        
        // DROPS
        
        for (Entry<Piece.Type, Stack<Piece>> pieces : parentHand.getPieces().entrySet())
        {
            Piece piece = pieces.getValue().peek();
            _selection.setPiece(piece);
            _selection.setCell(-1, -1);
            
            boolean[][] validDrops = piece.getValidDrops(parentBoard.getPieces());
            for (int toRow = 0; toRow < Board.ROWS; toRow++)
            {
                for (int toCol = 0; toCol < Board.COLS; toCol++)
                {
                    if (validDrops[toRow][toCol])
                    {
                        Turn drop = doDrop(mockState, toRow, toCol);
                        score = miniMax(mockState, depth + 1);
                        undoDrop(mockState, toRow, toCol);

                        if (team == Team.BLUE)  // BLUE (AI) is minimizer
                        {
                            if (score < topScore)
                            {
                                topScore = score;
                                if (depth == INIT_SEARCH_DEPTH) _finalMove = createMockGameState(mockState);
                            }
                        }
                        else                    // RED (Human Player) is maximizer
                        {
                            topScore = Integer.max(topScore, score);
                        }
                    }
                }
            }
        }
        
        
        return topScore;
    }
    
    
    // Move Methods
    private Turn doMove(GameState mockState, int fromR, int fromC, int toR, int toC)
    {
         if (_ruleBook.canMovePieceTo(_selection, mockState.getBoard().getPieces(), toR, toC))
         {
             return new Turn(fromR, fromC, toR, toC, mockState.getBoard().getPieceAt(toR, toC), false);
         }
    }
    
    private Turn undoMove(GameState mockState, int fromR, int fromC, int toR, int toC)
    {
        
    }
    
    private Turn doPromote(GameState mockState, int fromR, int fromC)
    {
        
    }
    
    private Turn undoPromote(GameState mockState, int fromR, int fromC)
    {
        
    }
    
    private Turn doDrop(GameState mockState, int toRow, int toCol)
    {
        
    }
    
    private Turn undoDrop(GameState mockState, int toRow, int toCol)
    {
        
    }
    
    
    /**
     * Evaluate the state of the game giving it a score that the AI will use
     * to assess which move choice is optimal.
     * @param state
     * @return 
     */
    private int evaluate(GameState state)
    {
        // Use an evaluation function (maybe an Evaluator based off of difficulty)
        // to analyze worth of a move by the AI
    }
    
    
    /**
     * This method will get the result of the AI's analysis and execute
     * its final decision through the RequestHandler.
     */
    public void execute()
    {
        
        
        // End AI turn
        setActive(false);
    }
    
    
    /**
     * Stop the AI's thread of execution.
     */
    public void terminate()
    {
        _running = false;
        setActive(false);
    }
}
