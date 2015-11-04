package com.game.mario.sprite.statusbar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MarioCoins extends Image {

	public MarioCoins() {		
		super(new Texture(Gdx.files.internal("sprites/statusbar/coin.png")));		
	}

}
