package com.tspgame;

/** This class creates the blocks used as barriers for the edges, as well as used to create puzzles. */
public class Block extends Character {

	public boolean unbreakable = false;	// Boolean check to ensure that the player can't walk off the edge of the map.

	public Block(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor
		
		defText = Textures.BLOCK;	// Overwrites block texture
		width = 32;
		height = 32;
	}
}