package com.tspgame;

public class Bullet extends Character {
	public Bullet(TSPGame game, int x, int y) {
		super(game, x, y);
		
		defText = Textures.BULLET;
	}
}
