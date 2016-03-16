package com.tspgame;

/**
 * This class creates the background (i.e. the floor) for the game.
 *
 */

public class Background extends Character {
	public Background(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor
		
		defText = Textures.BG;	// Overwrites block texture
	}
}