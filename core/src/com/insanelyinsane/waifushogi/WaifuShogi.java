package com.insanelyinsane.waifushogi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.screens.PlayScreen;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenType;
import java.util.HashMap;

public class WaifuShogi extends ApplicationAdapter implements InputProcessor, ScreenChangeListener 
{
    
	SpriteBatch _batch;
        HashMap<ScreenType, Screen> _screens;
        Screen _activeScreen;
	
	@Override
	public void create() 
        {
            // Application setup
            _batch = new SpriteBatch();
            Gdx.input.setInputProcessor(this);
            
            // Screen initialization
            _screens = new HashMap<>();
            addScreens();
            _activeScreen = _screens.get(ScreenType.PLAY);
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
        
        
        @Override
        public void onScreenChanged(ScreenType type)
        {
            _activeScreen = _screens.get(type);
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
	}
	
        
        /**
         * Calls dispose for the active screen, and releases any global resources.
         */
	@Override
	public void dispose()
        {
		_batch.dispose();
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
