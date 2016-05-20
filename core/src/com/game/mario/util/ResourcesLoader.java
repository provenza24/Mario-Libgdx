package com.game.mario.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.mario.enums.BackgroundTypeEnum;

public class ResourcesLoader {

	public static final Map<BackgroundTypeEnum, Texture> WALL_TEXTURES = new HashMap<BackgroundTypeEnum, Texture>();		
	
	// Enemies
	public static final Texture GOOMBA_UNDERWORLD = new Texture(Gdx.files.internal("sprites/enemies/goomba_underworld.png"));
	
	public static final Texture GOOMBA_OVERWORLD = new Texture(Gdx.files.internal("sprites/enemies/goomba_overworld.png"));
	
	public static final Texture KOOPA = new Texture(Gdx.files.internal("sprites/enemies/koopa.png"));
	
	public static final Texture PIRANHA_PLANT = new Texture(Gdx.files.internal("sprites/enemies/piranha_plant.png"));
	
	// Items
	public static final Texture COIN = new Texture(Gdx.files.internal("sprites/items/coin.png"));	
	
	public static final Texture FLAG = new Texture(Gdx.files.internal("sprites/items/flag.png"));
	
	public static final Texture COIN_BLOC = new Texture(Gdx.files.internal("sprites/items/coin-from-bloc.png"));
	
	public static final Texture RED_MUSHROOM = new Texture(Gdx.files.internal("sprites/items/mushroom_red.png"));
	
	public static final Texture GREEN_MUSHROOM = new Texture(Gdx.files.internal("sprites/items/mushroom_green.png"));
	
	public static final Texture FLOWER = new Texture(Gdx.files.internal("sprites/items/flower.png"));

	public static final Texture TRANSFER_ITEM = new Texture(Gdx.files.internal("sprites/items/transfer.png"));	
	
	public static final Texture FIREBALL = new Texture(Gdx.files.internal("sprites/items/fireball.png"));
	
	// Mario
	public static final Texture MARIO_SMALL = new Texture(Gdx.files.internal("sprites/mario/mario.gif"));
	
	public static final Texture MARIO_BIG = new Texture(Gdx.files.internal("sprites/mario/mario-big.png"));
	
	public static final Texture MARIO_FLOWER = new Texture(Gdx.files.internal("sprites/mario/mario-big-flower.png"));
	
	// Blocks
	public static final Texture MYSTERY_BLOC = new Texture(Gdx.files.internal("sprites/items/mystery.png"));
	
	// Walls
	public static final Texture WALL_UNDERGROUND = new Texture(Gdx.files.internal("sprites/wall/wall_underground.png"));
	
	public static final Texture WALL_OVERGROUND = new Texture(Gdx.files.internal("sprites/wall/wall_overground.png"));
			 
	// Backgrounds	// overworld-800.gif underworld-800.png	
	public static final Texture OVERWORLD = new Texture(Gdx.files.internal("backgrounds/overworld.gif"));
	
	public static final Texture UNDERWORLD = new Texture(Gdx.files.internal("backgrounds/underworld.png"));
	
	public static final Texture BONUS_STAGE = new Texture(Gdx.files.internal("backgrounds/bonus_stage.png"));
	
	// Transparency sprites	
	public static final Texture PIPE_DOWN = new Texture(Gdx.files.internal("sprites/transparency/pipe_down.png"));
	
	public static final Texture PIPE_LEFT = new Texture(Gdx.files.internal("sprites/transparency/pipe_left.png"));
	
	// Miscelleanous
	public static final Texture FIREBALL_EXPLOSION = new Texture(Gdx.files.internal("sprites/misc/fireball_explosion.png"));
	
	// Menu
	public static final BitmapFont MAIN_MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
	
	public static final BitmapFont MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
		
	static {
		WALL_TEXTURES.put(BackgroundTypeEnum.OVERGROUND, WALL_OVERGROUND);		
		WALL_TEXTURES.put(BackgroundTypeEnum.UNDERGROUND, WALL_UNDERGROUND);				
	}
}
