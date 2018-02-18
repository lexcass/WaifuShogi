/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ai;

import com.insanelyinsane.waifushogi.GameState;
import com.insanelyinsane.waifushogi.RuleBook;
import com.insanelyinsane.waifushogi.Selection;
import com.insanelyinsane.waifushogi.Sender;
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





/*

NOTES

Fix bug that occurs when the else statement in Hand.removePiece() is removed.

Hand may be referring to the player's hand when trying drops but trying to
get the piece from the AI's hand causing EmptyStackException.

AI doesn't wait for player's response of promotion dialog.

Ensure accuracy of promotions. This may be an issue in late game.

Implement Alpha-Beta pruning and maybe try bit-boards or similar.

*/





/**
 *
 * @author Alex Cassady
 */
public class AIOpponent extends Thread implements TurnEndListener, QuitListener
{
    private int num_ops = 0;
    private int elapsed_time = 0;
    
    private final Team PLAYER_TEAM = Team.RED;
    private final int INIT_SEARCH_DEPTH = 1;
    private final int MAX_SEARCH_DEPTH = 2;
    
    private RequestHandler _requestHandler;
    private GameState _currentGameState;
    private RuleBook _ruleBook;
    private Selection _selection;
    private Selection _finalSelection;
    
    private Turn _finalMove;
    
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
        _finalSelection = new Selection(null, -1, -1);
        
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
        num_ops = 0;
        long time = System.currentTimeMillis();
        
        GameState mockGameState = createMockGameState(_currentGameState);
        
        // final move will be decided here
        miniMax(mockGameState, INIT_SEARCH_DEPTH);
        System.out.println("Minimax complete!");
        System.out.println("Num iterations: " + num_ops);
        System.out.println("Duration: " + ((System.currentTimeMillis() - time) / 1000));
        // Have the DecisionNode hold what move was performed (difference between
        // it and its parent) so that it can be analyzed and submitted to the 
        // RequestHandler.
        
//        int fromRow = _finalMove.getFromRow();
//        int fromCol = _finalMove.getFromCol();
//        Board startingBoard = _currentGameState.getBoard();
//        Hand startingHand = _currentGameState.getBlueHand();
        
        
        
        //_requestHandler.requestSelection(Sender.AI, startingBoard.getPieceAt(fromRow, fromCol), fromRow, fromCol);
        _requestHandler.requestSelection(Sender.AI, _finalSelection.getPiece(), _finalSelection.getRow(), _finalSelection.getCol());
        System.out.println("Select: (" + _finalSelection.getRow() + ", " + _finalSelection.getCol() + ")");
        
