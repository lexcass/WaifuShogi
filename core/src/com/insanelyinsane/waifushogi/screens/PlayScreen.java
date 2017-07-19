/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.GameObject;
import com.insanelyinsane.waifushogi.objects.pieces.Pawn;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Highlighter;
import com.insanelyinsane.waifushogi.systems.Referee;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alex
 */
public class PlayScreen extends Screen
{
    // Constants
    
    // Assets to load
    Texture _woodTex;
    
    // Objects to update
    GameObject<Board> _board;
    List<GameObject> _waifus;
    
    // Systems
    Referee _referee;
    Highlighter _highlighter;
    
    List<TouchListener> _touchListeners;
    
    
    public PlayScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        _waifus = new LinkedList<>();
        _touchListeners = new LinkedList<>();
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
    }
    
    @Override
    public void create()
    {
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get("textures/woodbg.jpg");
        Texture boardTex = assets.get("textures/ShogiBoard.png");
        Texture pawnTex = assets.get("textures/Pawn.png");
        
        
        // Initialize board
        int boardX = Gdx.graphics.getWidth() / 2 - boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - boardTex.getHeight() / 2;
        _board = new GameObject<>(boardTex, new Board(), boardX, boardY);
        
        // Create pieces and place into cells
        addPiece(new Pawn(Team.ONE), pawnTex, 2, 3);
        addPiece(new Pawn(Team.ONE), pawnTex, 4, 7);
        
        // Initialize systems
        _highlighter = new Highlighter(_board);
        _referee = new Referee(_board, _highlighter);
        
        _touchListeners.add(_referee);
    }
    
    
    /**
     * Add a piece with texture to cell at row, col on board.
     * Also adds piece as a new game object for the PlayScreen and adds to touch listeners
     * to receive touch events.
     * @param piece
     * @param tex
     * @param row
     * @param col 
     */
    public void addPiece(Piece piece, Texture tex, int row, int col)
    {
        _board.getObject().getCellAt(row, col).setPiece(piece);
        
        GameObject obj = new GameObject(tex, piece, _board.getX() + col * Cell.WIDTH, _board.getY() + row * Cell.HEIGHT);
        _waifus.add(obj);
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        // Update objects here

        // Draw textures and text to the screen
        batch.begin();
        batch.draw(_woodTex, 0, 0);
        _board.draw(batch);
        
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
        
        for (GameObject pieceObj : _waifus)
        {
            pieceObj.draw(batch);
        }
        
        batch.end();
    }
    
    
    /**
     * Inform all touch listeners of the location the screen was touched (clicked) at in
     * render coordinates (bottom-to-top, left-to-right).
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return 
     */
    @Override
    public boolean  touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (TouchListener l : _touchListeners)
        {
            // IMPORTANT: Touch coordinates go top-to-bottom left-to-right. This converts
            // these coordinates to bottom-to-top left-to-right which the rendering system
            // works with.
            
            l.onTouch(new TouchEvent(true, screenX, Gdx.graphics.getHeight() - screenY));
        }
        
        return true;
    }
    
    
    @Override
    public void resume()
    {
        
    }
    
    @Override
    public void pause()
    {
        
    }
    
    @Override
    public void resize(int x, int y)
    {
        
    }
    
    @Override
    public void show()
    {
        
    }
    
    @Override
    public void hide()
    {
        
    }
}
