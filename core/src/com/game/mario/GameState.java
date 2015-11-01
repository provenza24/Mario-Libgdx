package com.game.mario;

import java.util.ArrayList;
import java.util.List;

public class GameState {

	private static final GameState instance = new GameState();
	
	private int nbLifes;
	
	private int nbCoins;
	
	private int currentLevel;
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;
	
	private List<String> levels = new ArrayList<String>();
	
	public GameState() {
		initState();
		levels.add(0, "level_1_1.tmx");		
		levels.add(1, "level_1_2.tmx");
	}

	private void initState() {
		nbLifes = 3;
		nbCoins = 0;
		sizeState = 0;
		currentLevel = 0;
	}
	
	public void nextLevel() {
		currentLevel++;
	}

	
	
	public void addCoin() {
		this.nbCoins++;
	}
	
	public static GameState getInstance() {
		return instance;
	}

	public int getNbLifes() {
		return nbLifes;
	}

	public void setNbLifes(int nbLifes) {
		this.nbLifes = nbLifes;
	}

	public int getNbCoins() {
		return nbCoins;
	}

	public void setNbCoins(int nbCoins) {
		this.nbCoins = nbCoins;
	}


	public int getSizeState() {
		return sizeState;
	}


	public void setSizeState(int sizeState) {
		this.sizeState = sizeState;
	}



	public int getCurrentLevel() {
		return currentLevel;
	}

	public String getCurrentLevelName() {
		return levels.get(currentLevel);
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public List<String> getLevels() {
		return levels;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

}
