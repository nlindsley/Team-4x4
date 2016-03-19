package com.tspgame;

/**
 * This class creates the background (i.e. the floor) for the game.
 *
 */

public class Background extends Character {
	public Background(TSPGame game, int x, int y, int world) {
		super(game, x, y);	// Uses Character constructor
		
		defText = Textures.BG;	// Overwrites block texture
		switch(world){
		case 1:
			defText = Textures.BG;
		case 2:
			defText = Textures.W2B;
		case 3:
			defText = Textures.W3B;
		case 4:
			defText = Textures.W4B;
		case 5:
			defText = Textures.W5B;
		case 6:
			defText = Textures.W6B;
		case 7:
			defText = Textures.W7B;
		case 8:
			defText = Textures.W8B;
		}
	}
}