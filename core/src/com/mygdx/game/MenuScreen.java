package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

	private final GameManager game; // Note it's "MyGame" not "Game"

	private Skin skin;
	
	private Stage stage;
	
	// constructor to keep a reference to the main Game class
	public MenuScreen(GameManager pgame) {
		
		this.game = pgame;
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
 
        createBasicSkin();
        TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(newGameButton);
        
        newGameButton.addListener( new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	game.getGameScreen().create();
		        game.setScreen(game.getGameScreen());
		    };
		});
	}
	
	private void createBasicSkin(){
		  //Create a font
		  BitmapFont font = new BitmapFont();
		  skin = new Skin();
		  skin.add("default", font);
		 
		  //Create a texture
		  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		  pixmap.setColor(Color.WHITE);
		  pixmap.fill();
		  skin.add("background",new Texture(pixmap));
		 
		  //Create a button style
		  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		  textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		  textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		  textButtonStyle.font = skin.getFont("default");
		  skin.add("default", textButtonStyle);
		  
		}
	

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
        stage.act();
        stage.draw();
		
		// update and draw stuff
		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			game.getGameScreen().create();
			game.setScreen(game.getGameScreen());
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
