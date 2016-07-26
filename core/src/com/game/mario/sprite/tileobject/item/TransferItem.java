package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.enums.MusicEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.AbstractTileObjectItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.constant.TilemapPropertiesConstants;

public abstract class TransferItem extends AbstractTileObjectItem {

	protected int keyToPress;
	
	protected Vector2 transferPosition;
	
	protected boolean scrollableCamera;
	
	protected WorldTypeEnum worldTypeEnum;
	
	protected Sound music;
	
	protected Sprite pipe;
	
	protected Array<BackgroundTypeEnum> backgroundTypesEnum;

	public TransferItem(MapObject mapObject) {		
		super(mapObject, new Vector2());							
		gravitating = false;
		collidableWithTilemap= false;
		transferPosition = new Vector2(Float.parseFloat((String)mapObject.getProperties().get("xOutgoing"))/32, Float.parseFloat((String)mapObject.getProperties().get("yOutgoing"))/32);		
		scrollableCamera = ((String)mapObject.getProperties().get("scrollable")).equals("true");		
		worldTypeEnum = WorldTypeEnum.valueOf(((String)mapObject.getProperties().get(TilemapPropertiesConstants.WORLD)).toUpperCase());		
		music = SoundManager.getSoundManager().getMusicTheme(MusicEnum.valueOf(((String)mapObject.getProperties().get("music")).toUpperCase()));
		String backgrounds[] =  ((String)mapObject.getProperties().get(TilemapPropertiesConstants.BACKGROUNDS)).split(",");
		backgroundTypesEnum = new Array<BackgroundTypeEnum>();
		for (String background : backgrounds) {
			backgroundTypesEnum.add(BackgroundTypeEnum.valueOf(background.toUpperCase()));
		}		
	}
			
	@Override
	public void initializeAnimations()  {
		spriteSheet = ResourcesLoader.TRANSFER_ITEM;				
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

	public WorldTypeEnum getWorldTypeEnum() {
		return worldTypeEnum;
	}

	public void setWorldTypeEnum(WorldTypeEnum worldTypeEnum) {
		this.worldTypeEnum = worldTypeEnum;
	}

	public Sound getMusic() {
		return music;
	}

	public void setMusic(Sound music) {
		this.music = music;
	}

	public Sprite getPipe() {
		return pipe;
	}

	public void setPipe(Sprite pipe) {
		this.pipe = pipe;
	}

	public Array<BackgroundTypeEnum> getBackgroundTypesEnum() {
		return backgroundTypesEnum;
	}

	public void setBackgroundTypesEnum(Array<BackgroundTypeEnum> backgroundTypesEnum) {
		this.backgroundTypesEnum = backgroundTypesEnum;
	}
	
	@Override
	public ItemEnum getType() {
		return ItemEnum.TRANSFER_ITEM;
	}

	
}
