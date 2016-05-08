package com.game.mario.background;

import com.game.mario.enums.BackgroundTypeEnum;

public interface IScrollingBackground {

	public void update();
	
	public void render();	
	
	public void changeImage(BackgroundTypeEnum backgroundTypeEnum);
		
}
