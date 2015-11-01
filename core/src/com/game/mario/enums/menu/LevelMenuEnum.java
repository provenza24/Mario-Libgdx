package com.game.mario.enums.menu;

public enum LevelMenuEnum {

	START ("START");	

    private final String name;       

    private LevelMenuEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }	
	
}
