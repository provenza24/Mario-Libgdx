package com.game.mario.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MushroomSelector extends Image {

	public MushroomSelector() {		
		super(new Texture(Gdx.files.internal("sprites/mushroom_selector.png")))	;		
	}

}
