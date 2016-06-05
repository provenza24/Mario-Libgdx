package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.ResourcesLoader;

public class PiranhaPlant extends AbstractEnemy {
			
	private Mario mario;
		
	private static final float TIME_TO_WAIT = 1;
	
	private static final float HEIGHT = (float)50/32;
	
	private static final float ACCELERATION_Y = 0.02f;
	
	private static final int NB_MOVE = (int)((HEIGHT + 0.45) /  ACCELERATION_Y);
	
	private static float HALF_WIDTH = 0.5f;
	
	private double move = 0;
	
	private float waitTime = -1;
		
	
	private Vector2 pipePosition;
			
	public PiranhaPlant(MapObject mapObject, Mario mario) {
		super(mapObject, new Vector2(0.1f,0.1f));	
		setGravitating(false);
		setCollidableWithTilemap(false);
		setAcceleration(new Vector2(0,ACCELERATION_Y));
		pipePosition = new Vector2((int)getX(), (int)getY());
		bounds=new Rectangle(getX()+offset.x, getY(), getWidth(), getHeight());
		this.mario = mario;
	}

	@Override
	public EnemyTypeEnum getEnemyType() {	
		return EnemyTypeEnum.PIRANHA_PLANT;
	}

	@Override
	public void initializeAnimations() {
		
		spriteSheet = ResourcesLoader.PIRANHA_PLANT;

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 2,
				spriteSheet.getHeight() / 1);

		TextureRegion[] frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		currentAnimation = new Animation(0.15f, frames);		
	}
	
	@Override
	public void move(float deltaTime) {
		
		if (!isNextTo()) {
			if (move==0 && waitTime!=-1 && waitTime<TIME_TO_WAIT) {
				waitTime += deltaTime;
			} else {
				super.move(deltaTime);
				move++;
				if (move==NB_MOVE) {
					waitTime = 0;
					acceleration.y = -acceleration.y;
					move = 0;
				}
			}
		}		
	}
	
	private boolean isNextTo() {
		return Math.abs((getX()+HALF_WIDTH) - (mario.getX()+mario.getWidth()/2+mario.getOffset().x)) <=1.6f 
				&& move==0 
				&& acceleration.y>0;
	}
	
	@Override
	public boolean collideMario(Mario mario) {
		return false;
	}

	@Override
	public void render(Batch batch) {		
		
		super.render(batch);		
		batch.begin();				
		batch.draw(ResourcesLoader.PIPE_DOWN,pipePosition.x, pipePosition.y, 2,2);	
		batch.end();
	}
	
	@Override
	public void killByFireball(AbstractSprite fireball) {
		super.kill();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
	}
	
	

}
