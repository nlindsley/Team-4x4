package com.tspgame;

public class CrackedWall extends Block {

	public CrackedWall(TSPGame game, int x, int y) {
		super(game, x, y, 3);
		defText = Textures.CWBLOCK;
	}

}
