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
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.Waifu;
import com.insanelyinsane.waifushogi.objects.pieces.Pawn;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
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
    //GameObject<Board> _boardObj;
    Board _board;
    Texture _boardTex;
    int _boardX;
    int _boardY;
    List<Waifu> _waifus;
    List<TouchListener> _touchListeners;
    
    
    public PlayScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
        
        _waifus = new LinkedList<>();
        _touchListeners = new LinkedList<>();
    }
    
    @Override
    public void create()
    {
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get("textures/woodbg.jpg");
        _boardTex = assets.get("textures/ShogiBoard.png");
        Texture pawnTex = assets.get("textures/Pawn.png");
        
        
        // Initialize board
        _boardX = Gdx.graphics.getWidth() / 2 - _boardTex.getWidth() / 2;
        _boardY = Gdx.graphics.getHeight() / 2 - _boardTex.getHeight() / 2;
        _board = new Board();
        //_boardObj = new Waifu<>(boardTex, board, boardX, boardY);
        
        // Create pieces and place into cells
        addPiece(new Pawn(), pawnTex, 2, 3);
        addPiece(new Pawn(), pawnTex, 8, 7);
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
        _board.getCellAt(row, col).setPiece(piece);
        
        Waifu obj = new Waifu(tex, piece, _boardX + col * Cell.WIDTH, _boardY + row * Cell.HEIGHT);
        _waifus.add(obj);
        _touchListeners.add(obj);
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        // Update objects here

        // Draw textures and text to the screen
        batch.begin();
        batch.draw(_woodTex, 0, 0);
        batch.draw(_boardTex, _boardX, _boardY);
        
        for (Waifu pieceObj : _waifus)
        {
            pieceObj.draw(batch);
        }
        
        batch.end();
    }
    
    
    /**
     * Inform all touch listeners of the location the screen was touched (clicked) at.
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
            l.onTouch(screenX, screenY);
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
