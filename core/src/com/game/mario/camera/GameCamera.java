package com.game.mario.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.mario.sprite.AbstractSprite;

/**
 * Class used to define an orthographic camera which will follow a chosen sprite
 */
public class GameCamera {

	private OrthographicCamera camera;
	
	private float cameraOffset = 0;
	
	private boolean scrollable;
	
	private AbstractSprite followedSprite;

	public GameCamera(AbstractSprite followedSprite) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 15);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();		
		scrollable = true;				
		this.followedSprite = followedSprite;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public float getCameraOffset() {
		return cameraOffset;
	}

	public void setCameraOffset(float cameraOffset) {
		this.cameraOffset = cameraOffset;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
	
	public void moveCamera() {

		float move = followedSprite.getX() - followedSprite.getOldPosition().x;
		if (cameraOffset < 8) {
			cameraOffset = cameraOffset + move;
		} else {
			if (move > 0) {
				camera.position.x = camera.position.x + move;
			} else {
				cameraOffset = cameraOffset + move;
			}
		}
		if (followedSprite.getX() < camera.position.x - 8) {					
			followedSprite.setX(followedSprite.getOldPosition().x);
			followedSprite.getAcceleration().x = 0;
			cameraOffset = cameraOffset - move;
		}
		camera.update();
	}
}
