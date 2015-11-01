package com.game.mario;

import java.util.ArrayList;
import java.util.List;

public class GameState {

	private static final GameState instance = new GameState();
	
	private int nbLifes;
	
	private int nbCoins;
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;
	
	private List<String> levels = new ArrayList<String>();
	
	public GameState() {
		nbLifes = 3;
		nbCoins = 0;
		sizeState = 0;
		levels.add(0, "level_1_1.tmx");		
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

}
