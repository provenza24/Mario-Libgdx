package com.game.mario.collision.item;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.item.FireFlame;
import com.game.mario.sprite.item.Flower;
import com.game.mario.sprite.item.GreenMushroom;
import com.game.mario.sprite.item.RedMushroom;
import com.game.mario.sprite.tileobject.item.CastleFirebar;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.item.TransferItemDown;
import com.game.mario.sprite.tileobject.item.TransferItemRight;
import com.game.mario.sprite.tileobject.item.plateform.AscendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.DescendingMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractItemCollisionHandler implements IItemCollisionHandler {

	private static Map<Class<?>, IItemCollisionHandler> handlers = new HashMap<Class<?>, IItemCollisionHandler>();

	static {
		handlers.put(RedMushroom.class, new RedMushroomCollisionHandler());
		handlers.put(GreenMushroom.class, new GreenMushroomCollisionHandler());
		handlers.put(Flower.class, new FlowerCollisionHandler());
		handlers.put(TransferItemDown.class, new TransferCollisionHandler());
		handlers.put(TransferItemRight.class, new TransferCollisionHandler());
		handlers.put(Coin.class, new CoinCollisionHandler());
		handlers.put(AscendingMetalPlateform.class, new MetalPlateformCollisionHandler());
		handlers.put(DescendingMetalPlateform.class, new MetalPlateformCollisionHandler());
		handlers.put(CastleFirebar.class, new CastleFirebarCollisionHandler());		
		handlers.put(FireFlame.class, new CastleFirebarCollisionHandler());
	}

	public static IItemCollisionHandler getHandler(AbstractSprite sprite) {		
		return handlers.get(sprite.getClass());
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
