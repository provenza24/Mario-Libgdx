package com.mygdx.game.mario.screen.menu;

import com.badlogic.gdx.math.Vector2;

public class MenuItem {

	private String name;

	private Vector2 position;
	
	public MenuItem(String name, Vector2 position) {
		super();
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	
	

}
