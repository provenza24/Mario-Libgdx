package com.game.mario.enums;

public abstract class AbstractEnum {

	private final String name;       

    private AbstractEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }	
}
