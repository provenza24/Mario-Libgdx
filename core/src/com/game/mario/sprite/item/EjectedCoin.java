package com.game.mario.sprite.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.sprite.AbstractItem;

public class EjectedCoin extends AbstractItem {

	public EjectedCoin(float x, float y) {
		super(x, y);			
	}

	@Override
	public void initializeAnimations() {
		
		spriteSheet = new Texture(Gdx.files.internal("sprites/coin-from-bloc.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 9, spriteSheet.getHeight() / 1);				
		TextureRegion[] coinFrames = new TextureRegion[9];
		coinFrames[0] = tmp[0][0];
		coinFrames[1] = tmp[0][1];
		coinFrames[2] = tmp[0][2];
		coinFrames[3] = tmp[0][3];
		coinFrames[4] = tmp[0][4];
		coinFrames[5] = tmp[0][5];
		coinFrames[6] = tmp[0][6];
		coinFrames[7] = tmp[0][7];
		coinFrames[8] = tmp[0][8];		
		currentAnimation = new Animation(0.45f/9, coinFrames);
		currentAnimation.setPlayMode(PlayMode.NORMAL);
	}
	
	@Override
	protected void updateAnimation(float delta) {
		// Ovverided because the coin animation must be played only one time
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, false);		
	}

	@Override
	public void addAppearAction() {
		SequenceAction actions = new SequenceAction(
				ActionFacade.createMoveAction(getX(), getY()+2, 0.2f),
				ActionFacade.createMoveAction(getX(), getY()+2, 0.25f),
				new DeleteItemAction(this));
		addAction(actions);
		
	}
		
}
