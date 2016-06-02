package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class Bowser extends AbstractEnemy {

	private Animation walkAnimation;

	public Bowser(MapObject mapObject) {

		super(mapObject);
		offset.x = 0.2f;
		offset.y = 0.1f;
		setSize(2 - 2*offset.x, 2.375f - offset.y);
		setRenderingSize(2, 2.375f);
		setY(getY()-1+getHeight());
		gravitating = true;
		collidableWithTilemap = true;
		bounds = new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		currentAnimation = walkAnimation;
		GRAVITY_COEF = 0.002f;
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.BOWSER;

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);
		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkAnimation = new Animation(0.3f, walkFrames);

	}

	@Override
	public EnemyTypeEnum getEnemyType() { 
		return EnemyTypeEnum.BOWSER;
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		
		if (Gdx.input.isKeyJustPressed(Keys.B)) {
			acceleration.y=0.08f;
			setState(SpriteStateEnum.JUMPING);
		}
		super.update(tileMap, camera, deltaTime);
		
	}

}
