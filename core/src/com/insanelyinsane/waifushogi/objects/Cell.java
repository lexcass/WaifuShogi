package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 *
 * @author alex
 */
public class Cell extends Actor
{
    public Cell()
    {
        super();
        
        addListener(new InputListener() 
        {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
            {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {}
        });
    }
    
}
