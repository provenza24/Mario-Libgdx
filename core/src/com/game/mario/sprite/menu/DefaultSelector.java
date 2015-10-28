package com.game.mario.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class DefaultSelector extends Image {

	public DefaultSelector() {		
		super(new Texture(Gdx.files.internal("sprites/right_arrow.png")))	;		
	}

}
