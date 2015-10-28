package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public abstract class TransferItem extends AbstractTileObjectSprite {

	protected int keyToPress;
	
	protected Vector2 transferPosition;
	
	protected boolean scrollableCamera;

	public TransferItem(MapObject mapObject) {		
		super(mapObject);					
		setSize((float)spriteSheet.getWidth()/32, (float)spriteSheet.getHeight()/32);
		setRenderingSize((float)spriteSheet.getWidth()/32, (float)spriteSheet.getHeight()/32);
		setY(getY()-1+getHeight());
		gravitating = false;
		collidableWithTilemap= false;
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		transferPosition = new Vector2(Float.parseFloat((String)mapObject.getProperties().get("xOutgoing"))/32, Float.parseFloat((String)mapObject.getProperties().get("yOutgoing"))/32);		
		scrollableCamera = ((String)mapObject.getProperties().get("scrollable")).equals("true");
	}
			
	@Override
	public void initializeAnimations()  {
		spriteSheet = new Texture(Gdx.files.internal("sprites/transfer.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);						
		TextureRegion[] frame = new TextureRegion[1];
		frame[0] = tmp[0][0];
		currentAnimation = new Animation(0, frame);		
	}
	
	public Vector2 getTransferPosition() {
		return transferPosition;
	}

	public void setTransferPosition(Vector2 transferPosition) {
		this.transferPosition = transferPosition;
	}

	public boolean isScrollableCamera() {
		return scrollableCamera;
	}

	public void setScrollableCamera(boolean scrollableCamera) {
		this.scrollableCamera = scrollableCamera;
	}

	public int getKeyToPress() {
		return keyToPress;
	}

	public void setKeyToPress(int keyToPress) {
		this.keyToPress = keyToPress;
	}

}
