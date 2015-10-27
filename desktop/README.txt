private void createBasicSkin() {

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/ui-blue.atlas"));
		// Create a font
		BitmapFont font = new BitmapFont();
		skin = new Skin(atlas);
		skin.add("default", font);
		
		final CheckBoxStyle t = new CheckBoxStyle();
		t.font = skin.getFont("default");
		t.fontColor = new Color(0, 0, 0, 1f);
		t.disabledFontColor = new Color(0, 0, 0, 0.4f);
		t.checkboxOff = skin.getDrawable("checkbox_off");
		t.checkboxOn = skin.getDrawable("checkbox_on");
		skin.add("default", t);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();		
		textButtonStyle.up = skin.newDrawable("button_04");
		textButtonStyle.down = skin.newDrawable("button_01");
		textButtonStyle.checked = skin.newDrawable("button_01");
		textButtonStyle.over = skin.newDrawable("button_03");
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		// Create a texture
		Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10,
				Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));
	
	}