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
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.GameObject;
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
    GameObject<Board> _boardObj;
    List<GameObject<Piece>> _pieceObjects;
    
    
    public PlayScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
        
        _pieceObjects = new LinkedList<>();
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
        Board board = new Board();
        _boardObj = new GameObject<>(boardTex, board, boardX, boardY);
        
        // Create pieces and place into cells
        Pawn pawn = new Pawn();
        board.getCellAt(0, 0).setPiece(pawn);
        _pieceObjects.add(new GameObject<>(pawnTex, pawn, boardX, boardY));
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        // Update objects here

        // Draw textures and text to the screen
        batch.begin();
        batch.draw(_woodTex, 0, 0);
        _boardObj.draw(batch);
        
        for (GameObject<Piece> pieceObj : _pieceObjects)
        {
            pieceObj.draw(batch);
        }
        
        batch.end();
    }
    
    
    @Override
    public boolean  touchDown(int screenX, int screenY, int pointer, int button)
    {
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
