package com.game.mario.sprite.tileobject.mario;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.CollisionPoint;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.item.TransferItem;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class Mario extends AbstractTileObjectSprite {
		
	private static final float X_OFFSET = 0.05f;
	
	private static final float Y_OFFSET = 0.1f;
	
	private static final float COLLISION_X_CORRECTIF = 10e-5F;
		
	private static final float ACCELERATION_MAX = 5f; // 7.5f;

	private static final float ACCELERATION_MAX_SPEEDUP = 8.5f;

	private static final float DECELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF_SPEEDUP = 0.2f;

	private Animation animations[][];

	private Animation marioRunRightAnimation;

	private Animation marioRunLeftAnimation;

	private Animation marioSlideRightAnimation;

	private Animation marioSlideLeftAnimation;

	private Animation marioJumpLeftAnimation;

	private Animation marioJumpRightAnimation;

	private Animation marioDeathAnimation;

	private Animation marioGrowDownRightAnimation;

	private Animation marioGrowDownLeftAnimation;

	private Animation marioGrowUpRightAnimation;

	private Animation marioGrowUpLeftAnimation;

	private Animation marioFlagRightAnimation;

	private Animation marioFlagLeftAnimation;

	private Animation marioVictoryAnimation;

	private SpriteStateEnum previousState;

	private int jumpTimer;

	private boolean canInitiateJump;

	private boolean canJumpHigher;

	private boolean invincible;

	private float invincibleDuration;

	private float deathNoMoveDuration;

	private boolean growingUp;

	private boolean growingDown;

	private boolean inTransfer;

	private TransferItem transferItem;

	private List<AbstractSprite> fireballs;
	
	private Vector2 move = new Vector2();	
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;

	public Mario(MapObject mapObject) {
		super(mapObject);
		offset.x = X_OFFSET;
		offset.y = Y_OFFSET;
		setSize(1 - 2*offset.x, 1 - offset.y);
		renderingSize = new Vector2(1, 1);
		stateTime = 0f;
		jumpTimer = 0;
		onFloor = true;
		canInitiateJump = true;
		canJumpHigher = true;
		gravitating = true;
		direction = DirectionEnum.RIGHT;
		state = SpriteStateEnum.NO_MOVE;
		previousState = SpriteStateEnum.NO_MOVE;
		bounds = new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		sizeState = GameManager.getGameManager().getSizeState();
		changeSizeState(sizeState);
		invincible = false;
		invincibleDuration = 0;
		alive = true;
		deathNoMoveDuration = 0;
		fireballs = new ArrayList<AbstractSprite>();			
	}

	public void changeSizeState(int i) {
		sizeState = i;
		if (i == 0) {
			setSize(1 - 2*offset.x, 1 - offset.y);
			setRenderingSize(1, 1);
			bounds.setWidth(1 - 2*offset.x);
			bounds.setHeight(1 - offset.y);
		} else {
			setSize(1 - 2*offset.x, 2 - offset.y);
			setRenderingSize(1, 2);
			bounds.setWidth(1 - 2*offset.x);
			bounds.setHeight(2 - offset.y);
		}		
		updateBounds();		
		marioRunRightAnimation = animations[i][0];
		marioRunLeftAnimation = animations[i][1];
		marioSlideRightAnimation = animations[i][2];
		marioSlideLeftAnimation = animations[i][3];
		marioJumpRightAnimation = animations[i][4];
		marioJumpLeftAnimation = animations[i][5];
		marioDeathAnimation = animations[i][6];
		marioFlagRightAnimation = animations[i][7];
		marioFlagLeftAnimation = animations[i][8];
		marioVictoryAnimation = animations[i][9];
	}

	@Override
	public void initializeAnimations() {
		initializeAnimation(ResourcesLoader.MARIO_SMALL, 0);
		initializeAnimation(ResourcesLoader.MARIO_BIG, 1);
		initializeAnimation(ResourcesLoader.MARIO_FLOWER, 2);

		Texture growDownRightTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-right.png"));
		TextureRegion[][] tmp = TextureRegion.split(growDownRightTexture, growDownRightTexture.getWidth() / 3,
				growDownRightTexture.getHeight() / 1);
		TextureRegion[] frames = new TextureRegion[2];
		frames[0] = tmp[0][1];
		frames[1] = tmp[0][0];
		marioGrowDownRightAnimation = new Animation(0.15f, frames);

		Texture growDownLeftTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-left.png"));
		tmp = TextureRegion.split(growDownLeftTexture, growDownLeftTexture.getWidth() / 3,
				growDownLeftTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][1];
		frames[1] = tmp[0][0];
		marioGrowDownLeftAnimation = new Animation(0.15f, frames);

		Texture growUpLeftTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-left.png"));
		tmp = TextureRegion.split(growUpLeftTexture, growUpLeftTexture.getWidth() / 3,
				growUpLeftTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		marioGrowUpLeftAnimation = new Animation(0.15f, frames);

		Texture growUpRightTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-right.png"));
		tmp = TextureRegion.split(growUpRightTexture, growUpRightTexture.getWidth() / 3,
				growUpRightTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		marioGrowUpRightAnimation = new Animation(0.15f, frames);
	}

	private void initializeAnimation(Texture texture, int i) {

		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 14, texture.getHeight() / 1);

		TextureRegion[] marioRunRightFrames = new TextureRegion[3];
		marioRunRightFrames[0] = tmp[0][0];
		marioRunRightFrames[1] = tmp[0][1];
		marioRunRightFrames[2] = tmp[0][2];
		marioRunRightAnimation = new Animation(0.05f, marioRunRightFrames);

		TextureRegion[] marioRunLeftFrames = new TextureRegion[3];
		marioRunLeftFrames[0] = tmp[0][5];
		marioRunLeftFrames[1] = tmp[0][6];
		marioRunLeftFrames[2] = tmp[0][7];
		marioRunLeftAnimation = new Animation(0.05f, marioRunLeftFrames);

		TextureRegion[] marioSlideRightFrames = new TextureRegion[1];
		marioSlideRightFrames[0] = tmp[0][9];
		marioSlideRightAnimation = new Animation(1, marioSlideRightFrames);

		TextureRegion[] marioSlideLeftFrames = new TextureRegion[1];
		marioSlideLeftFrames[0] = tmp[0][4];
		marioSlideLeftAnimation = new Animation(1, marioSlideLeftFrames);

		TextureRegion[] marioJumpRightFrames = new TextureRegion[1];
		marioJumpRightFrames[0] = tmp[0][3];
		marioJumpRightAnimation = new Animation(1, marioJumpRightFrames);

		TextureRegion[] marioJumpLeftFrames = new TextureRegion[1];
		marioJumpLeftFrames[0] = tmp[0][8];
		marioJumpLeftAnimation = new Animation(1, marioJumpLeftFrames);

		TextureRegion[] marioDeathFrames = new TextureRegion[1];
		marioDeathFrames[0] = tmp[0][10];
		marioDeathAnimation = new Animation(1, marioDeathFrames);

		TextureRegion[] marioFlagRightFrames = new TextureRegion[1];
		marioFlagRightFrames[0] = tmp[0][11];
		marioFlagRightAnimation = new Animation(1, marioFlagRightFrames);

		TextureRegion[] marioFlagLeftFrames = new TextureRegion[1];
		marioFlagLeftFrames[0] = tmp[0][12];
		marioFlagLeftAnimation = new Animation(1, marioFlagLeftFrames);

		TextureRegion[] marioVictoryFrames = new TextureRegion[1];
		marioVictoryFrames[0] = tmp[0][13];
		marioVictoryAnimation = new Animation(1, marioVictoryFrames);

		if (animations == null) {
			animations = new Animation[3][10];
		}
		animations[i] = new Animation[10];
		animations[i][0] = marioRunRightAnimation;
		animations[i][1] = marioRunLeftAnimation;
		animations[i][2] = marioSlideRightAnimation;
		animations[i][3] = marioSlideLeftAnimation;
		animations[i][4] = marioJumpRightAnimation;
		animations[i][5] = marioJumpLeftAnimation;
		animations[i][6] = marioDeathAnimation;
		animations[i][7] = marioFlagRightAnimation;
		animations[i][8] = marioFlagLeftAnimation;
		animations[i][9] = marioVictoryAnimation;

	}

	public void accelerate(boolean accelerationKeyHold) {
		if (this.acceleration.x < (accelerationKeyHold ? ACCELERATION_MAX_SPEEDUP : ACCELERATION_MAX)) {
			this.acceleration.x = this.acceleration.x
					+ (accelerationKeyHold ? ACCELERATION_COEF_SPEEDUP : ACCELERATION_COEF);
		}

		if (accelerationKeyHold == false && this.acceleration.x > ACCELERATION_MAX) {
			this.acceleration.x = ACCELERATION_MAX;
		}
	}

	public void decelerate(float rate) {
		if (this.acceleration.x > 0) {
			this.acceleration.x = this.acceleration.x - DECELERATION_COEF * rate;
		}
		if (this.acceleration.x < 0) {
			this.acceleration.x = 0;
		}
	}

	public Animation getMarioJumpLeftAnimation() {
		return marioJumpLeftAnimation;
	}

	public void setMarioJumpLeftAnimation(Animation marioJumpLeftAnimation) {
		this.marioJumpLeftAnimation = marioJumpLeftAnimation;
	}

	public Animation getMarioJumpRightAnimation() {
		return marioJumpRightAnimation;
	}

	public void setMarioJumpRightAnimation(Animation marioJumpRightAnimation) {
		this.marioJumpRightAnimation = marioJumpRightAnimation;
	}

	public void storeOldPosition() {
		super.storeOldPosition();
		previousState = state;		
	}

	public void setStateIfNotJumping(SpriteStateEnum pstate) {
		if (state != SpriteStateEnum.FALLING && state != SpriteStateEnum.JUMPING) {
			this.state = pstate;
		}
	}

	

	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		move(deltaTime);
		collideWithTilemap(tileMap);
		updateAnimation(deltaTime);
		updateInvincibleStatus(deltaTime);
		updateAliveStatus();
	}

	private void updateAliveStatus() {
		setAlive(getY() >= -getHeight());
	}

	private void updateInvincibleStatus(float deltaTime) {
		if (isInvincible() && invincibleDuration < 3) {
			invincibleDuration += deltaTime;
		} else {
			invincible = false;
			invincibleDuration = 0;
		}
	}		

	public void checkBottomMapCollision(TmxMap tilemap) {
		
		reinitMapCollisionEvent();
		getMapCollisionEvent().reinitCollisionPoints();
		
		Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x, getY());		
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x, getY());
		
		int x = (int) leftBottomCorner.x;
		int y = (int) leftBottomCorner.y;
		boolean isCollision = tilemap.isCollisioningTileAt(x, y);		
		getMapCollisionEvent().setCollidingBottomLeft(isCollision);
	
		x = (int) rightBottomCorner.x;
		y = (int) rightBottomCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);		
		getMapCollisionEvent().setCollidingBottomRight(getMapCollisionEvent().isCollidingBottom() || isCollision);
	}
	
	public void checkMapCollision(TmxMap tilemap) {
		
		reinitMapCollisionEvent();
		getMapCollisionEvent().reinitCollisionPoints();		

		Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x, getY());
		Vector2 leftTopCorner = new Vector2(getX() + getOffset().x, getY() + getHeight());
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x, getY());
		Vector2 rightTopCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + getHeight());

		int x = (int) leftBottomCorner.x;
		int y = (int) leftBottomCorner.y;
		boolean isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingBottomLeft(isCollision);
		if (isCollision) {
			getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(leftBottomCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}

		x = (int) leftTopCorner.x;
		y = (int) leftTopCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingTopLeft(isCollision);
		if (isCollision) {
			getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(leftTopCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}

		x = (int) rightBottomCorner.x;
		y = (int) rightBottomCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingBottomRight(isCollision);
		if (isCollision) {
			getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(rightBottomCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}

		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingTopRight(isCollision);
		if (isCollision) {
			getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(rightTopCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}

		if (sizeState > 0) {
			Vector2 rightMiddle = new Vector2(getX() + getWidth() + getOffset().x, getY() + getHeight() / 2);
			x = (int) rightMiddle.x;
			y = (int) rightMiddle.y;
			isCollision = tilemap.isCollisioningTileAt(x, y);
			getMapCollisionEvent().setCollidingMiddleRight(isCollision);
			if (isCollision) {
				getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(rightMiddle, new TmxCell(tilemap.getTileAt(x, y), x, y)));
			}

			Vector2 leftMiddle = new Vector2(getX() + getOffset().x, getY() + getHeight() / 2);
			x = (int) leftMiddle.x;
			y = (int) leftMiddle.y;
			isCollision = tilemap.isCollisioningTileAt(x, y);
			getMapCollisionEvent().setCollidingMiddleLeft(isCollision);
			if (isCollision) {
				getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(leftMiddle, new TmxCell(tilemap.getTileAt(x, y), x, y)));
			}
		}
	}

	public void collideWithTilemap(TmxMap tileMap) {
				
		
		collidingCells = new ArrayList<TmxCell>();
		
		boolean onFloorCorrection = false;
		move = new Vector2(getX() - getOldPosition().x, getY() - getOldPosition().y);
				
		checkBottomMapCollision(tileMap);		
		
		if (oldAcceleration.y == 0 && getMapCollisionEvent().isCollidingBottom()) {
			// Mario is on a plateform and is still on it
			if (state==SpriteStateEnum.FALLING) {
				state=SpriteStateEnum.NO_MOVE;
			}
			onFloor = true;
			setY((int) getY() + 1);
			oldPosition.y = getY();
			acceleration.y = 0;
			onFloorCorrection = true;
		}
		
		move = new Vector2(getX() - getOldPosition().x, getY() - getOldPosition().y);		
		Vector2 newPosition = new Vector2(getX(), getY());
		
		checkMapCollision(tileMap);				
								
		
		if (getMapCollisionEvent().getCollisionPoints().size()>0) {
												
			int i=0;
			
			while (getMapCollisionEvent().getCollisionPoints().size()>0) {
			
				for (CollisionPoint collisionPoint : getMapCollisionEvent().getCollisionPoints()) {
					
					if (move.y==0 && move.x!=0) {
						newPosition.x = move.x>0 ? (int) (getX() + offset.x) + offset.x - COLLISION_X_CORRECTIF : (int) (getX() + getWidth() + offset.x) - offset.x + COLLISION_X_CORRECTIF;						
						acceleration.x = 0;	
						if (state!=SpriteStateEnum.FALLING && state!=SpriteStateEnum.JUMPING) {
							state = SpriteStateEnum.NO_MOVE;
						}
					}
					
					if (move.y<0 && move.x==0) {						
						newPosition.y = (int) getY() + 1f;
						acceleration.y = 0;
						state = SpriteStateEnum.NO_MOVE;
						onFloor = true;					
					}
					
					if (move.y>0 && move.x==0) {
						
						addCollidingCell(collisionPoint.getCell());
						
						newPosition.y = (int) getY();
						acceleration.y = 10e-5F;						
						state = SpriteStateEnum.FALLING;
																						
					}
					
					if (move.x>0 && move.y>0) {
											
						if (mapCollisionEvent.isBlockedRight()) {
							newPosition.x = (int) (getX() + offset.x) + offset.x - COLLISION_X_CORRECTIF;						
							acceleration.x = 0;									
						} else {
							float xDelta = collisionPoint.getPoint().x - collisionPoint.getCell().getX();
							float yDelta = collisionPoint.getPoint().y - collisionPoint.getCell().getY();
																		
							if (xDelta>yDelta) {
								addCollidingCell(collisionPoint.getCell());
								newPosition.y = (int) getY();
								acceleration.y = 10e-5F;								
								if (state!=SpriteStateEnum.FALLING && state!=SpriteStateEnum.JUMPING) {
									state = SpriteStateEnum.NO_MOVE;
									onFloor = true;
								}							
							} else {								
								newPosition.x = (int) (getX() + offset.x) + offset.x - COLLISION_X_CORRECTIF;						
								acceleration.x = 0;					
							}
						}						
						
					}
					
					if (move.x>0 && move.y<0) {
					
						if (mapCollisionEvent.isBlockedRight()) {
							newPosition.x = (int) (getX() + offset.x) + offset.x - COLLISION_X_CORRECTIF;						
							acceleration.x = 0;									
						} else {	
							float xDelta = collisionPoint.getPoint().x - collisionPoint.getCell().getX();
							float yDelta = (collisionPoint.getCell().getY() + 1) - collisionPoint.getPoint().y;
							if (xDelta>yDelta) {				
								newPosition.y = (int) getY() + 1f;						
								acceleration.y = 0;
								onFloor = true;
								state = SpriteStateEnum.NO_MOVE;
							} else {
								newPosition.x = (int) (getX() + offset.x) + offset.x - COLLISION_X_CORRECTIF;						
								acceleration.x = 0;										
							}
						}
												
					}
					
					if (move.x<0 && move.y<0) {	
												
						if (mapCollisionEvent.isBlockedLeft()) {						
							newPosition.x = (int) (getX() + getWidth() + offset.x) - offset.x + COLLISION_X_CORRECTIF;					
							acceleration.x = 0;				
							
						} else {
							float xDelta = (collisionPoint.getCell().getX()+1) - collisionPoint.getPoint().x;
							float yDelta = (collisionPoint.getCell().getY()+1) - collisionPoint.getPoint().y;
							if (xDelta>yDelta) {
								newPosition.y = (int) getY() + 1f;
								acceleration.y = 0;
								onFloor = true;
								state = SpriteStateEnum.NO_MOVE;
							} else {
								newPosition.x = (int) (getX() + getWidth() + offset.x) - offset.x + COLLISION_X_CORRECTIF;					
								acceleration.x = 0;					
							}
						}												
					}
					
					if (move.x<0 && move.y>0) {
						
						if (mapCollisionEvent.isBlockedLeft()) {
							newPosition.x = (int) (getX() + getWidth() + offset.x) - offset.x + COLLISION_X_CORRECTIF;						
							acceleration.x = 0;			
						} else {
							float xDelta = (collisionPoint.getCell().getX()+1) - collisionPoint.getPoint().x;
							float yDelta = collisionPoint.getPoint().y - (collisionPoint.getCell().getY());
							if (xDelta>yDelta) {
								addCollidingCell(collisionPoint.getCell());
								newPosition.y = (int) getY();
								acceleration.y = 10e-5F;
								
								if (state!=SpriteStateEnum.FALLING && state!=SpriteStateEnum.JUMPING) {
									state = SpriteStateEnum.NO_MOVE;
									onFloor = true;
								}
							} else {
									newPosition.x = (int) (getX() + getWidth() + offset.x) - offset.x + COLLISION_X_CORRECTIF;						
								acceleration.x = 0;					
							}
						}
						
					}
														
				}
				setX(newPosition.x);
				setY(newPosition.y);
				checkMapCollision(tileMap);
				i++;
				if (i>10) {
					System.out.println("Erreur de collision ?"+i);
				}
				
			}	
			// The collision has been handled, now fix player acceleration
			if (move.x<0) {
				Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x - 1, getY());
				Vector2 leftTopCorner = new Vector2(getX() + getOffset().x - 1, getY() + getHeight());				
				int x = (int) leftBottomCorner.x;
				int y = (int) leftBottomCorner.y;
				boolean isCollision = tileMap.isCollisioningTileAt(x, y);
				if (!isCollision) {
					x = (int) leftTopCorner.x;
					y = (int) leftTopCorner.y;
					isCollision = tileMap.isCollisioningTileAt(x, y);
					if (!isCollision && sizeState>0) {
						Vector2 leftMiddle = new Vector2(getX() + getOffset().x - 1, getY() + getHeight() / 2);
						x = (int) leftMiddle.x;
						y = (int) leftMiddle.y;
						isCollision = tileMap.isCollisioningTileAt(x, y);
					}
				}				
				acceleration.x = isCollision ? 0 : oldAcceleration.x;
			} else {
				Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x + 1, getY());
				Vector2 rightTopCorner = new Vector2(getX() + getWidth() + getOffset().x + 1, getY() + getHeight());			
				int x = (int) rightBottomCorner.x;
				int y = (int) rightBottomCorner.y;
				boolean isCollision = tileMap.isCollisioningTileAt(x, y);
				if (!isCollision) {
					x = (int) rightTopCorner.x;
					y = (int) rightTopCorner.y;
					isCollision = tileMap.isCollisioningTileAt(x, y);
					if (!isCollision && sizeState>0) {
						Vector2 rightMiddle = new Vector2(getX() + getWidth() + getOffset().x + 1, getY() + getHeight() / 2);
						x = (int) rightMiddle.x;
						y = (int) rightMiddle.y;
						isCollision = tileMap.isCollisioningTileAt(x, y);
					}
				}				
				acceleration.x = isCollision ? 0 : oldAcceleration.x;
			}										
		}  else {
			if (move.y < 0 && !onFloorCorrection) {				
				setState(SpriteStateEnum.FALLING);
				onFloor = false;
			}
		}										
		
		bounds.setX(getX()+offset.x);
		bounds.setY(getY());
	}

	public Animation getMarioVictoryAnimation() {
		return marioVictoryAnimation;
	}

	public void setMarioVictoryAnimation(Animation marioVictoryAnimation) {
		this.marioVictoryAnimation = marioVictoryAnimation;
	}

	public void setDeathAnimation() {
		currentAnimation = marioDeathAnimation;
		currentFrame = currentAnimation.getKeyFrame(0, false);
		acceleration.x = 0;
		acceleration.y = 0.2f;
	}

	public void setGrowDownAnimation() {
		currentAnimation = direction == DirectionEnum.RIGHT ? marioGrowDownRightAnimation : marioGrowDownLeftAnimation;
		currentFrame = currentAnimation.getKeyFrame(1, false);
	}

	public void setGrowUpAnimation() {
		currentAnimation = direction == DirectionEnum.RIGHT ? marioGrowUpRightAnimation : marioGrowUpLeftAnimation;
		currentFrame = currentAnimation.getKeyFrame(1, false);
	}

	public void updateCinematicAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}

	public void updateAnimation(float delta) {

		stateTime = stateTime + delta;

		float xMove = getX() - getOldPosition().x;

		if (getState() != SpriteStateEnum.JUMPING && getState() != SpriteStateEnum.FALLING) {
			if (xMove == 0) {
				if (getState() == SpriteStateEnum.SLIDING_LEFT) {
					setDirection(DirectionEnum.RIGHT);
				} else if (getState() == SpriteStateEnum.SLIDING_RIGHT) {
					setDirection(DirectionEnum.LEFT);
				}
				setState(SpriteStateEnum.NO_MOVE);
				currentAnimation = direction == DirectionEnum.RIGHT ? marioRunRightAnimation : marioRunLeftAnimation;
				currentFrame = currentAnimation.getKeyFrame(0, false);
			} else {
				currentAnimation = state == SpriteStateEnum.RUNNING_LEFT ? marioRunLeftAnimation
						: state == SpriteStateEnum.RUNNING_RIGHT ? marioRunRightAnimation
								: state == SpriteStateEnum.SLIDING_LEFT ? marioSlideLeftAnimation
										: state == SpriteStateEnum.SLIDING_RIGHT ? marioSlideRightAnimation
												: direction == DirectionEnum.RIGHT ? marioRunRightAnimation
														: marioRunLeftAnimation;
				currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			}
		}

		if (!onFloor) {
			currentAnimation = direction == DirectionEnum.RIGHT ? marioJumpRightAnimation : marioJumpLeftAnimation;
			currentFrame = currentAnimation.getKeyFrame(0, false);
		}
	}

	public boolean canInitiateJump() {
		return canInitiateJump;
	}

	public void setCanInitiateJump(boolean canInitiateJump) {
		this.canInitiateJump = canInitiateJump;
	}

	public boolean canJumpHigher() {
		return canJumpHigher;
	}

	public void setCanJumpHigher(boolean canJumpHigher) {
		this.canJumpHigher = canJumpHigher;
	}

	public SpriteStateEnum getState() {
		return state;
	}

	public void setState(SpriteStateEnum pstate) {
		this.state = pstate;
	}

	public int getJumpTimer() {
		return jumpTimer;
	}

	public void incJumpTimer() {
		this.jumpTimer++;
	}

	public void setJumpTimer(int jumpTimer) {
		this.jumpTimer = jumpTimer;
	}

	public Animation getMarioRunRightAnimation() {
		return marioRunRightAnimation;
	}

	public void setMarioRunRightAnimation(Animation marioRunRightAnimation) {
		this.marioRunRightAnimation = marioRunRightAnimation;
	}

	public SpriteStateEnum getPreviousState() {
		return previousState;
	}

	public void setPreviousState(SpriteStateEnum previousState) {
		this.previousState = previousState;
	}

	public int getSizeState() {
		return sizeState;
	}

	public void setSizeState(int sizeState) {
		this.sizeState = sizeState;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void dispose() {
		spriteSheet.dispose();
	}

	@Override
	public void render(Batch batch) {
		if (isInvincible()) {
			batch.setColor(1, 1, 1, 0.5f);
			super.render(batch);
			batch.setColor(1, 1, 1, 1);
		} else {
			super.render(batch);
		}
	}

	public float getDeathNoMoveDuration() {
		return deathNoMoveDuration;
	}

	public void setDeathNoMoveDuration(float deathNoMoveDuration) {
		this.deathNoMoveDuration = deathNoMoveDuration;
	}

	public boolean isGrowingUp() {
		return growingUp;
	}

	public void setGrowingUp(boolean growingUp) {
		this.growingUp = growingUp;
	}

	public boolean isGrowingDown() {
		return growingDown;
	}

	public void setGrowingDown(boolean growingDown) {
		this.growingDown = growingDown;
	}

	public boolean isGrowing() {
		return isGrowingDown() || isGrowingUp();
	}

	public List<AbstractSprite> getFireballs() {
		return fireballs;
	}

	public void setFireballs(List<AbstractSprite> fireballs) {
		this.fireballs = fireballs;
	}

	public Animation getMarioFlagRightAnimation() {
		return marioFlagRightAnimation;
	}

	public void setMarioFlagRightAnimation(Animation marioFlagRightAnimation) {
		this.marioFlagRightAnimation = marioFlagRightAnimation;
	}

	public Animation getMarioFlagLeftAnimation() {
		return marioFlagLeftAnimation;
	}

	public void setMarioFlagLeftAnimation(Animation marioFlagLeftAnimation) {
		this.marioFlagLeftAnimation = marioFlagLeftAnimation;
	}

	public boolean isInTransfer() {
		return inTransfer;
	}

	public void setInTransfer(boolean inTransfer) {
		this.inTransfer = inTransfer;
	}

	public void transfer(TmxMap tilemap, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds,
			SpriteBatch spriteBatch) {
		setOnFloor(true);
		setState(SpriteStateEnum.NO_MOVE);
		setAcceleration(new Vector2(0, 0));
		setDirection(DirectionEnum.RIGHT);
		setX(transferItem.getTransferPosition().x);
		setY(transferItem.getTransferPosition().y);
		camera.setCameraOffset(2f);
		camera.getCamera().position.x = transferItem.getTransferPosition().x + 6;
		camera.getCamera().update();
		camera.setScrollable(transferItem.isScrollableCamera());
		scrollingBackgrounds.get(0).changeImage(transferItem.getBackgroundTypesEnum().get(0));
		if (transferItem.getBackgroundTypesEnum().size > 1) {
			if (scrollingBackgrounds.size > 1) {
				scrollingBackgrounds.get(1).changeImage(transferItem.getBackgroundTypesEnum().get(1));
			} else {
				scrollingBackgrounds.add(new LeftScrollingBackground(this, spriteBatch,
						transferItem.getBackgroundTypesEnum().get(1), 16));
			}
		} else {
			if (scrollingBackgrounds.size > 1) {
				scrollingBackgrounds.removeIndex(1);
			}
		}

		tilemap.setWorldType(transferItem.getWorldTypeEnum());
		
		SoundManager.getSoundManager().stopMusic();
		SoundManager.getSoundManager().setCurrentMusic(transferItem.getMusic());
		SoundManager.getSoundManager().playMusic(false);
	}

	public TransferItem getTransferItem() {
		return transferItem;
	}

	public void setTransferItem(TransferItem transferItem) {
		this.transferItem = transferItem;
	}

	public Vector2 getMove() {
		return move;
	}

	public void setMove(Vector2 move) {
		this.move = move;
	}			
		
}
