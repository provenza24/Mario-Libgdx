package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {

	Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));

	@Override
	public void draw(Batch batch, float alpha) {
		 batch.draw(texture,this.getX(),getY());
	}

}
