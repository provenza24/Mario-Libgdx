package com.game.mario.sprite.sfx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class ToadBag extends AbstractSfxSprite {
	
	private Animation shakingBagAnimation;
	
	private Animation openingBagAnimation;
	
	public ToadBag(float x, float y) {		
		super(x ,y);						
		renderingSize = new Vector2(2,2);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());				
		gravitating = false;
		collidableWithTilemap = false;
		moveable = false;		
		currentAnimation = shakingBagAnimation;		
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.CASTLE_BAG;			
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 8, spriteSheet.getHeight() / 1);
		shakingBagAnimation = AnimationBuilder.getInstance().build(tmp, new int[] {0,1,2}, 0.2f);
		shakingBagAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		openingBagAnimation = AnimationBuilder.getInstance().build(tmp, new int[] {3,4,5}, 0.1f);			
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
		if (currentAnimation==shakingBagAnimation) {
			super.updateAnimation(delta);
		} else {
			// Override because the animation must be played only one time when the bag explodes
			stateTime = stateTime + delta;
			currentFrame = currentAnimation.getKeyFrame(stateTime, false);		
		}
		
	}

	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		// TODO Auto-generated method stub
		super.update(tileMap, camera, deltaTime);
	}
	
}
