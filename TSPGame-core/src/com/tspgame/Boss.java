package com.tspgame;

/**
 * Base Class for bosses. Gives them three more health than super tough enemies.
 *
 */

public class Boss extends Enemy{

	public Boss(TSPGame game, int x, int y) {
		super(game, x, y);
		lives = 13;
	}

}
