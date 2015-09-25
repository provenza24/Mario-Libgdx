package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

	Batch batch;
	
	TextureRegion currentFrame;	
	
	Mario mario;
	
	float cameraOffset = 0;
	
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
						
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mario.setMarioStateTime(mario.getMarioStateTime() + Gdx.graphics.getDeltaTime());
		
		float move = handleInput();
						
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
				
		renderer.setView(camera);
		renderer.render();									
		renderMario();       

	}

	private void renderMario() {
		currentFrame = mario.getMarioRunRightAnimation().getKeyFrame(mario.getMarioStateTime(), true);        
        batch = renderer.getBatch();
        batch.begin();        
        batch.draw(currentFrame, mario.getPosition().x, mario.getPosition().y, 1, 1);        
        batch.end();
		
	}

	private float handleInput() {

		float move = 0;

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			move = Gdx.graphics.getDeltaTime() * 5;		
			
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			move = -Gdx.graphics.getDeltaTime() * 5;				
		}		
				
		mario.getPosition().x = mario.getPosition().x + move;		
		return move;
				
	}
}
