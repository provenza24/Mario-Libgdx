package com.game.mario.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {

	private static BitmapFont debugFont;
	
	private static BitmapFont font;
	
	public Fonts() {
		debugFont = new BitmapFont();		
		debugFont.setColor(0, 0, 1, 1);
		
		font = new BitmapFont(Gdx.files.internal("fonts/mario_in_game.fnt"));		
		font.setColor(1,1,1,1);
	}
	
	public static BitmapFont getDebugFont() {
		return debugFont;
	}

	public static BitmapFont getInGameFont() {
		return font;
	}
}
