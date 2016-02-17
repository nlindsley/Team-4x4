package com.tspgame;

public class Block extends Character {
	public Block(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor
		
		defText = Textures.BLOCK;	// Overwrites block texture
	}
}