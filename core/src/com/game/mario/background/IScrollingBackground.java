package com.game.mario.background;

import com.game.mario.enums.BackgroundTypeEnum;

public interface IScrollingBackground {

	/** Update the background position */
	public void update();
	
	/** Render the background image on screen */
	public void render();	
	
	/** Change the image of the background */
	public void changeImage(BackgroundTypeEnum backgroundTypeEnum);
	
	/** Enable / disable background display */
	public void toggleEnabled();
		
}
