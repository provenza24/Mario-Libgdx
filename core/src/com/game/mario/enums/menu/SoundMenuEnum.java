package com.game.mario.enums.menu;

public enum SoundMenuEnum {

	MUSIC_VOL ("MUSIC VOLUME"),
	SOUND_VOL ("SOUND VOLUME"),
	BACK ("BACK");

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
