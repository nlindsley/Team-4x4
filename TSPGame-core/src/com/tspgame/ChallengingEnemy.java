package com.tspgame;

/**
 * Creates a more challenging enemy for some of the rooms with fewer enemies.
 * Thought is to have them deal more damage as well, but not sure if possible.
 */

public class ChallengingEnemy extends Enemy {

	public ChallengingEnemy(TSPGame game, int x, int y) {
		super(game, x, y);
		lives = 7;
		
	}

}
