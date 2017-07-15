package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.screens.LoadScreen;
import com.insanelyinsane.waifushogi.screens.PlayScreen;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenType;
import java.util.HashMap;

public class WaifuShogi extends ApplicationAdapter implements InputProcessor, ScreenChangeListener 
{
    
	SpriteBatch _batch;
        HashMap<ScreenType, Screen> _screens;
        Screen _activeScreen;
        Screen _nextScreen;
	
	@Override
	public void create() 
        {
            // Application setup
            _batch = new SpriteBatch();
            Gdx.input.setInputProcessor(this);
            
            // Screen initialization
            _screens = new HashMap<>();
            addScreens();
            onScreenChanged(ScreenType.PLAY);
	}
        
        
        /**
         * Add screens to the screen Hash Map for easy access
         */
        public void addScreens()
        {
            _screens.put(ScreenType.PLAY, new PlayScreen(this, _batch));
        }
        
        /**
         * Returns the active screen.
         */
        public final Screen getScreen() { return _activeScreen; }
        
        
        /**
         * Change to the given screen type, but first set the active screen to a load
         * screen that will load the next screen's assets. Then, change to that screen
         * when loading is finished.
         * @param type 
         */
        @Override
        public void onScreenChanged(ScreenType type)
        {
            _nextScreen = _screens.get(type);
            _activeScreen = new LoadScreen(this, _batch, _nextScreen.getAssets());
            System.out.println("Changing to new screen.");
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
                
                // If it's a load screen, load the next screens assets then
                // switch to it.
                if (_activeScreen instanceof LoadScreen)
                {
                    LoadScreen screen = (LoadScreen)_activeScreen;
                    if (screen.loadingCompleted())
                    {
                        _activeScreen = _nextScreen;
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
                _screens.forEach((type, screen) -> screen.getAssets().dispose());
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
