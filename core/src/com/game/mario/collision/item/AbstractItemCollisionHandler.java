package com.game.mario.collision.item;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractItemCollisionHandler implements IItemCollisionHandler {

	private static Map<ItemEnum, IItemCollisionHandler> handlers = new HashMap<ItemEnum, IItemCollisionHandler>();

	static {
		handlers.put(ItemEnum.RED_MUSHROOM, new RedMushroomCollisionHandler());
		handlers.put(ItemEnum.GREEN_MUSHROOM, new GreenMushroomCollisionHandler());
		handlers.put(ItemEnum.FLOWER, new FlowerCollisionHandler());
		handlers.put(ItemEnum.TRANSFER_ITEM, new TransferCollisionHandler());		
		handlers.put(ItemEnum.COIN, new CoinCollisionHandler());		
		handlers.put(ItemEnum.STAR, new StarCollisionHandler());		
	}

	public static IItemCollisionHandler getHandler(AbstractItem sprite) {		
		return handlers.get(sprite.getType());
	}

	public AbstractItemCollisionHandler() {

	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		item.setDeletable(true);
	}
	
	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		item.setDeletable(true);
	}
	
	

}
