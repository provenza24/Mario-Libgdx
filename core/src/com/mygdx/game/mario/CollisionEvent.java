package com.mygdx.game.mario;

public class CollisionEvent {

	boolean collidingLeft;

	boolean collidingRight;
	
	boolean collidingTop;
	
	boolean collidingBottom;

	public boolean isCollidingLeft() {
		return collidingLeft;
	}

	public void setCollidingLeft(boolean collidingLeft) {
		this.collidingLeft = collidingLeft;
	}

	public boolean isCollidingRight() {
		return collidingRight;
	}

	public void setCollidingRight(boolean collidingRight) {
		this.collidingRight = collidingRight;
	}

	public boolean isCollidingTop() {
		return collidingTop;
	}

	public void setCollidingTop(boolean collidingTop) {
		this.collidingTop = collidingTop;
	}

	public boolean isCollidingBottom() {
		return collidingBottom;
	}

	public void setCollidingBottom(boolean collidingBottom) {
		this.collidingBottom = collidingBottom;
	}
	}
