package com.tspgame;

/**
 * This class creates the background (i.e. the floor) for the game.
 *
 */

public class Background extends Character {
	public Background(TSPGame game, int x, int y, int world) {
		super(game, x, y);	// Uses Character constructor
		
		// Overwrites block texture
		switch(world){
		case 1:
			defText = Textures.W1B;
			break;
		case 2:
			defText = Textures.W2B;
			break;
		case 3:
			defText = Textures.W3B;
			break;
		case 4:
			defText = Textures.W4B;
			break;
		case 5:
			defText = Textures.W5B;
			break;
		case 6:
			defText = Textures.W6B;
			break;
		case 7:
			defText = Textures.W7B;
			break;
		case 8:
			defText = Textures.W8B;
			break;
		}
	}
}