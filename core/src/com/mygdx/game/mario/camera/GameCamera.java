package com.mygdx.game.mario.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public class GameCamera {

	private OrthographicCamera camera;
	
	private float cameraOffset = 0;
	
	private boolean scrollable;

	public GameCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 15);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		scrollable = true;
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
	
	public void moveCamera(Mario mario) {

		float move = mario.getX() - mario.getOldPosition().x;
		if (cameraOffset < 8) {
			cameraOffset = cameraOffset + move;
		} else {
			if (move > 0) {
				camera.position.x = camera.position.x + move;
			} else {
				cameraOffset = cameraOffset + move;
			}
		}
		if (mario.getX() < camera.position.x - 8) {
			mario.setX(mario.getOldPosition().x);
			mario.getAcceleration().x = 0;
			cameraOffset = cameraOffset - move;
		}
		camera.update();
	}
}
