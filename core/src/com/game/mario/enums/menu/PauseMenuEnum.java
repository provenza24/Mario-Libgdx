package com.game.mario.enums.menu;

public enum PauseMenuEnum {

	CONTINUE ("continue"),
	QUIT ("quit");	

    private final String name;       

    private PauseMenuEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }	
	
}
