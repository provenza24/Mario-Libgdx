package com.game.mario.sprite.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sprite.AbstractEnemy;
import com.game.mario.sprite.tileobject.enemy.Bowser;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class FireFlame extends AbstractEnemy {

	private static final float WIDTH = 1.625f;
	
	private static final float HEIGHT = 1;
	
	private float yTarget;
	
	public FireFlame(Bowser bowser, float yTarget) {		
		super(bowser.getX()-WIDTH, bowser.getY() + bowser.getHeight() - HEIGHT);
		this.setOffset(new Vector2(0, 0.1f));
		this.setSize(WIDTH, HEIGHT - 2*offset.y);		
		this.renderingSize = new Vector2(WIDTH,HEIGHT);
		this.bounds=new Rectangle(getX(), getY(), getWidth(), getHeight()+offset.y);			
		this.acceleration.x = -3.5f;
		this.yTarget = yTarget;		
		this.acceleration.y = yTarget>getY() ? 0.1f : -0.1f;
		
		this.alive = true;		
		this.killableByPlayer = false;
		this.killableByFireball = false;				
	}
		
	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FIREFLAME, 0, 3, 0.05f);					
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
											
		super.update(tileMap, camera, deltaTime);				
		if (isAlive() && hasReachVerticalTargetPosition()) {
			acceleration.y = 0;
			setY(yTarget);			
		}		
	}
	
	@Override
	protected void updateBounds() {
		bounds.setX(getX()+offset.x);
		bounds.setY(getY()+offset.y);
	}

	@Override
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.FIRE_FLAME;
	}
	
	private boolean hasReachVerticalTargetPosition() {
		return acceleration.y<0 && getY()<=yTarget 
				|| acceleration.y>0 && getY()>=yTarget;
	}
	
	

}
