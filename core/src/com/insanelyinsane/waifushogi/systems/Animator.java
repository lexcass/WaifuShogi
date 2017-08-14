/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author A Wild Popo Appeared
 */
public class Animator 
{
    
    private final static float FRAME_RATE = 1.0f / 2.0f;
    private TextureRegion[] _frames;
    private final HashMap<String, Animation<TextureRegion>> _animations;
    
    private Animation<TextureRegion> _active;
    private TextureRegion _keyFrame;
    private float _elapsedTime;
    
    
    public Animator(Texture tex, int frameWidth, int frameHeight)
    {
        _animations = new HashMap<>();
        _elapsedTime = 0.0f;
        
        // Split texture into texture regions
        TextureRegion[][] regions = TextureRegion.split(tex, frameWidth, frameHeight);
        _frames = new TextureRegion[regions.length * regions[0].length];
        
        // Copy texture regions into frames array
        int index = 0;
        for (int r = 0; r < regions.length; r++)
        {
            for (int c = 0; c < regions[r].length; c++)
            {
                _frames[index++] = regions[r][c];
                
                Gdx.app.debug("Current Index", Integer.toString(index));
            }
        }
        
        Gdx.app.debug("Frame", String.valueOf(FRAME_RATE));
    }
    
    public void loadFromFile(String fileName)
    {
        // Read content of .anim file
        FileHandle file = Gdx.files.internal("animations/" + fileName + ".anim");
        String content = file.readString();
        
        Gdx.app.debug("Content", content);
        
        // Split into lines since one line is data for one animation
        String[] lines = content.split("\\r?\\n");
        
        // Log message in case of empty file
        if (lines.length < 1) 
        {
            Gdx.app.debug("Error", fileName + ".anim is empty.");
            return;
        }
        
        for (String s : lines)
        {
            Gdx.app.debug("Line", s);
            String[] data = s.split(" ");
            
            // Elements in data except for the 0th element (the string name)
            String[] indices = Arrays.copyOfRange(data, 1, data.length);
            
            // Error checking
            // If string isn't at least 2 characters long, error.
            // If there is not at least 1 index for the animation, error.
            if (data[0].length() < 2)
            {
                Gdx.app.debug("Animator::loadFromFile", fileName + ".anim should have a string name for each animation.");
                return;
            }
            if (indices.length < 1)
            {
                Gdx.app.debug("Animator::loadFromFile", fileName + ".anim should have indices for each animation.");
                return;
            }
            
            // Copy TextureRegions at indices into array
            TextureRegion[] frameArr = new TextureRegion[indices.length];
            
            for (int i = 0; i < indices.length; i++)
            {
                int index = Integer.parseInt(indices[i]);
                
                frameArr[i] = _frames[index];
            }
            
            // Add animation with array of copied frames to animations HashMap
            Animation<TextureRegion> a = new Animation<>(FRAME_RATE, frameArr);
            _animations.put(data[0], a);
        }
    }
    
    
    public void update(float delta)
    {
        if (_active == null) return;
        
        _elapsedTime += delta;
        _keyFrame = _active.getKeyFrame(_elapsedTime, true);
    }
    
    
    public TextureRegion getFrame()
    {
        return _keyFrame;
    }
    
    
    public void setAnimation(String anim)
    {
        boolean hasProblem = false;
        
        if (_animations.containsKey(anim))
        {
            if (_animations.get(anim) != null)
            {
                _active = _animations.get(anim);
                return;
            }
            
            hasProblem = true;
        }
        else
        {
            hasProblem = true;
        }
        
        if (hasProblem)
        {
            throw new GdxRuntimeException("Animator has no animation " + anim + ".");
        }
    }
}
