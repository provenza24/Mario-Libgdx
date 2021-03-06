package com.game.mario.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.game.mario.enums.MusicEnum;

public class SoundManager {

	private static final float VOLUME_SOUND = 1f;

	private static final float VOLUME_MUSIC = 1f;

	public static Map<MusicEnum, Sound> THEMES = new HashMap<MusicEnum, Sound>();
	
	public static Sound SOUND_TITLE_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/title.ogg"));
	
	public static Sound SOUND_OVERWORLD_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/overworld.ogg"));
	
	public static Sound SOUND_UNDERGROUND_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/underground.ogg"));			
	
	public static Sound SOUND_CASTLE_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/castle.ogg"));
	
	public static Sound SOUND_WORLD_CLEAR = Gdx.audio.newSound(Gdx.files.internal("audio/themes/world_clear.ogg"));
	
	public static Sound SOUND_BONUS_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/bonus.ogg"));
	
	public static Sound SOUND_BOWSER_THEME = Gdx.audio.newSound(Gdx.files.internal("audio/themes/bowser.ogg"));
	
	public static Sound SOUND_INVINCIBLE = Gdx.audio.newSound(Gdx.files.internal("audio/invincible.mp3"));
	
	public static Sound SOUND_MARIO_DEATH = Gdx.audio.newSound(Gdx.files.internal("audio/smb_mariodie.ogg"));
	
	public static Sound SOUND_COIN = Gdx.audio.newSound(Gdx.files.internal("audio/smb_coin.ogg"));
	
	public static Sound SOUND_POWERUP_APPEAR = Gdx.audio.newSound(Gdx.files.internal("audio/smb_powerup_appears.ogg"));
	
	public static Sound SOUND_JUMP_SMALL = Gdx.audio.newSound(Gdx.files.internal("audio/smb_jump-small.ogg"));
	
	public static Sound SOUND_JUMP_SUPER = Gdx.audio.newSound(Gdx.files.internal("audio/smb_jump-super.ogg"));
	
	public static Sound SOUND_BREAK_BLOCK = Gdx.audio.newSound(Gdx.files.internal("audio/smb_breakblock.ogg"));
	
	public static Sound SOUND_BUMP = Gdx.audio.newSound(Gdx.files.internal("audio/smb_bump.ogg"));
	
	public static Sound SOUND_POWERUP = Gdx.audio.newSound(Gdx.files.internal("audio/smb_powerup.ogg"));
	
	public static Sound SOUND_PIPE = Gdx.audio.newSound(Gdx.files.internal("audio/smb_pipe.ogg"));
		
	public static Sound SOUND_KICK = Gdx.audio.newSound(Gdx.files.internal("audio/smb_kick.ogg"));
	
	public static Sound SOUND_FLAGPOLE = Gdx.audio.newSound(Gdx.files.internal("audio/smb_flagpole.ogg"));
	
	public static Sound SOUND_STAGE_CLEAR = Gdx.audio.newSound(Gdx.files.internal("audio/smb_stage_clear.ogg"));
	
	public static Sound SOUND_FLAME = Gdx.audio.newSound(Gdx.files.internal("audio/flames.mp3"));
	
	public static Sound SOUND_FIREWORK = Gdx.audio.newSound(Gdx.files.internal("audio/smb_fireworks.ogg"));
	
	private static float soundFxVolume;
	
	private static float musicVolume;
	
	private static SoundManager soundManager = new SoundManager();
	
	private static Sound stageMusic;
	
	private static Sound currentMusic;
	
	private static long currentMusicId;
	
	static {
		THEMES.put(MusicEnum.UNDERGROUND, SOUND_UNDERGROUND_THEME);
		THEMES.put(MusicEnum.OVERGROUND, SOUND_OVERWORLD_THEME);
		THEMES.put(MusicEnum.BONUS, SOUND_BONUS_THEME);
		THEMES.put(MusicEnum.CASTLE, SOUND_CASTLE_THEME);
	}
	
	private SoundManager() {	
		musicVolume = VOLUME_MUSIC;		
		soundFxVolume = VOLUME_SOUND;
	}

	public static SoundManager getSoundManager() {
		return soundManager;
	}
	
	public void playMusic(boolean change) {
		
		if (change) {
			currentMusic = stageMusic;
		}		
		currentMusicId = currentMusic.loop(musicVolume);
	}
	
	public void resumeMusic() {
		currentMusic.resume();
	}
	
	public void pauseMusic() {		
		currentMusic.pause();
	}
	
	public void stopMusic() {
		currentMusic.stop();
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
		currentMusic.setVolume(currentMusicId, musicVolume);
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

	public Sound getStageMusic() {
		return stageMusic;
	}

	public void setStageMusic(Sound stageMusic) {
		SoundManager.stageMusic = stageMusic;
	}
	
	public void setStageMusic(MusicEnum musicEnum) {
		SoundManager.stageMusic = THEMES.get(musicEnum);
	}

	public Sound getCurrentMusic() {
		return currentMusic;
	}

	public void setCurrentMusic(Sound currentMusic) {
		SoundManager.currentMusic = currentMusic;
	}
	
	public Sound getMusicTheme(MusicEnum musicEnum) {
		return THEMES.get(musicEnum);
	}

}
