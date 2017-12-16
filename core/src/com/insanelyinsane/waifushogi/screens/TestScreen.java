/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.insanelyinsane.waifushogi.assets.Textures;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.RequestHandler;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.pieces.Pawn;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Rook;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Highlighter;
import com.insanelyinsane.waifushogi.systems.Referee;
import com.insanelyinsane.waifushogi.ui.PlayUI;
import com.insanelyinsane.waifushogi.ui.UIController;
import com.insanelyinsane.waifushogi.ui.actors.BoardObject;
import com.insanelyinsane.waifushogi.ui.actors.HandObject;
import com.insanelyinsane.waifushogi.ui.actors.Waifu;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Cassady
 */
public class TestScreen extends Screen implements QuitListener
{
    // Assets to load
    Texture _woodTex;
    BitmapFont _font = new BitmapFont();
    
    // Objects to update
    BoardObject  _board;
    HandObject _redHand;
    HandObject _blueHand;
    List<Waifu> _waifus;
    
    // Systems
    Highlighter _highlighter;
    RequestHandler _requestHandler;
    
    List<TouchListener> _touchListeners;
    
    // Board Coordinates
    int BOARD_X;
    int BOARD_Y;
    
    
    // Flags
    boolean _clickReleased = true;
    boolean _promotePiece = false;
    Team    _chosenTeam = Team.RED;
    
    
    public TestScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
        
        _waifus = new LinkedList<>();
        _touchListeners = new LinkedList<>();
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
        loadAsset("textures/Rook.png", Texture.class);
        loadAsset("textures/Bishop.png", Texture.class);
        loadAsset("textures/Lance.png", Texture.class);
        loadAsset("textures/Knight.png", Texture.class);
        loadAsset("textures/SilverGeneral.png", Texture.class);
        loadAsset("textures/GoldGeneral.png", Texture.class);
        loadAsset("textures/JadeGeneral.png", Texture.class);
    }
    
    @Override
    public void create()
    {
        //////////////////////////////
        // Create assets
        AssetManager assets = getAssets();
        
        Texture boardTex = assets.get(Textures.BOARD);
        Texture woodTex = assets.get(Textures.WOOD_BACKGROUND);
        
        // Set the background of the screen to wood texture
        setBackground(woodTex);
        
        // Create UI
        PlayUI ui = new PlayUI(getStage(), this);
        
        // Initialize logic objects (Hand, Board, Highlighter, RequestHandler, etc.)
        Hand blue = new Hand(Team.BLUE);
        Hand red = new Hand(Team.RED);
        Board board = new Board();
        BOARD_X = Gdx.graphics.getWidth() / 2 - boardTex.getWidth() / 2;
        BOARD_Y = Gdx.graphics.getHeight() / 2 - boardTex.getHeight() / 2;
        
        // Initialize systems (lifeblood of game logic)
        _highlighter = new Highlighter(BOARD_X, BOARD_Y);
        _requestHandler = new RequestHandler(new Referee(board, red, blue), _highlighter, ui);
        
        
        ///////////////////////////////
        // Initialize board object
        _board = new BoardObject(boardTex, BOARD_X, BOARD_Y, board, _requestHandler);
        addActor(_board);
        
        
        //////////////////////////////////
        // Initialize player hand objects
        
        _blueHand = new HandObject(0, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, blue, _font, _requestHandler);
        addActor(_blueHand);
        
        // The red hand builds from bottom to top (x, y is bottom-left), so negative width and height will give proper bounds for touch coords.
        
        _redHand = new HandObject(Gdx.graphics.getWidth() - Board.CELL_WIDTH, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, red, _font, _requestHandler);
        addActor(_redHand);
        
        
        // Add the UI's actors to the stage and draw on top of Board and Pieces
        getUIController().loadUI(ui);
        
        
        addPiece(new Pawn(Team.RED), assets.get(Textures.PAWN), 0, 1);
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
        _board.getBoard().addPiece(piece, row, col);
        
        Waifu obj = new Waifu(tex, _board.getX() + col * Board.CELL_WIDTH, _board.getY() + row * Board.CELL_HEIGHT, piece, _board, _redHand, _blueHand);
        _waifus.add(obj);
        _requestHandler.registerWaifu(obj);
        addActor(obj);
    }
    
    
    /// NOTE: Instead of removing Waifus, just place them back at starting positions?
    /**
     * Reset the board to its initial state.
     */
    public void reset()
    {
        _board.getBoard().clear();
        _waifus.clear();
    }
    
    
    @Override
    public void update(float delta)
    {
        //////////////////////////////////////
        // Update objects here
        
        int x = 0, y = 0, row = 0, col = 0;
        AssetManager assets = getAssets();
        
        // If the board is right-clicked, the currently selected piece will be added to the board.
        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && _clickReleased)
        {
            _clickReleased = false;
            
            x = Gdx.input.getX();
            y = Gdx.input.getY();
            
            row = Board.ROWS - ((y - BOARD_Y) / Board.CELL_HEIGHT) - 1;
            row = (row >= 9) ? -1 : row;
            
            col = (x - BOARD_X) / Board.CELL_WIDTH;
            col = (col >= 9) ? -1 : col;
            
            if (row < 0 || col < 0) return;
            
            Gdx.app.debug("Selected cell", "(" + row + ", " + col + ")");
        }
        else if (!Gdx.input.isButtonPressed(Buttons.RIGHT))
        {
            _clickReleased = true;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.P))
        {
            Piece pawn = new Pawn(_chosenTeam);
            if (_promotePiece) pawn.promote();
            addPiece(pawn, assets.get(Textures.PAWN), row, col);
        }
        else if (Gdx.input.isKeyJustPressed(Keys.R))
        {
            Piece rook = new Rook(_chosenTeam);
            if (_promotePiece) rook.promote();
            addPiece(rook, assets.get(Textures.ROOK), row, col);
        }
        else if (Gdx.input.isKeyJustPressed(Keys.B))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.J))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.S))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.G))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.K))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.L))
        {
            
        }
        else if (Gdx.input.isKeyJustPressed(Keys.ENTER))
        {
            _promotePiece = !_promotePiece;
        }
        else if (Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT))
        {
            _chosenTeam = (_chosenTeam.equals(Team.RED)) ? Team.BLUE : Team.RED;
        }
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        //////////////////////////////////////////
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
}