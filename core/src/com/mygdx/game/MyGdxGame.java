package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.mario.Mario;

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;

	private OrthogonalTiledMapRenderer renderer;

	private OrthographicCamera camera;

	private Batch batch;
	
	private Animation currentAnimation; 
	
	private TextureRegion currentFrame;	
	
	private Mario mario;
	
	private float cameraOffset = 0;
	
	@Override
	public void create() {
		
		mario = new Mario();
		
		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
		map = new TmxMapLoader().load("level_1_1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 15);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

	}

	@Override
	public void render() {
						
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mario.setMarioStateTime(mario.getMarioStateTime() + Gdx.graphics.getDeltaTime());
				
		handleInput();
		
		moveCamera();
									
		renderer.setView(camera);
		renderer.render();									
		renderMario();       

	}

	private void moveCamera() {
		
		float move = mario.getPosition().x - mario.getOldPosition().x;
		if (cameraOffset<8) {
			cameraOffset = cameraOffset + move;									
		} else {
			if (move>0) {
				camera.position.x = camera.position.x + move;
			} else {
			cameraOffset = cameraOffset + move;				
			}
		}	
		camera.update();
	}

	private void renderMario() {		       
        batch = renderer.getBatch();
        batch.begin();                
        batch.draw(currentFrame, mario.getPosition().x, mario.getPosition().y, 1, 1);        
        batch.end();
		
	}

	private float handleInput() {

		mario.storeOldPosition();
				
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (mario.getDirection()==DirectionEnum.LEFT) {
				// Sliding
				mario.decelerate();
				if (mario.getAcceleration().x==0) {
					mario.setDirection(DirectionEnum.RIGHT);
				}
			} else {
				mario.accelerate();
				mario.setDirection(DirectionEnum.RIGHT);
			}													
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (mario.getDirection()==DirectionEnum.RIGHT) {
				// Sliding
				mario.decelerate();
				if (mario.getAcceleration().x==0) {
					mario.setDirection(DirectionEnum.LEFT);
				}
			} else {
				mario.accelerate();
				mario.setDirection(DirectionEnum.LEFT);
			}	
		} else {
			mario.decelerate();
		}
		
		float move = Gdx.graphics.getDeltaTime() * mario.getAcceleration().x;
		move = mario.getDirection()==DirectionEnum.LEFT ? -move : move;
		mario.getPosition().x = mario.getPosition().x + move;
		
		currentAnimation = mario.getDirection()==DirectionEnum.RIGHT ? mario.getMarioRunRightAnimation() : mario.getMarioRunLeftAnimation();		
		if (move==0) {
			currentFrame = currentAnimation.getKeyFrame(0, false);
		} else {				
			currentFrame = currentAnimation.getKeyFrame(mario.getMarioStateTime(), true);
		}
		
		return move;				
	}
}
