package com.game.mario.enums.menu;

public enum PauseMenuEnum {

	CONTINUE ("CONTINUE"),
	QUIT ("QUIT");	

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
