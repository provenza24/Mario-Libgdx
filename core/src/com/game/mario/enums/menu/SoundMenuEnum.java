package com.game.mario.enums.menu;

public enum SoundMenuEnum {

	MUSIC_VOLUME ("MUSIC VOLUME"),
	SOUND_VOLUME ("SOUND VOLUME"),
	QUIT ("QUIT");

    private final String name;       

    private SoundMenuEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }	
	
}
