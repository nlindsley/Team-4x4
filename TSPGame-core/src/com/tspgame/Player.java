package com.tspgame;

public class Player extends Character {
	public Player(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor
		
		defText = Textures.PLAYER;	// Overwrites player texture
	}
}
