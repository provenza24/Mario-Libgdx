package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class CastleFirebar extends AbstractTileObjectSprite {

	private Polygon polygon;
	
	public CastleFirebar(MapObject mapObject) {
		super(mapObject, new Vector2());		
		setRenderingSize(3, 0.5f);
		setOrigin(0, 0.25f);
		bounds=new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		polygon = new Polygon(new float[]{getX() + offset.x,getY(),
				getX() + offset.x, getY()+getHeight()-offset.y, 
				getX() + offset.x + getWidth(), getY()+getHeight()-offset.y,
				getX() + offset.x + getWidth(), getY()
				});		
		polygon.setOrigin(getX() + offset.x, getY() + 0.25f);
		moveable = false;
		collidableWithTilemap = false;
		gravitating = false;
		
		float rotation = Float.parseFloat((String)mapObject.getProperties().get("angle"));		
		rotateBy(rotation);				
		polygon.setRotation(getRotation());
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		super.update(tileMap, camera, deltaTime);
		rotateBy(1.75f);				
		polygon.setRotation(getRotation());
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.CASTLE_FIREBAR;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];
		currentAnimation = new Animation(0.08f, animationFrames);						
	}
	
	public void render(Batch batch) {		
		batch.begin();		
		batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());				
		batch.end();								
	}	
	
	@Override
	public boolean overlaps(AbstractSprite sprite) {
		
		Polygon spritePolygon = new Polygon(new float[]{sprite.getBounds().getX(), sprite.getBounds().getY(),
				sprite.getBounds().getX(), sprite.getBounds().getY() + sprite.getBounds().getHeight(),
				sprite.getBounds().getX()+ sprite.getBounds().getWidth(), sprite.getBounds().getY() + sprite.getBounds().getHeight(),
				sprite.getBounds().getX()+ sprite.getBounds().getWidth(), sprite.getBounds().getY()});
		
		return Intersector.overlapConvexPolygons(spritePolygon, polygon);				
	}

}
