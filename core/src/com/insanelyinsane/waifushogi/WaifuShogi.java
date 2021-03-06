package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.screens.LoadScreen;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenProducer;
import com.insanelyinsane.waifushogi.screens.ScreenType;
import com.insanelyinsane.waifushogi.ui.UIController;

public class WaifuShogi extends ApplicationAdapter implements ScreenChangeListener 
{
        public final static boolean DEBUG = true;
    
	SpriteBatch _batch;
        Screen _activeScreen;
        Screen _nextScreen;
        
        Stage _stage;
        UIController _uiController;
        
        
        public WaifuShogi(boolean test)
        {
            super();
        }
        
	
	@Override
	public void create() 
        {
            // Application setup
            _batch = new SpriteBatch();
            _stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
            _uiController = new UIController(_stage);
            Gdx.input.setInputProcessor(_stage);
            
            // Allow debug logging
            if (DEBUG) Gdx.app.setLogLevel(Application.LOG_DEBUG);
            else       Gdx.app.setLogLevel(Application.LOG_NONE);
            
            // Screen initialization ( the lazy way -_- )
            /*if (DEBUG) 
            {
                onScreenChanged(new ScreenChangeEvent(ScreenType.TEST));
            }
            else
            {*/ // LOCAL_MULTIPLAYER, VERSUS_AI
                onScreenChanged(new ScreenChangeEvent(ScreenType.MAIN_MENU));
            //}
	}
        
        /**
         * Returns the active screen.
         */
        public final Screen getScreen() { return _activeScreen; }
        
        public final Stage getStage() { return _stage; }
        
        
        /**
         * Change to the given screen type, but first set the active screen to a load
         * screen that will load the next screen's assets. Then, change to that screen
         * when loading is finished.
         * @param e 
         */
        @Override
        public void onScreenChanged(ScreenChangeEvent e)
        {
            // Load next screen's assets asynchronously and show loading screen in the process
            _nextScreen = ScreenProducer.produceScreen(e.getType(), this, _batch, _uiController, e.getArg1(), e.getArg2());
            
            // Report non-existing screen to error log
            if (_nextScreen == null) Gdx.app.error("Error", "WaifuShogi::_nextScreen was null in method onScreenChanged.");
            
            
            // Clean up previous active screen
            _stage.clear();
            if (_activeScreen != null) _activeScreen.getAssets().dispose();
            
            _activeScreen = new LoadScreen(this, _batch, _uiController, _nextScreen.getAssets());
            _activeScreen.create();
            
            Gdx.app.debug("Change screen", e.getType().toString());
        }

        
        /**
         * Renders the active screen.
         */
	@Override
	public void render() 
        {
                // Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                // Update active screen
                _activeScreen.render(Gdx.graphics.getDeltaTime());
                
                // If it's a load screen, load the next screen's assets then
                // switch to it and call its create method.
                if (_activeScreen instanceof LoadScreen)
                {
                    LoadScreen screen = (LoadScreen)_activeScreen;
                    if (screen.loadingCompleted())
                    {
                        _stage.clear();
                        _activeScreen.getAssets().dispose();
                        _activeScreen = _nextScreen;
                        _activeScreen.create();
                    }
                }
	}
	
        
        /**
         * Calls dispose for screens' assets, and releases any global resources.
         */
	@Override
	public void dispose()
        {
		_batch.dispose();
                _activeScreen.getAssets().dispose();
                _nextScreen.getAssets().dispose();
                _stage.dispose();
                _uiController.dispose();
	}

}
