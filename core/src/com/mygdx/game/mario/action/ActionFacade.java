package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class ActionFacade {

	public static Action createMoveAction(float x, float y, float duration) {
		MoveToAction moveAction = new MoveToAction();
	    moveAction.setPosition(x, y);
	    moveAction.setDuration(duration);
	    return moveAction;
	}
	
}
