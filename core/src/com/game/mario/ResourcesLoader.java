package com.game.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ResourcesLoader {

	public static final Texture MYSTERY_BLOC = new Texture(Gdx.files.internal("sprites/mystery.png"));  
	
	public static final Texture GOOMBA = new Texture(Gdx.files.internal("sprites/goomba.png"));
	
	public static final Texture COIN = new Texture(Gdx.files.internal("sprites/coin.png"));
	
	public static final Texture MARIO_SMALL = new Texture(Gdx.files.internal("sprites/mario.gif"));
	
	public static final Texture MARIO_BIG = new Texture(Gdx.files.internal("sprites/mario-big.png"));
	
	public static final Texture FLAG = new Texture(Gdx.files.internal("sprites/flag.png"));
	
	public static final Texture COIN_BLOC = new Texture(Gdx.files.internal("sprites/coin-from-bloc.png"));
	
	public static final Texture MUSHROOM = new Texture(Gdx.files.internal("sprites/mushroom.png"));
	
	public static final Texture OVERWORLD = new Texture(Gdx.files.internal("overworld.gif"));
	
	public static final Texture UNDERWORLD = new Texture(Gdx.files.internal("underworld.png"));
	
	public static final Texture TRANSFER_ITEM = new Texture(Gdx.files.internal("sprites/transfer.png"));	
	
	public static final BitmapFont MAIN_MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
	
	public static final BitmapFont MENU_FONT = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
	
	public static Sound SOUND_MARIO_DEATH = Gdx.audio.newSound(Gdx.files.internal("audio/smb_mariodie.ogg"));
	
	public static Sound SOUND_COIN = Gdx.audio.newSound(Gdx.files.internal("audio/smb_coin.ogg"));
	
	public static Sound SOUND_POWERUP_APPEAR = Gdx.audio.newSound(Gdx.files.internal("audio/smb_powerup_appears.ogg"));
	
	public static Sound SOUND_JUMP_SMALL = Gdx.audio.newSound(Gdx.files.internal("audio/smb_jump-small.ogg"));
	
	public static Sound SOUND_JUMP_SUPER = Gdx.audio.newSound(Gdx.files.internal("audio/smb_jump-super.ogg"));
	
	public static Sound SOUND_BREAK_BLOCK = Gdx.audio.newSound(Gdx.files.internal("audio/smb_breakblock.ogg"));
	
	public static Sound SOUND_BUMP = Gdx.audio.newSound(Gdx.files.internal("audio/smb_bump.ogg"));
	
	public static Sound SOUND_POWERUP = Gdx.audio.newSound(Gdx.files.internal("audio/smb_powerup.ogg"));
	
	public static Sound SOUND_PIPE = Gdx.audio.newSound(Gdx.files.internal("audio/smb_pipe.ogg"));
	
	public static Sound SOUND_MAIN_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/main_theme.ogg"));
	
	public static Sound SOUND_TITLE_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/title-theme.mp3"));

	public static Sound SOUND_KICK = Gdx.audio.newSound(Gdx.files.internal("audio/smb_kick.ogg"));	

}
