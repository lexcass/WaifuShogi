package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.screens.LoadScreen;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenFactory;
import com.insanelyinsane.waifushogi.screens.ScreenType;

public class WaifuShogi extends ApplicationAdapter implements InputProcessor, ScreenChangeListener 
{
        public static final boolean DEBUG = true;
        public static String testToLoad = "";
    
	SpriteBatch _batch;
        Screen _activeScreen;
        Screen _nextScreen;
        
        
        public WaifuShogi(String test)
        {
            super();
            testToLoad = test;
        }
        
	
	@Override
	public void create() 
        {
            // Application setup
            _batch = new SpriteBatch();
            Gdx.input.setInputProcessor(this);
            
            // Allow debug logging
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            
            // Screen initialization ( the lazy way -_- )
            onScreenChanged(new ScreenChangeEvent(ScreenType.PLAY));
	}
        
        /**
         * Returns the active screen.
         */
        public final Screen getScreen() { return _activeScreen; }
        
        
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
            _nextScreen = ScreenFactory.createScreen(e.getType(), this, _batch);
            
            // Report non-existing screen to debug log
            if (_nextScreen == null) Gdx.app.debug("Error", "WaifuShogi::_nextScreen was null in method onScreenChanged.");
            
            // Fail hard if _nextScreen is null (uncaught NullPointerException). 
            // This is unacceptable behavior for the end user. 
            _activeScreen = new LoadScreen(this, _batch, _nextScreen.getAssets());
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
	}
        
        
        /**
         * When the screen is touched or clicked, call the touchDown method of
         * the active screen with the given data.
         * @param screenX
         * @param screenY
         * @param pointer
         * @param button
         * @return 
         */
        public boolean  touchDown(int screenX, int screenY, int pointer, int button) 
        { 
            _activeScreen.touchDown(screenX, screenY, pointer, button);
            
            return true; 
        }
        
        
        // Possibly necessary callbacks that are ignored for now...
        public boolean	touchDragged(int screenX, int screenY, int pointer) { return true; }
        public boolean	touchUp(int screenX, int screenY, int pointer, int button) { return true; }
        
        
        
        // Unnecessary callbacks that will be ignored...
        public boolean	keyDown(int keycode) { return true; }
        public boolean	keyTyped(char character) { return true; }
        public boolean	keyUp(int keycode) { return true; }
        public boolean	mouseMoved(int screenX, int screenY) { return true; }
        public boolean	scrolled(int amount) { return true; }

}
