package com.game.mario.collision;

import java.util.ArrayList;
import java.util.List;

public class CollisionEvent {

	private List<CollisionPoint> collisionPoints;
	
	boolean collidingTop;
	
	boolean collidingBottom;
	
	boolean collidingLeft;
	
	boolean collidingRight;
	
	boolean collidingBottomLeft;

	boolean collidingBottomRight;
	
	boolean collidingTopLeft;
	
	boolean collidingTopRight;

	public CollisionEvent() {
		collisionPoints = new ArrayList<CollisionPoint>();
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

	public boolean isCollidingBottomLeft() {
		return collidingBottomLeft;
	}

	public void setCollidingBottomLeft(boolean collidingBottomLeft) {
		this.collidingBottomLeft = collidingBottomLeft;
	}

	public boolean isCollidingBottomRight() {
		return collidingBottomRight;
	}

	public void setCollidingBottomRight(boolean collidingBottomRight) {
		this.collidingBottomRight = collidingBottomRight;
	}

	public boolean isCollidingTopLeft() {
		return collidingTopLeft;
	}

	public void setCollidingTopLeft(boolean collidingTopLeft) {
		this.collidingTopLeft = collidingTopLeft;
	}

	public boolean isCollidingTopRight() {
		return collidingTopRight;
	}

	public void setCollidingTopRight(boolean collidingTopRight) {
		this.collidingTopRight = collidingTopRight;
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
	
}