        int toRow = _finalMove.getToRow();
        int toCol = _finalMove.getToCol();
        if (_finalMove.getType() == Turn.Type.MOVE)
        {
            _requestHandler.requestMove(Sender.AI, toRow, toCol);
        }
        else
        {
            _requestHandler.requestDrop(toRow, toCol);
        }
    }
    
    
    private GameState createMockGameState(GameState gs)
    {
        return new GameState(new Board(gs.getBoard()), new Hand(gs.getRedHand()), new Hand(gs.getBlueHand()));
    }
    
    
    
    
    private int miniMax(GameState mockState, int depth)
    {
        // Stop processing if game is interrupted (forfeit, etc.)
        //if (!isActive()) return 0;
        
        num_ops++;
        
        // When a leaf node is reached, a final call is made to evaluate the leaf node's position
        // and return the final score.
        if (depth > MAX_SEARCH_DEPTH)
        {
            return evaluate(mockState);
        }
        
        // Score and team
        int score, topScore;
        int scoreWithPromo;
        Team team = (depth % 2 == 0) ? Team.RED : Team.BLUE; // even depth = RED, odd depth = BLUE
        
        // Board and Hands
        Board parentBoard = mockState.getBoard();
        Hand parentHand;
        
        if (team == Team.BLUE) // BLUE is AI (minimizer)
        {
            parentHand = mockState.getBlueHand();
            topScore = Integer.MAX_VALUE;
        }
        else                   // RED is Human Player (maximizer)
        {
            parentHand = mockState.getRedHand();
            topScore = Integer.MIN_VALUE;
        }
        
        
        // MOVES
        
        for (int r = 0; r < Board.ROWS; r++)
        {
            for (int c = 0; c < Board.COLS; c++)
            {
                Piece piece = parentBoard.getPieceAt(r, c);
                
                if (piece != null)
                {
                    if (piece.getTeam() == team)
                    {
                        // Select the piece
                        _selection.setPiece(piece);
                        _selection.setCell(r, c);

                        // Try every possible move for the piece
                        boolean[][] validMoves = piece.getValidMoves(parentBoard.getPieces(), r, c);
                        for (int toRow = 0; toRow < Board.ROWS; toRow++)
                        {
                            for (int toCol = 0; toCol < Board.COLS; toCol++)
                            {
                                if (validMoves[toRow][toCol])
                                {
                                    // Perform the move
                                    Turn move = doMove(mockState, r, c, toRow, toCol, team);
                                    if (move != null)
                                    {
                                        // Find the max/min score
                                        score = miniMax(mockState, depth + 1);

                                        // Promote the piece (if applicable)
                                        Turn promotion = doPromote(mockState, move, team);

                                        // Find the max/min score with a promotion
                                        if (promotion == null) scoreWithPromo = score;
                                        else scoreWithPromo = miniMax(mockState, depth + 1);

                                        // If the score with a promotion is better, use it.
                                        // Otherwise, undo the promotion for the final move.
                                        if ((scoreWithPromo < score && team == Team.BLUE) ||
                                           (scoreWithPromo > score && team == Team.RED))
                                        {
                                            score = scoreWithPromo;
                                            move = promotion;
                                        }
                                        else
                                        {
                                            undoPromote(mockState, move);
                                        }

                                        if (team == Team.BLUE)  // BLUE (AI) is minimizer
                                        {
                                            if (score < topScore)
                                            {
                                                topScore = score;
                                                if (depth == INIT_SEARCH_DEPTH) 
                                                {
                                                    _finalMove = move;
                                                    _finalSelection.setPiece(_currentGameState.getBoard().getPieceAt(r, c));
                                                    _finalSelection.setCell(r, c);
                                                }
                                            }
                                        }
                                        else                    // RED (Human Player) is maximizer
                                        {
                                            topScore = Integer.max(topScore, score);
                                        }

                                        undoPromote(mockState, move);
                                        undoMove(mockState, move);
                                    }
                                }
                            }
                        }
                    }
                    
                }
            }
        }
        
        
        // DROPS
        
        for (Entry<Piece.Type, Stack<Piece>> pieces : parentHand.getPieces().entrySet())
        {
            if (!pieces.getValue().isEmpty())
            {
                Piece piece = pieces.getValue().peek();

                if (piece != null)
                {
                    if (piece.getTeam() == team)
                    {
                        _selection.setPiece(piece);
                        _selection.setCell(-1, -1);

                        boolean[][] validDrops = piece.getValidDrops(parentBoard.getPieces());
                        for (int toRow = 0; toRow < Board.ROWS; toRow++)
                        {
                            for (int toCol = 0; toCol < Board.COLS; toCol++)
                            {
                                if (validDrops[toRow][toCol])
                                {
                                    Turn drop = doDrop(parentBoard, parentHand, validDrops, toRow, toCol);
                                    if (drop != null)
                                    {
                                        score = miniMax(mockState, depth + 1);
                                        undoDrop(parentBoard, parentHand, drop);

                                        if (team == Team.BLUE)  // BLUE (AI) is minimizer
                                        {
                                            if (score < topScore)
                                            {
                                                topScore = score;
                                                if (depth == INIT_SEARCH_DEPTH) 
                                                {
                                                    _finalMove = drop;
                                                    _finalSelection.setPiece(_currentGameState.getBlueHand().getPiecesOfType(piece.getType()).peek());
                                                    _finalSelection.setCell(-1, -1);
                                                }
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
                    }
                }
            }
        }
        
        
        return topScore;
    }
    
    
    // Move Methods
    
    /**
     * Perform a move on the mock state's board according to the game's rules.
     * @param mockState
     * @param fromR
     * @param fromC
     * @param toR
     * @param toC
     * @param team
     * @return 
     */
    private Turn doMove(GameState mockState, int fromR, int fromC, int toR, int toC, Team team)
    {
        Board board = mockState.getBoard();
        Hand hand = (team == Team.BLUE) ? mockState.getBlueHand() : mockState.getRedHand();
        Piece piece = board.getPieceAt(fromR, fromC);
        
        if ( _ruleBook.canMovePieceTo(_selection, piece.getValidMoves(board.getPieces(), fromR, fromC), toR, toC))
        {
            // Change state of captured piece to reflect its capture
            Piece captured = board.movePiece(fromR, fromC, toR, toC);
            boolean wasPromoted = false;
            
            if (captured != null)
            {
                captured.setCaptured(true);
                captured.setTeam(team);
                if (captured.isPromoted())
                {
                    wasPromoted = true;
                    captured.demote();
                }

                hand.addPiece(captured);
            }
            
            return new Turn(Turn.Type.MOVE, team, fromR, fromC, toR, toC, captured, wasPromoted);
        }

        return null;
    }
    
    
    /**
     * Undo the given move and reset the mock state's board to its original state.
     * @param mockState
     * @param move 
     */
    private void undoMove(GameState mockState, Turn move)
    {
        Board board = mockState.getBoard();
        Hand hand = (move.getTeam() == Team.RED) ? mockState.getRedHand() : mockState.getBlueHand();
        Piece captured = move.getCapturedPiece();
        
        board.movePiece(move.getToRow(), move.getToCol(), move.getFromRow(), move.getFromCol());
        
        // Reset original state of the captured piece and remove from hand
        if (captured != null)
        {
            board.putPiece(captured, move.getToRow(), move.getToCol());
            hand.removePiece(captured.getType());
            
            captured.setCaptured(false);
            captured.toggleTeam();
            if (move.wasCapturedPromoted()) captured.promote();
        }
    }
    
    
    /**
     * Promote the currently selected piece after it has been moved if applicable.
     * @param mockState
     * @param move
     * @param team
     * @return Turn containing data specifying what has changed.
     */
    private Turn doPromote(GameState mockState, Turn move, Team team)
    {
        Piece piece = _ruleBook.canPromotePieceAt(_selection, move.getToRow(), team);
        
        if (piece != null)
        {
            piece.promote();
            return new Turn(move).setPromoted(true);
        }
        
        return null;
    }
    
    
    /**
     * Demote the currently selected piece by undoing its promotion.
     * @param mockState
     * @param move 
     */
    private void undoPromote(GameState mockState, Turn move)
    {
        mockState.getBoard().getPieceAt(move.getToRow(), move.getToCol()).demote();
        move.setPromoted(false);
    }
    
    
    /**
     * Drop the piece to the given row and column on the mock state's board if legal.
     * @param board
     * @param hand
     * @param validDrops
     * @param toRow
     * @param toCol
     * @return Turn that contains data concerning what has changed.
     */
    private Turn doDrop(Board board, Hand hand, boolean[][] validDrops, int toRow, int toCol)
    {
        if (_ruleBook.canDropPieceAt(_selection, validDrops, toRow, toCol))
        {
            Piece piece = _selection.getPiece();
            hand.removePiece(piece.getType());
            board.putPiece(piece, toRow, toCol);
            return new Turn(Turn.Type.DROP, piece.getTeam(), -1, -1, toRow, toCol, null, false);
        }
        
        return null;
    }
    
    
    /**
     * Undo the given drop (move) that was applied to the mock state's board.
     * @param board
     * @param hand
     * @param drop 
     */
    private void undoDrop(Board board, Hand hand, Turn drop)
    {
        Piece piece = board.removePieceAt(drop.getToRow(), drop.getToCol());
        hand.addPiece(piece);
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
        Board board = state.getBoard();
        Hand redHand = state.getRedHand();
        Hand blueHand = state.getBlueHand();
        
        int score = 0;
        
        for (int fromRow = 0; fromRow < Board.ROWS; fromRow++)
        {
            for (int fromCol = 0; fromCol < Board.COLS; fromCol++)
            {
                Piece piece = board.getPieceAt(fromRow, fromCol);
                
                if (piece != null)
                {
                    boolean[][] validMoves = piece.getValidMoves(board.getPieces(), fromRow, fromCol);
                    
                    for (int r = 0; r < Board.ROWS; r++)
                    {
                        for (int c = 0; c < Board.COLS; c++)
                        {
                            if (validMoves[r][c])
                            {
                                Piece capture = board.getPieceAt(r, c);
                                if (capture != null)
                                {
                                    Team team = piece.getTeam();
                                    
                                    if (team == Team.RED) // Human Player
                                    {
                                        score += piece.getType().getIndex();
                                    }
                                    else    // AI
                                    {
                                        score -= piece.getType().getIndex();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return score;
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
