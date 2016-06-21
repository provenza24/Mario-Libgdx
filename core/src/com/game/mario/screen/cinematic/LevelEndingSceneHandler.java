package com.game.mario.screen.cinematic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.enums.CastleTypeEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractEnemy;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.sfx.ToadBag;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class LevelEndingSceneHandler extends AbstractCinematicSceneHandler {

	private float timer = 0;

	private int endLevelState = 0;
	
	private boolean updateScrolling = false;
	
	private ArrayList<Vector2> tileToRemove = new ArrayList<Vector2>();
	
	private ToadBag toadBag;
		
	public LevelEndingSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			 Array<IScrollingBackground> scrollingBbackgrounds, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super(mario, tileMap, camera, scrollingBbackgrounds, font, spriteBatch, renderer, stage, batch);
		for (int i = 0; i < tileMap.getTileLayer().getWidth(); i++) {
			for (int j = 0; j < tileMap.getTileLayer().getHeight(); j++) {
				Cell cell = tileMap.getTileLayer().getCell(i, j);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					int id = tile.getId();
					if (id==118) {
						tileToRemove.add(new Vector2(i,j));
					}
				}
			}
		}
		if (tileMap.getWorldType()==WorldTypeEnum.CASTLE) {
			updateEnemies = true;
		}
	}

	protected void renderBackgrounds() {	
		if (tileMap.getWorldType()==WorldTypeEnum.CASTLE && updateScrolling) {
			scrollingBackgrounds.get(0).update();
		}
		scrollingBackgrounds.get(0).render();
		if (scrollingBackgrounds.size>1) {
			if (tileMap.getWorldType()==WorldTypeEnum.CASTLE && updateScrolling) {
				scrollingBackgrounds.get(1).update();
			}
			scrollingBackgrounds.get(1).render();
		}
	}
	
	public void handleScene(float delta) {

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			endLevelState = 5;
			timer = 4;
		}
		
		if (tileMap.getWorldType()==WorldTypeEnum.OVERGROUND) {
			handleOverground(delta);
		} else if (tileMap.getWorldType()==WorldTypeEnum.CASTLE) {			
			handleCastleEnding(delta);						
		}		
		
	}
	
	protected void renderEnemies(float delta) {
		for (AbstractSprite enemy : tileMap.getEnemies()) {
			if (enemy.isVisible()) {
				if (updateEnemies) {
					enemy.update(tileMap, camera.getCamera(), delta);
				}
				enemy.render(renderer.getBatch());
			}				
		}
	}

	private void handleCastleEnding(float delta) {
		
		timer += delta;		
	
		if (!mario.isOnFloor() && endLevelState==0) {
			mario.setCurrentAnimation(mario.getMarioJumpRightAnimation());
			mario.move(delta);
			mario.updateCinematicAnimation(delta);
		} else if (mario.isOnFloor() && endLevelState==0) {					
			endLevelState = 1;
			mario.getAcceleration().x=2;
			mario.setState(SpriteMoveEnum.NO_MOVE);
			mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			mario.setCurrentFrame(mario.getCurrentAnimation().getKeyFrame(0));
			timer = 0;
			for (AbstractEnemy enemy : tileMap.getEnemies()) {
				if (enemy.getEnemyType()==EnemyTypeEnum.BOWSER) {
					enemy.kill();
				}
			}
			toadBag = new ToadBag(150, 2);
			tileMap.getSfxSprites().add(toadBag);					
		} else if (endLevelState==1) {			
			if (timer>0.1f && tileToRemove.size()>0) {
				timer = 0;
					Vector2 tilePos = tileToRemove.get(tileToRemove.size()-1);
					tileMap.removeCell((int)tilePos.x, (int)tilePos.y);
					tileToRemove.remove(tileToRemove.size()-1);								
			} else if (timer>3) {
				endLevelState = 2;
				updateScrolling = true;
			}
		} else if (endLevelState==2) {			
			if (camera.getCamera().position.x<149) {
				camera.getCamera().position.x = camera.getCamera().position.x + 0.05f;
				camera.getCamera().update();
			} else {
				updateScrolling = false;
				endLevelState=3;
				mario.setCurrentFrame(mario.getMarioRunRightAnimation().getKeyFrame(0));				
			}
			if (mario.getX()>149) {
				mario.getAcceleration().x = 0;
				mario.setCurrentFrame(mario.getMarioRunRightAnimation().getKeyFrame(0));
			} else {		
				mario.move(delta);
				mario.updateCinematicAnimation(delta);
			}
		} else if (endLevelState==3 && timer>3) {
			toadBag.setCurrentAnimation(toadBag.getOpeningBagAnimation());
			toadBag.setStateTime(0);
			endLevelState = 4;
		}										
		mario.collideWithTilemap(tileMap);					
		renderCinematicScene(delta);		
	}

	private void handleOverground(float delta) {
						
		timer += delta;

		if (endLevelState == 0) {
			mario.setPosition(tileMap.getFlagTargetPosition() - 0.5f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioFlagRightAnimation());
			mario.setAcceleration(new Vector2());
			mario.setGravitating(false);
			endLevelState = 1;
			timer = 0;
			SoundManager.getSoundManager().stopMusic();			
		} else if (endLevelState == 1 && timer > 1) {
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_FLAGPOLE);
			timer = 0;
			mario.setPosition(mario.getX() + 0.85f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioFlagLeftAnimation());
			mario.setAcceleration(new Vector2());
			mario.setGravitating(true);
			endLevelState = 2;			
			tileMap.getFlag().setGravitating(true);
			tileMap.getFlag().setCollidableWithTilemap(true);
		} else if (endLevelState == 2 && mario.isOnFloor() && timer > 1.5f) {
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_STAGE_CLEAR);
			timer = 0;
			endLevelState = 3;
			mario.setPosition(mario.getX() + 1f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			mario.setAcceleration(new Vector2(2f, 0));
		}

		if (endLevelState == 3) {
			if (mario.getX() > tileMap.getFlagTargetPosition() + 8.35f) {
				mario.setAcceleration(new Vector2());
				endLevelState = 4;
				timer = 0;
				mario.setCurrentAnimation(mario.getMarioVictoryAnimation());
			} else {
				camera.moveCamera(mario);
				// Move scrolling background
				if (Math.floor(camera.getCameraOffset()) == 8) {
					scrollingBackgrounds.get(0).update();					
					if (scrollingBackgrounds.size>1) {
						scrollingBackgrounds.get(1).update();
					}
				}
				scrollingBackgrounds.get(0).render();	
				if (scrollingBackgrounds.size>1) {
					scrollingBackgrounds.get(1).render();
				}
			}
		}

		if (endLevelState == 4 && timer > 3) {
			mario.setAcceleration(new Vector2(2f, 0));
			mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			if (mario.getX() > tileMap.getFlagTargetPosition() + 9.5f) {
				mario.setAcceleration(new Vector2(0, 0));
				mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
				endLevelState = 5;
				timer=0;
			}						
		}
				
		if (endLevelState == 5 && timer > 3) {
			GameManager.getGameManager().setSizeState(mario.getSizeState());
			GameManager.getGameManager().nextLevel();
		} else {
			mario.move(delta);
			mario.collideWithTilemap(tileMap);
			mario.updateCinematicAnimation(delta);
			renderCinematicScene(delta);
			if (endLevelState>=4) {
				renderer.getBatch().begin();				
				renderer.getBatch().draw(tileMap.getEndLevelCastleType()==CastleTypeEnum.SMALL ? ResourcesLoader.CASTLE_DOOR : ResourcesLoader.CASTLE_DOOR_BIG,(int)tileMap.getFlagTargetPosition() + 9, mario.getY(), 2,2);	
				renderer.getBatch().end();
			}

		}				
	}
	
}
