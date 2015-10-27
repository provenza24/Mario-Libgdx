package com.mygdx.game.mario.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameManager;
import com.mygdx.game.mario.sprite.menu.Mushroom;

public class MenuScreen implements Screen {

	private static final int HORIZONTAL_MENU_SPACING = 40;
	
	private static final int VERTICAL_MENU_START = Gdx.graphics.getWidth() / 2;		
	
	private String[] MENU_ITEMS = {"START / RESUME", "OPTIONS", "CREDITS"};
	
	private Stage stage;
	
	private BitmapFont font;
	
	private int currentItem = 0;
	
	private Mushroom mushroom;
	
	// constructor to keep a reference to the main Game class
	public MenuScreen() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);// Make the stage consume events
	
		font = new BitmapFont();		
		font.setColor(1, 1, 1, 1);
		
		mushroom = new Mushroom();
		mushroom.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 16 - 25, VERTICAL_MENU_START -12);
		
		stage.addActor(mushroom);		
	}

	@Override
	public void render(float delta) {

		handleInput();
		stage.act();		
		draw();
	}

	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Batch batch = stage.getBatch();
		batch.begin();
		float y = 0;
		for (String string : MENU_ITEMS) {
			font.draw(batch, string, Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 16, VERTICAL_MENU_START - y);
			y += HORIZONTAL_MENU_SPACING;
		}		
		batch.end();		
		stage.draw();
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.DOWN) && currentItem<MENU_ITEMS.length-1) {
			currentItem++;
			mushroom.setY(mushroom.getY() - HORIZONTAL_MENU_SPACING);
		}
		if (Gdx.input.isKeyJustPressed(Keys.UP) && currentItem>0) {
			currentItem--;
			mushroom.setY(mushroom.getY() + HORIZONTAL_MENU_SPACING);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if (currentItem==0) {
				GameScreen gameScreen = GameManager.getGameManager().getGameScreen();
				if (gameScreen==null) {
					 GameManager.getGameManager().setGameScreen(new GameScreen());								
				} 
				GameManager.getGameManager().setScreen(GameManager.getGameManager().getGameScreen());
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// called when this screen is set as the screen with game.setScreen();
	}

	@Override
	public void hide() {
		// called when current screen changes from this to a different screen
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// never called automatically
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
