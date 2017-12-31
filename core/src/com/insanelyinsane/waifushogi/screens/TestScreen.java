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
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.insanelyinsane.waifushogi.requesthandlers.RequestHandler;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.pieces.Pawn;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.Highlighter;
import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.ui.PlayUI;
import com.insanelyinsane.waifushogi.ui.UIController;
import com.insanelyinsane.waifushogi.actors.BoardObject;
import com.insanelyinsane.waifushogi.actors.HandObject;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.pieces.Bishop;
import com.insanelyinsane.waifushogi.pieces.GoldGeneral;
import com.insanelyinsane.waifushogi.pieces.JadeGeneral;
import com.insanelyinsane.waifushogi.pieces.Knight;
import com.insanelyinsane.waifushogi.pieces.Lance;
import com.insanelyinsane.waifushogi.pieces.Rook;
import com.insanelyinsane.waifushogi.pieces.SilverGeneral;
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
    //List<Waifu> _waifus;
    
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
    int     _row;
    int     _col;
    String  _tex;
    Piece   _piece;
    
    
    
    public TestScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
        
        //_waifus = new LinkedList<>();
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
        
        
        _piece = new Pawn(_chosenTeam);
        _tex = Textures.PAWN;
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
        
        
        
        // Add the pieces
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
        if (_board.getBoard().addPiece(piece, row, col))
        {
            Waifu obj = new Waifu(tex, _board.getX() + col * Board.CELL_WIDTH, _board.getY() + row * Board.CELL_HEIGHT, piece, _board, _redHand, _blueHand);
            addActor(obj);
            _requestHandler.registerWaifu(obj);
        }
        else
        {
            Gdx.app.debug("Warning", piece.getType().toString() + " was not added at (" + row + ", " + col + ").");
        }
    }
    
    
    @Override
    public void update(float delta)
    {
        //////////////////////////////////////
        // Update objects here
//        AssetManager assets = getAssets();
//        
//        int x = Gdx.input.getX();
//        int y = Gdx.input.getY();
//
//        _row = Board.ROWS - ((y - BOARD_Y) / Board.CELL_HEIGHT) - 1;
//        _row = (_row >= 9) ? -1 : _row;
//
//        _col = (x - BOARD_X) / Board.CELL_WIDTH;
//        _col = (_col >= 9) ? -1 : _col;
//
//        if (_row < 0 || _col < 0) return;
//        
//        // If the board is right-clicked, the currently selected piece will be added to the board.
//        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && _clickReleased)
//        {
//            _clickReleased = false;
//            
//            Gdx.app.debug("Selected cell", "(" + _row + ", " + _col + ")");
//            addPiece(_piece, assets.get(_tex), _row, _col);
//        }
//        else if (!Gdx.input.isButtonPressed(Buttons.RIGHT))
//        {
//            _clickReleased = true;
//        }
//        
//        // If the mouse's BACK button is pressed, remove the piece the cursor is on.
//        else if (Gdx.input.isButtonPressed(Buttons.BACK))
//        {
//            removePiece(_row, _col);
//        }
//        
//        if (Gdx.input.isKeyJustPressed(Keys.P))
//        {
//            _piece = new Pawn(_chosenTeam);
//            _tex = Textures.PAWN;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.R))
//        {
//            _piece = new Rook(_chosenTeam);
//            _tex = Textures.ROOK;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.B))
//        {
//            _piece = new Bishop(_chosenTeam);
//            _tex = Textures.BISHOP;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.J))
//        {
//            _piece = new JadeGeneral(_chosenTeam);
//            _tex = Textures.JADE_GENERAL;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.S))
//        {
//            _piece = new SilverGeneral(_chosenTeam);
//            _tex = Textures.SILVER_GENERAL;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.G))
//        {
//            _piece = new GoldGeneral(_chosenTeam);
//            _tex = Textures.GOLD_GENERAL;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.K))
//        {
//            _piece = new Knight(_chosenTeam);
//            _tex = Textures.KNIGHT;
//        }
//        else if (Gdx.input.isKeyJustPressed(Keys.L))
//        {
//            _piece = new Lance(_chosenTeam);
//            _tex = Textures.LANCE;
//        }
//        
//        if (Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT))
//        {
//            _chosenTeam = (_chosenTeam == Team.RED) ? Team.BLUE : Team.RED;
//        }
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        //////////////////////////////////////////
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
        
        batch.begin();
        _font.draw(batch, "Testing 1, 2, 3...", 16, BOARD_Y - 16);
        batch.end();
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
}