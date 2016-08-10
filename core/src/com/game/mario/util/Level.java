package com.game.mario.util;

import com.game.mario.enums.WorldTypeEnum;

public class Level {

	private int worldNumber;
	
	private int levelNumber;
	
	private String tmxName;
	
	private WorldTypeEnum worldTypeEnum;

	public Level(int worldNumber, int levelNumber, String tmxName, WorldTypeEnum worldTypeEnum) {
		super();
		this.worldNumber = worldNumber;
		this.levelNumber = levelNumber;
		this.tmxName = tmxName;
		this.worldTypeEnum = worldTypeEnum;
	}

	public int getWorldNumber() {
		return worldNumber;
	}

	public void setWorldNumber(int worldNumber) {
		this.worldNumber = worldNumber;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getTmxName() {
		return tmxName;
	}

	public void setTmxName(String tmxName) {
		this.tmxName = tmxName;
	}

	public WorldTypeEnum getWorldTypeEnum() {
		return worldTypeEnum;
	}

	public void setWorldTypeEnum(WorldTypeEnum worldTypeEnum) {
		this.worldTypeEnum = worldTypeEnum;
	}
	
	

}
