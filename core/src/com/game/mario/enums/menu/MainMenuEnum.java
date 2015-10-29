package com.game.mario.enums.menu;

public enum MainMenuEnum {

	ONE_PLAYER_GAME ("1 player game"),
	OPTIONS ("options"),
	CREDITS ("credits");

    private final String name;       

    private MainMenuEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }	
	
}
