package com.tspgame;

/**
 * Class which controls items.
 *
 */

public class Item extends Character {
	public Item(TSPGame game, int x, int y) {
		super(game, x, y);
		
		defText = Textures.AMMO;
		width = 32;
		height = 32;
	}
}
