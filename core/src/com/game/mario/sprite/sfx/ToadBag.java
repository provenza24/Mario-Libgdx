package com.game.mario.sprite.sfx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;

public class ToadBag extends AbstractSfxSprite {
	
	private Animation shakingBagAnimation;
	
	private Animation openingBagAnimation;
	
	public ToadBag(float x, float y) {		
		super(x ,y);						
		renderingSize = new Vector2(2,2);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());		
		alive = true;
		gravitating = false;
		collidableWithTilemap = false;
		moveable = false;
		visible = true;
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.CASTLE_BAG;			
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 6, spriteSheet.getHeight() / 1);				
		TextureRegion[] frame = new TextureRegion[3];
		frame[0] = tmp[0][0];
		frame[1] = tmp[0][1];
		frame[2] = tmp[0][2];
		shakingBagAnimation = new Animation(0.2f, frame);		
		currentAnimation = shakingBagAnimation;
		
		frame = new TextureRegion[3];
		frame[0] = tmp[0][3];
		frame[1] = tmp[0][4];
		frame[2] = tmp[0][5];
		openingBagAnimation = new Animation(0.5f, frame);	
		openingBagAnimation.setPlayMode(PlayMode.NORMAL);
	}

	@Override
	public void addAppearAction() {			
	}

	public Animation getOpeningBagAnimation() {
		return openingBagAnimation;
	}

	public void setOpeningBagAnimation(Animation openingBagAnimation) {
		this.openingBagAnimation = openingBagAnimation;
	}

	@Override
	protected void updateAnimation(float delta) {
		// Override because the coin animation must be played only one time
		if (currentAnimation==shakingBagAnimation) {
			super.updateAnimation(delta);
		} else {
			stateTime = stateTime + delta;
			currentFrame = currentAnimation.getKeyFrame(stateTime, false);		
		}
		
	}
	
}
