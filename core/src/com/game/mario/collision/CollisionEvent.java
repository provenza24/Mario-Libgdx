package com.game.mario.collision;

import java.util.ArrayList;
import java.util.List;

public class CollisionEvent {

	private List<CollisionPoint> collisionPoints;
	
	boolean collidingLeft;

	boolean collidingRight;
	
	boolean collidingTop;
	
	boolean collidingBottom;

	public CollisionEvent() {
		collisionPoints = new ArrayList<CollisionPoint>();
	}
	
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

	public List<CollisionPoint> getCollisionPoints() {
		return collisionPoints;
	}

	public void setCollisionPoints(List<CollisionPoint> collisionPoints) {
		this.collisionPoints = collisionPoints;
	}
	
	public void reinitCollisionPoints() {
		this.collisionPoints = new ArrayList<CollisionPoint>();
	}
	
}
