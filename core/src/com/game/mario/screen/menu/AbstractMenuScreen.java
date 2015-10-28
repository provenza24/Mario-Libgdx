package com.game.mario.screen.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.sprite.menu.DefaultSelector;

public abstract class AbstractMenuScreen implements IScreen {

	private float horizontalMenuStart = Gdx.graphics.getWidth() / 2;
	
	private Vector2 offset;
	
	private float selectorHeight = 0;
	
	private int horizontalMenuSpacing = 40;
	
	private float verticalMenuStart = Gdx.graphics.getHeight() / 2;
	
	private Stage stage;
	
	private int currentItem = 0;
	
	private Actor selector;
	
	private BitmapFont font;
	
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
				
	public AbstractMenuScreen(String[] menuItems) {
		this(null, menuItems);
	}
		
	public AbstractMenuScreen(Class<?> selectorClass, String[] pMenuItems) {				
		font = new BitmapFont(Gdx.files.internal("verdana.fnt"));			
		font.setColor(1, 1, 1, 1);	
		offset = new Vector2(0,0);
		initItemsPositions(pMenuItems);			
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);// Make the stage consume events
		generateSelector(selectorClass);		
		stage.addActor(selector);		
	}

	public void setOffset(float xNewOffset, float yNewOffset) {	
		verticalMenuStart = verticalMenuStart - offset.y + yNewOffset;
		for (MenuItem menuItem : menuItems) {
			menuItem.getPosition().x = menuItem.getPosition().x - offset.x + xNewOffset;
		}
		horizontalMenuStart = horizontalMenuStart - offset.x + xNewOffset; 
		selector.setPosition(horizontalMenuStart - 25, verticalMenuStart -12);
		offset.x = xNewOffset;
		offset.y = yNewOffset;
	}
	
	private void initItemsPositions(String[] pMenuItems) {
		float maxWidth = 0;
		selectorHeight = 0;
		for (String itemName : pMenuItems) {
			GlyphLayout layout = new GlyphLayout();
			layout.setText(font, itemName);
			selectorHeight = layout.height > selectorHeight ? layout.height : selectorHeight;
			maxWidth = layout.width > maxWidth ? layout.width : maxWidth;
			menuItems.add(new MenuItem(itemName, new Vector2((Gdx.graphics.getWidth() - layout.width) /2, 0)));
		}				
		horizontalMenuStart = (Gdx.graphics.getWidth() - maxWidth) /2;		
	}

	private void generateSelector(Class<?> selectorClass) {
		try {
			if (selectorClass!=null) {
				selector = (Actor) selectorClass.newInstance();
			} else {
				selector = (Actor) DefaultSelector.class.newInstance();
			}
		} catch (Exception e) {
			try {
				selector = (Actor) DefaultSelector.class.newInstance();
			} catch (Exception e1) {
				// Should never happen
			}
		} 
		selector.setSize(selectorHeight*1.5f, selectorHeight*1.5f);
		selector.setPosition(horizontalMenuStart - 50, verticalMenuStart - 25);
	}

	@Override
	public void render(float delta) {	
		handleInput();
		stage.act();
		draw();
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Batch batch = stage.getBatch();
		batch.begin();
		float y = 0;
		int i = 0;
		for (MenuItem menuItem : menuItems) {
			font.draw(batch, menuItem.getName(), menuItems.get(i).getPosition().x, verticalMenuStart - y);
			y += horizontalMenuSpacing;
			i++;
		}
		batch.end();
		stage.draw();
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.DOWN) && currentItem < menuItems.size() - 1) {
			currentItem++;
			selector.setY(selector.getY() - horizontalMenuSpacing);
		}
		if (Gdx.input.isKeyJustPressed(Keys.UP) && currentItem > 0) {
			currentItem--;
			selector.setY(selector.getY() + horizontalMenuSpacing);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}	

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public int getHorizontalMenuSpacing() {
		return horizontalMenuSpacing;
	}

	public void setHorizontalMenuSpacing(int horizontalMenuSpacing) {
		this.horizontalMenuSpacing = horizontalMenuSpacing;
	}

	public int getSelectedItemIndex() {
		return currentItem;
	}
	
	public String getSelectedItemName() {
		return menuItems.get(currentItem).getName();
	}

}
