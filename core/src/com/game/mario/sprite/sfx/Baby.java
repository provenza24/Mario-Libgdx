package com.game.mario.sprite.sfx;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.animation.AnimationBuilder;

public class Baby extends AbstractSfxSprite {
		
	private static final Map<Integer, Vector2> MACE_HOT_POINTS = new HashMap<Integer, Vector2>();
		
	private Texture maceTexture = new Texture(Gdx.files.internal("sprites/massue.png"));		

	private int currentFrameIdx;

	static {
		MACE_HOT_POINTS.put(0, new Vector2(0.35f,0.4375f));
		MACE_HOT_POINTS.put(1, new Vector2(0.28f,0.4375f));
		MACE_HOT_POINTS.put(2, new Vector2(0.35f,0.4375f));
		MACE_HOT_POINTS.put(3, new Vector2(0.22f,0.4375f));
		MACE_HOT_POINTS.put(4, new Vector2(0.22f,0.4375f));
		MACE_HOT_POINTS.put(5, new Vector2(0.22f,0.4375f));
	}
	
	public Baby(float x, float y) {		
		super(x, y, new Vector2(2,2), new Vector2());												
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = new Texture(Gdx.files.internal("sprites/baby_walk_right.png"));		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 6, spriteSheet.getHeight() / 1);
		currentAnimation = AnimationBuilder.getInstance().build(tmp, 0, 6, 0.1f);					
	}

	@Override
	public void addAppearAction() {			
	}

	@Override
	protected void updateAnimation(float delta) {		
		super.updateAnimation(delta);
		currentFrameIdx = currentAnimation.getKeyFrameIndex(stateTime);
		if (stateTime>=0.6f) {
			stateTime = 0;
		}		
	}
	
	@Override
	public void render(Batch batch) {				
		batch.begin();		
		batch.draw(maceTexture, getX()-2 + MACE_HOT_POINTS.get(currentFrameIdx).x, getY(), 2, 1);
		batch.end();
		super.render(batch);
	}	
	
	
	
}
