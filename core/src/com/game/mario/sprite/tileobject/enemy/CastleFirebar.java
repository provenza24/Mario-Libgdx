package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class CastleFirebar extends AbstractTileObjectEnemy {

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
		killableByPlayer = false;
		killableByFireball = false;
		explodeFireball = false;
		
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
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.CASTLE_FIREBAR, 0, 3, 0.08f);							
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

	@Override
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.CASTLE_FIREBAR;
	}

}
