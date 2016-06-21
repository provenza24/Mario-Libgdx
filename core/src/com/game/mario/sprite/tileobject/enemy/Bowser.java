package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.tilemap.BowserTilemapCollisionHandler;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.enemy.FireFlame;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class Bowser extends AbstractTileObjectEnemy {

	private Animation walkAnimation;
	
	private Animation walkRightAnimation;
	
	private Animation fireAnimation;
	
	private Animation endFireAnimation;

	private float xInitial;
	
	private float yInitial;
	
	private float xTarget;
	
	private float timeBeforeJump;
	
	private float timeBeforeStopping;
	
	private boolean isFiring;
	
	private boolean hasFired;
	
	private float firingTime;
	
	private float stopTime;
	
	private float targetStopTime;
	
	private int direction;
	
	private int chanceToJump = 20;
	
	private int chanceToStop = 20;
	
	private int chanceToFire = 20;
	
	private int fireballHints = 0;
	
	public Bowser(MapObject mapObject) {

		super(mapObject, new Vector2(0.2f, 0.1f));
		
		gravitating = true;
		collidableWithTilemap = true;
		killableByPlayer = false;
		killableByFireball = false;
		bounds = new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		currentAnimation = walkAnimation;
		GRAVITY_COEF = 0.002f;
		
		acceleration.x = 1f;
		xInitial = getX();
		yInitial = getY();
		xTarget = xInitial;
		
		setState(SpriteMoveEnum.WALKING);
		
		tilemapCollisionHandler = new BowserTilemapCollisionHandler();
		
		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.BOWSER;

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 6, spriteSheet.getHeight() / 1);
		
		TextureRegion[] walkFrames = new TextureRegion[2];		
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkAnimation = new Animation(0.3f, walkFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[2];
		walkRightFrames[0] = tmp[0][4];
		walkRightFrames[1] = tmp[0][5];
		walkRightAnimation = new Animation(0.3f, walkRightFrames);
		
		TextureRegion[] firingFrames = new TextureRegion[2];
		firingFrames[0] = tmp[0][2];
		firingFrames[1] = tmp[0][3];
		
		fireAnimation = new Animation(0, tmp[0][2]);		
		endFireAnimation = new Animation(0, tmp[0][3]);
	}

	@Override
	public EnemyTypeEnum getEnemyType() { 
		return EnemyTypeEnum.BOWSER;
	}		
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
											
		super.update(tileMap, camera, deltaTime);		
		
		if (killed) {
			acceleration.x = 0;
		} else {
			if (isVisible()) {
				
				Mario mario = tileMap.getMario();
				if (getX()<mario.getX() || mario.getX()>xInitial-1) {
					setCurrentAnimation(walkRightAnimation);
					if (getX()<xInitial + 0.1f) {					
						acceleration.x = 1f;
						timeBeforeStopping = 0;
						timeBeforeJump = 0;	
						xTarget = xInitial + 0.1f;
					}
				} else {
													
					timeBeforeStopping += deltaTime;
					
					if (state!=SpriteMoveEnum.JUMPING) {
						timeBeforeJump += deltaTime;
					}
					
					if (acceleration.x<0 && getX()<xTarget) {
						acceleration.x = 1f;
						xTarget = xInitial + 0.1f;
					} else if (acceleration.x>0 && getX()>xTarget) {
						setX(xInitial);
						xTarget = xInitial - (3+MathUtils.random(6));
						acceleration.x = -1f;
					}
								
					if (state!=SpriteMoveEnum.JUMPING && timeBeforeJump>1 && !(acceleration.x>0 && xTarget-getX()<1)) {
						int jumpRandomValue = MathUtils.random(chanceToJump-1);
						if (jumpRandomValue==0) {			
							timeBeforeJump = 0;
							acceleration.y=0.08f;
							setState(SpriteMoveEnum.JUMPING);
						}
					}
											
					if (state==SpriteMoveEnum.WALKING && timeBeforeStopping>3) {
						int stopRandomValue = MathUtils.random(chanceToStop-1);
						if (stopRandomValue==0) {
							direction = acceleration.x>0 ? 1 : -1;
							timeBeforeStopping = 0;
							acceleration.x = 0;
							stopTime = 0;		
							targetStopTime = 0.5f + MathUtils.random(2f);
						}
					}
					
					if (acceleration.x==0) {
						stopTime +=deltaTime;
						if (stopTime>targetStopTime) {
							acceleration.x = direction; 
						}
					}
					
					if (isAlive()) {
						
						if (!isFiring) {
							int fireRandomValue = MathUtils.random(chanceToFire-1);
							if (fireRandomValue==0) {
								firingTime = 0;
								isFiring = true;
								setCurrentAnimation(fireAnimation);
								hasFired = false;
							}
						} else {
							
							firingTime += deltaTime;
							
							if (firingTime>3) {
								isFiring = false;
							} else if (firingTime>1f) {
								setCurrentAnimation(walkAnimation);					
							} else if (firingTime>0.5f) {
								setCurrentAnimation(endFireAnimation);
								if (!hasFired) {
									tileMap.getEnemies().add(new FireFlame(this, yInitial + MathUtils.random(2)));
									hasFired = true;
								}
							}
							
							
						}									
					}				
				}
			}
		}
						
	}
	
	public void killByFireball(AbstractSprite fireball) {
		if (fireballHints==0) {
			killed = true;
		} else {
			fireballHints--;
		}
	}	

}
