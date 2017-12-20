/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.interfaces.PromotionHandler;
import com.insanelyinsane.waifushogi.listeners.PromotionListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.systems.Referee;
import java.util.LinkedList;
import java.util.List;
import com.insanelyinsane.waifushogi.interfaces.PromotionConfirmation;
import com.insanelyinsane.waifushogi.listeners.DropListener;

/**
 * The RequestHandler acts as a proxy between the Board and Hand objects and the
 * Referee object (the rule enforcer). The Board and Hands will make requests to the 
 * RequestHandler (selection, move, etc.), and if the Referee determines the request
 * is valid, a generated event is dispatched to the respective listeners.
 * @author Alex Cassady
 */
public class RequestHandler implements PromotionHandler
{
    /**
     * Represents the type of sender. This is so the RequestHandler can differentiate between
     * a Board and a Hand object.
     */
    public enum Sender { BOARD, HAND };
    
    private final Referee _referee;
    private final List<Waifu> _waifus;
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    private final List<DropListener> _replaceListeners;
    private final List<CaptureListener> _captureListeners;
    private final List<PromotionListener> _promotionListeners;
    private final PromotionConfirmation _promoConfirmer;
    
    
    /**
     * Stores a reference to the Referee and registered Waifus, and uses the Referee's
     * references to the Board and Hands to register them (and the highlighter) as listeners.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Waifus that want to receive events must manually register to the RequestHandler
     * via RequestHandler::registerWaifu().
     * @param ref
     * @param highlighter
     * @param waifus 
     */
    public RequestHandler(Referee ref, SelectionListener highlighter, PromotionConfirmation c)
    {
        _waifus = new LinkedList<>();
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _replaceListeners = new LinkedList<>();
        _captureListeners = new LinkedList<>();
        _promotionListeners = new LinkedList<>();
        
        _promoConfirmer = c;
        
        _referee = ref;
        
        // Register listeners
        // Selection
        _selectionListeners.add(highlighter);
        
        // Move
        _moveListeners.add(_referee.getBoard());
        
        // Replace
        _replaceListeners.add(_referee.getBoard());
        _replaceListeners.add(_referee.getRedHand());
        _replaceListeners.add(_referee.getBlueHand());
        
        // Capture
        _captureListeners.add(_referee.getRedHand());
        _captureListeners.add(_referee.getBlueHand());
    }
    
    
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
        SelectionEvent e;
        if (from == Sender.BOARD)
        {
            e = _referee.selectPieceOnBoard(target, r, c);
        }
        else
        {
            e = _referee.selectPieceInHand(target);
        }
        
        if (e != null) _selectionListeners.forEach(l -> l.onWaifuSelected(e));
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
    public void requestMove(int r, int c)
    {
        if (!inBounds(r, c)) return;
        
        // Generate move event and stop processing if move is invalid
        MoveEvent e = _referee.movePieceTo(r, c);
        if (e == null) return;
        
        // Capture the piece in the cell (if there is one)
        Piece captured = _referee.capturePieceAt(r, c);
        if (captured != null)
        {
            _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(captured)));
            
            if (captured.getType() == Piece.Type.JADE)
            {
                // Player wins
                System.out.println("---------------------------------------------------------------\n" + captured.getTeam().toString() + " is the winner!\n---------------------------------------------------------------");
            }
        }
        
        // Move the selected piece
        _moveListeners.forEach(l -> l.onWaifuMoved(e));
        
        
        // Handle promotion
        Piece toPromote = _referee.promotePieceAt(r, c);
        if (toPromote != null)
        {
            requestPromotion(toPromote, _referee.isPieceStuck(toPromote, r, c));
        }
        
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        _referee.finishTurn();
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
        if (!inBounds(r, c)) return;
        
        DropEvent e = _referee.dropPieceAt(r, c);
        if (e == null) return;
        
        // Drop the selected piece
        _replaceListeners.forEach(l -> l.onWaifuDropped(e));
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        _referee.finishTurn();
    }
    
    
    private void requestPromotion(Piece p, boolean auto)
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
    
    
    /**
     * Add a Waifu object to each of the listener lists (selection, move, replace, and capture).
     * @param w 
     */
    public void registerWaifu(Waifu w)
    {
        _waifus.add(w);
        _selectionListeners.add(w);
        _moveListeners.add(w);
        _replaceListeners.add(w);
        _captureListeners.add(w);
        _promotionListeners.add(w);
    }
    
    
    /**
     * Helper method to check if the specified row and column are within the bounds of the Board.
     * @param r
     * @param c
     * @return 
     */
    private boolean inBounds(int r, int c)
    {
        return r < Board.ROWS || c < Board.COLS || r >= 0 || c >= 0;
    }
}
