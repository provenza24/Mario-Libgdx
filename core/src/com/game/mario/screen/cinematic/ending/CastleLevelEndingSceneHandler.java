package com.game.mario.screen.cinematic.ending;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.screen.cinematic.AbstractCinematicSceneHandler;
import com.game.mario.sprite.AbstractEnemy;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.sfx.Toad;
import com.game.mario.sprite.sfx.ToadBag;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.constant.WinConstants;

public class CastleLevelEndingSceneHandler extends AbstractCinematicSceneHandler {

	private float timer = 0;

	private int endLevelState = 0;
	
	private boolean updateScrolling = false;
	
	private ArrayList<Vector2> tileToRemove = new ArrayList<Vector2>();
	
	private ToadBag toadBag;
	
	private boolean bowserWasKilled = true;
	
	public CastleLevelEndingSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
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
		updateEnemies = true;		
	}

	protected void renderBackgrounds() {	
		if (updateScrolling) {
			scrollingBackgrounds.get(0).update();
		}
		scrollingBackgrounds.get(0).render();
		if (scrollingBackgrounds.size>1) {
			if (updateScrolling) {
				scrollingBackgrounds.get(1).update();
			}
			scrollingBackgrounds.get(1).render();
		}
	}
	
	public void handleScene(float delta) {
		
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
					if (!enemy.isKilled()) {
						enemy.kill();
						bowserWasKilled = false;
					} 
				}
			}
			for (AbstractItem item : tileMap.getItems()) {
				if (item.getType()==ItemEnum.HAWK) {
					item.setVisible(false);
				}
			}
			toadBag = new ToadBag(150, 2);
			tileMap.getSfxSprites().add(toadBag);
			if (bowserWasKilled) {
				endLevelState = 2;
				updateScrolling = true;
			}
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
				timer = 0;
				updateScrolling = false;
				endLevelState=3;
				mario.setCurrentFrame(mario.getMarioRunRightAnimation().getKeyFrame(0));				
			}
			if (mario.getX()>149) {
				mario.getAcceleration().x = 0;
				mario.setCurrentFrame(mario.getMarioRunRightAnimation().getKeyFrame(0));
			} else {						
				mario.move(delta);
				if (mario.isOnFloor()) {
					mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
				} else {
					mario.setCurrentAnimation(mario.getMarioJumpRightAnimation());
				}
				mario.updateCinematicAnimation(delta);
			}
		} else if (endLevelState==3 && timer>2) {
			toadBag.setCurrentAnimation(toadBag.getOpeningBagAnimation());
			toadBag.setStateTime(0);
			endLevelState = 4;
		}  else if (endLevelState==4 && timer>2) {			
			tileMap.getSfxSprites().add(new Toad(toadBag.getX() + 0.5f, toadBag.getY()));
			endLevelState = 5;
		}
		mario.collideWithTilemap(tileMap);					
		renderCinematicScene(delta);
		if (endLevelState>=5) {
			spriteBatch.begin();			
			font.draw(spriteBatch, "thank you mario,", WinConstants.WIDTH/2, WinConstants.HEIGHT/2 - 32);
			font.draw(spriteBatch, "but our princess", WinConstants.WIDTH/2, WinConstants.HEIGHT/2 - 52);
			font.draw(spriteBatch, "is in an other castle !!!",  WinConstants.WIDTH/2, WinConstants.HEIGHT/2 - 72);
			spriteBatch.end();
		}
		
	}
	
}
