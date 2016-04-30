package com.game.mario.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

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
	
	private static float soundFxVolume;
	
	private static float musicVolume;
	
	private static SoundManager soundManager = new SoundManager();
	
	private SoundManager() {	
		soundFxVolume = 0f;
		musicVolume = 0f;		
	}

	public static SoundManager getSoundManager() {
		return soundManager;
	}
	
	public void playMusic(Sound music) {
		music.play(musicVolume);
	}
	
	public void stopMusic(Sound music) {
		music.stop();
	}
	
	public void playSound(Sound sound) {
		sound.play(soundFxVolume);
	}
	
	public void pauseSound(Sound music) {
		music.pause();		
	}
	
	public void decreaseMusicVolume() {
		musicVolume = musicVolume - 0.1f;
		if (musicVolume<0) {
			musicVolume=0;
		}
	}
	
	public void increaseMusicVolume() {
		musicVolume = musicVolume + 0.1f;
		if (musicVolume>1f) {
			musicVolume=1f;
		}		
	}
	
	public void decreaseSoundVolume() {
		soundFxVolume = soundFxVolume - 0.1f;
		if (soundFxVolume<0) {
			soundFxVolume=0;
		}
	}
	
	public void increaseSoundVolume() {
		soundFxVolume = soundFxVolume + 0.1f;
		if (soundFxVolume>1f) {
			soundFxVolume=1f;
		}
	}

	public float getSoundFxVolume() {
		return soundFxVolume;
	}
	
	public float getMusicVolume() {
		return musicVolume;
	}

}