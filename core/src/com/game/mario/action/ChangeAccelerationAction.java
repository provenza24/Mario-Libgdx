package com.game.mario.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.game.mario.sprite.AbstractSprite;

public class ChangeAccelerationAction extends Action {

	private AbstractSprite sprite;
	
	private Vector2 acceleration;

	public ChangeAccelerationAction(AbstractSprite sprite, Vector2 acceleration) {		
		this.sprite = sprite;
		this.acceleration = acceleration;
	}

	@Override
	public boolean act(float delta) {		
		sprite.setAcceleration(acceleration);
		return true;
	}

}
