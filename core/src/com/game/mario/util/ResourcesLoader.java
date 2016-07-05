package com.game.mario.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.util.constant.WinConstants;

public class ResourcesLoader {

	public static final Map<WorldTypeEnum, Texture> WALL_TEXTURES = new HashMap<WorldTypeEnum, Texture>();		
	
	// Enemies
	public static final Texture GOOMBA_UNDERWORLD = new Texture(Gdx.files.internal("sprites/enemies/goomba_underworld.png"));
	
	public static final Texture GOOMBA_OVERWORLD = new Texture(Gdx.files.internal("sprites/enemies/goomba_overworld.png"));
	
	public static final Texture KOOPA = new Texture(Gdx.files.internal("sprites/enemies/koopa.png"));
	
	public static final Texture KOOPA_RED = new Texture(Gdx.files.internal("sprites/enemies/koopa_red.png"));
	
	public static final Texture PIRANHA_PLANT = new Texture(Gdx.files.internal("sprites/enemies/piranha_plant.png"));
	
	public static final Texture BOWSER = new Texture(Gdx.files.internal("sprites/enemies/bowser.png"));
	
	public static final Texture CASTLE_FIREBAR = new Texture(Gdx.files.internal("sprites/items/castle-firebar.png"));
	
	public static final Texture FIREFLAME = new Texture(Gdx.files.internal("sprites/items/fire-flame.png"));
	
	// Items
	public static final Texture COIN = new Texture(Gdx.files.internal("sprites/items/coin.png"));	
	
	public static final Texture FLAG = new Texture(Gdx.files.internal("sprites/items/flag.png"));
	
	public static final Texture HAWK = new Texture(Gdx.files.internal("sprites/items/hawk.png"));
	
	public static final Texture COIN_BLOC = new Texture(Gdx.files.internal("sprites/items/coin-from-bloc.png"));
	
	public static final Texture RED_MUSHROOM = new Texture(Gdx.files.internal("sprites/items/mushroom_red.png"));
	
	public static final Texture GREEN_MUSHROOM = new Texture(Gdx.files.internal("sprites/items/mushroom_green.png"));
	
	public static final Texture FLOWER = new Texture(Gdx.files.internal("sprites/items/flower.png"));
	
	public static final Texture STAR = new Texture(Gdx.files.internal("sprites/items/star.png"));

	public static final Texture TRANSFER_ITEM = new Texture(Gdx.files.internal("sprites/items/transfer.png"));	
	
	public static final Texture FIREBALL = new Texture(Gdx.files.internal("sprites/items/fireball.png"));
	
	public static final Texture METAL_PLATEFORM_4 = new Texture(Gdx.files.internal("sprites/items/metal-plateform-4.png"));
	
	public static final Texture METAL_PLATEFORM_6 = new Texture(Gdx.files.internal("sprites/items/metal-plateform-6.png"));		
	
	// Mario
	public static final Texture MARIO_SMALL = new Texture(Gdx.files.internal("sprites/mario/mario.png"));
	
	public static final Texture MARIO_BIG = new Texture(Gdx.files.internal("sprites/mario/mario-big.png"));
	
	public static final Texture MARIO_FLOWER = new Texture(Gdx.files.internal("sprites/mario/mario-big-flower.png"));
	
	//public static final Texture MARIO_SMALL_STAR = new Texture(Gdx.files.internal("sprites/mario/mario-star.png"));
	
	public static final Texture MARIO_SMALL_STAR_JUMP_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_jump_left.png"));
	
	public static final Texture MARIO_SMALL_STAR_SLIDE_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_slide_left.png"));
	
	public static final Texture MARIO_SMALL_STAR_RUN_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_run_left.png"));
	
	public static final Texture MARIO_SMALL_STAR_STAND_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_stand_left.png"));
	
	public static final Texture MARIO_SMALL_STAR_RUN_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_run_right.png"));
	
	public static final Texture MARIO_SMALL_STAR_JUMP_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_jump_right.png"));
	
	public static final Texture MARIO_SMALL_STAR_SLIDE_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_slide_right.png"));
	
	public static final Texture MARIO_SMALL_STAR_STAND_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_stand_right.png"));
	
	public static final Texture MARIO_SMALL_STAR_VICTORY = new Texture(Gdx.files.internal("sprites/mario/star/small/mario_victory.png"));
	
			
	public static final Texture MARIO_BIG_STAR_RUN_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_run_left.png"));
	
	public static final Texture MARIO_BIG_STAR_RUN_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_run_right.png"));		
	
	public static final Texture MARIO_BIG_STAR_JUMP_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_jump_right.png"));
	
