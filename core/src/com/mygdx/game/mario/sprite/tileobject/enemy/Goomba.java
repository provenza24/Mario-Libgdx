package com.mygdx.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public class Goomba extends AbstractTileObjectSprite {

	private Animation walkAnimation;
		
	public Goomba(MapObject mapObject) {
		
		super(mapObject);								
		offset.x = 0.2f;
		setSize(1-offset.x*2, 1);
		currentAnimation = walkAnimation;				
		acceleration.x = -2;
		gravitating = true;		
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
			
	@Override
	public void initializeAnimations()  {
		spriteSheet = new Texture(Gdx.files.internal("sprites/goomba.png"));		
		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);		
		
		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkAnimation = new Animation(0.15f, walkFrames);
		
		TextureRegion[] killedFrames = new TextureRegion[1];
		killedFrames[0] = tmp[0][2];
		killedAnimation = new Animation(0, killedFrames);		
	}
	
	public void kill() {
		super.kill();
		acceleration.x = 0;
		this.currentAnimation = killedAnimation;
		addAction(ActionFacade.createMoveAction(getX(), getY(), 1f));
	}

}
