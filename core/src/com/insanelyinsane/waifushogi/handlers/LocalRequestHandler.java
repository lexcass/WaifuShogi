/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.handlers;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.Sender;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TurnEndEvent;
import com.insanelyinsane.waifushogi.interfaces.PromotionConfirmation;
import com.insanelyinsane.waifushogi.interfaces.WinConfirmation;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.DropListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.PromotionListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.listeners.TurnEndListener;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.screens.MatchScreen;
import com.insanelyinsane.waifushogi.screens.ScreenType;

/**
 *
 * @author Alex Cassady
 */
public class LocalRequestHandler extends RequestHandler
{
    /**
     * Request the selection of a piece that's on the Board or a Hand.
     * Parameters "r" and "c" are required for a request from the Board,
     * but requests from the Hand can put any "r" or "c" with no effect.
     * @param from
     * @param target
     * @param r
     * @param c 
     */
    public void requestSelection(Sender from, Piece target, int r, int c)
    {
        // If the request is from the local player (touching the Board or Hand) and the 
        // opponent is an AI, ignore the request if it's not the local player's turn.
        if (from == Sender.LOCAL && _referee.getBluePlayer().getType() == Player.Type.AI)
        {
            if (_referee.whoseTurn() == _referee.getBluePlayer())
            {
                return;
            }
        }
        
        SelectionEvent e;
        
        // Try to select on Board first
        e = _referee.selectPieceOnBoard(target, r, c);
        
        // If the piece is not on the Board, try the Hand
        if (e == null) e = _referee.selectPieceInHand(target);
        
        // Inform Selection listeners of the new selection
        if (e != null) 
        {
            for (SelectionListener l : _selectionListeners)
            {
                l.onWaifuSelected(e);
            }
        }
    }
    
    
    /**
     * Request to move the currently selected Piece (tracked by the Referee) to the 
     * given row and column. If the move is in bounds and valid, a MoveEvent is generated.
     * If the piece at (r, c) should be captured, a CaptureEvent is generated and dispatched
     * first, and then the MoveEvent is dispatched (otherwise the piece that moved is captured).
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Note: Null events are disregarded, so a null event in this case means ignore the request.
     * This means it's safe to call request move on each touch of the board since it will have
     * no adverse effects.
     * @param r
     * @param c 
     */
    public void requestMove(Sender sender, int r, int c)
    {
        if (!Board.inBounds(r, c)) return;
        
        // Generate move event and stop processing if move is invalid
        MoveEvent e = _referee.movePieceTo(r, c);
        if (e == null) return;
        
        // Capture the piece in the cell (if there is one)
        Piece captured = _referee.capturePieceAt(r, c);
        boolean gameOver = false;
        if (captured != null)
        {
            _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(captured)));
            
            if (captured.getType() == Piece.Type.JADE)
            {
                // Player wins
                System.out.println("---------------------------------------------------------------\n" + captured.getTeam().toString() + " is the winner!\n---------------------------------------------------------------");
                
                gameOver = true;
                
                // Use same team since team changes upon capture; Jade is now on winner's team.
                _winConfirmer.confirmWin(this, captured.getTeam());
            }
        }
        
        // Move the selected piece
        _moveListeners.forEach(l -> l.onWaifuMoved(e));
        
        
        // Handle promotion
        Piece toPromote = _referee.promotePieceAt(r, c);
        if (toPromote != null && !gameOver)
        {
            requestPromotion(toPromote, _referee.isPieceStuck(toPromote, r, c) || sender == Sender.AI);
        }
        
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        Team ended = _referee.finishTurn();
        _turnEndListeners.forEach(l -> l.onTurnEnd(new TurnEndEvent(ended)));
    }
    
    
    /**
     * Request to replace a captured Piece (or drop) at the specified row and column.
     * If (r, c) is in bounds and the Drop (drop) is valid, a DropEvent is generated
        and the ReplaceListeners are given the event.
 
        ///////////////////////////////////////////////////////////////////////
        Note: Null events are ignored, so there is no harm in making this request on every touch
        of the Board for example.
     * @param r
     * @param c 
     */
    public void requestDrop(int r, int c)
    {
        if (!Board.inBounds(r, c)) return;
        
        DropEvent e = _referee.dropPieceAt(r, c);
        if (e == null) return;
        
        // Drop the selected piece
        _dropListeners.forEach(l -> l.onWaifuDropped(e));
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        Team ended = _referee.finishTurn();
        _turnEndListeners.forEach(l -> l.onTurnEnd(new TurnEndEvent(ended)));
    }
    
    
    protected void requestPromotion(Piece p, boolean auto)
    {
        if (auto)
        {
            _promotionListeners.forEach(l -> l.onWaifuPromoted(p));
        }
        else
        {
            _promoConfirmer.confirmPromotion(this, p);
        }
    }
    
    
    @Override
    public void handlePromotion(Piece p)
    {
        _promotionListeners.forEach(l -> l.onWaifuPromoted(p));
    }
    
    
    @Override
    public void handleGameWon(Boolean playAgain)
    {
        if (playAgain)
        {
            // Reload Play screen
            _screen.changeScreen(ScreenType.LOCAL_MULTIPLAYER);
        }
        else
        {
            // Got to Main Menu screen
            _screen.changeScreen(ScreenType.MAIN_MENU);
        }
    }
    
    
    public LocalRequestHandler(MatchScreen screen, Referee ref, SelectionListener highlighter, PromotionConfirmation c, WinConfirmation w)
    {
        super(screen, ref, highlighter, c, w);
    }
}