	public static final Texture MARIO_BIG_STAR_JUMP_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_jump_left.png"));
	
	public static final Texture MARIO_BIG_STAR_SLIDE_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_slide_right.png"));
	
	public static final Texture MARIO_BIG_STAR_SLIDE_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_slide_left.png"));	
	
	public static final Texture MARIO_BIG_STAR_STAND_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_stand_right.png"));
	
	public static final Texture MARIO_BIG_STAR_STAND_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_stand_left.png"));	
	
	public static final Texture MARIO_BIG_STAR_VICTORY = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_victory.png"));
	
	public static final Texture MARIO_BIG_STAR_CROUCH_RIGHT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_crouch_right.png"));
	
	public static final Texture MARIO_BIG_STAR_CROUCH_LEFT = new Texture(Gdx.files.internal("sprites/mario/star/big/mario_crouch_left.png"));	
	
	
	public static final Texture MARIO_GROW_UP_RIGHT = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-right.png"));
	
	public static final Texture MARIO_GROW_UP_LEFT = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-left.png"));
		
	
	// Blocks
	public static final Texture MYSTERY_BLOC = new Texture(Gdx.files.internal("sprites/items/mystery.png"));
	
	public static final Texture MYSTERY_BLOC_INVISIBLE = new Texture(Gdx.files.internal("sprites/items/invisible-block.png"));
	
	// Walls
	public static final Texture WALL_UNDERGROUND = new Texture(Gdx.files.internal("sprites/wall/wall_underground.png"));
	
	public static final Texture WALL_OVERGROUND = new Texture(Gdx.files.internal("sprites/wall/wall_overground.png"));
			 
	// Backgrounds	// overworld-800.gif underworld-800.png	
	public static final Texture OVERGROUND_CLOUDS = new Texture(Gdx.files.internal("backgrounds/overworld-"+WinConstants.WIDTH+".gif"));
			
	public static final Texture UNDERGROUND = new Texture(Gdx.files.internal("backgrounds/underworld-"+WinConstants.WIDTH+".png"));
	
	public static final Texture WATERFALL = new Texture(Gdx.files.internal("backgrounds/waterfall-"+WinConstants.WIDTH+".png"));
	
	public static final Texture OVERGROUND_HILLS = new Texture(Gdx.files.internal("backgrounds/hills.png"));
	
	public static final Texture BONUS = new Texture(Gdx.files.internal("backgrounds/bonus_stage.png"));
	
	public static final Texture CASTLE = new Texture(Gdx.files.internal("backgrounds/castle-"+WinConstants.WIDTH+".png"));;
	
	// Transparency sprites	
	public static final Texture PIPE_DOWN = new Texture(Gdx.files.internal("sprites/transparency/pipe_down.png"));
	
	public static final Texture PIPE_LEFT = new Texture(Gdx.files.internal("sprites/transparency/pipe_left.png"));
	
	public static final Texture CASTLE_DOOR = new Texture(Gdx.files.internal("sprites/transparency/castle_door.png"));
	
	public static final Texture CASTLE_DOOR_BIG = new Texture(Gdx.files.internal("sprites/transparency/castle_door_big.png"));
	
	public static final Texture CASTLE_LAVA = new Texture(Gdx.files.internal("sprites/transparency/lava.png"));
	
	public static final Texture CASTLE_TOP = new Texture(Gdx.files.internal("sprites/transparency/castle_top.png"));
	
	// Miscelleanous
	public static final Texture FIREBALL_EXPLOSION = new Texture(Gdx.files.internal("sprites/misc/fireball_explosion.png"));
	
	public static final Texture CASTLE_BAG = new Texture(Gdx.files.internal("sprites/misc/bag.png"));
	
	public static final Texture CASTLE_TOAD = new Texture(Gdx.files.internal("sprites/misc/toad.png"));
	
	public static final Texture BRIDGE_BREAKING_WALL = new Texture(Gdx.files.internal("sprites/misc/bridge.png"));
	
	public static final Texture WHITE_FLAG = new Texture(Gdx.files.internal("sprites/misc/white_flag.png"));
	
	// Menu
	public static final BitmapFont MAIN_MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
	
	public static final BitmapFont MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
		
	static {
		WALL_TEXTURES.put(WorldTypeEnum.OVERGROUND, WALL_OVERGROUND);		
		WALL_TEXTURES.put(WorldTypeEnum.UNDERGROUND, WALL_UNDERGROUND);
		WALL_TEXTURES.put(WorldTypeEnum.BONUS, WALL_UNDERGROUND);
	}
}
