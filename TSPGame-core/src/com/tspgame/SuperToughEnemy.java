package com.tspgame;

/**
 * Creates a super tough enemy for some of the levels.
 *	Does even more damage than challenging enemy.
 */

public class SuperToughEnemy extends Enemy{

	public SuperToughEnemy(TSPGame game, int x, int y) {
		super(game, x, y);
		lives = 10;
	}

}
