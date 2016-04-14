package com.tspgame;

public class Boss extends Enemy {
	
	boolean mini = false;	// Boolean value to determine whether a mini boss or stage boss
	
	public Boss(TSPGame game, int x, int y) {
		super(game, x, y, true);	// Uses Character constructor

		defText = Textures.DEFAULT;	// Overwrites player texture
		lives = 50;
		lastFacing = -1;
		isEnemy = true;
		width = 57;
		height = 62;

		switch (game.levelNum){
		case 1:
			name = "Salae";
			lives = 13;
			defText = Textures.PLAYER0;
			break;
		case 2:
			name = "Keza";
			lives = 15;
			defText = Textures.PLAYER0;
			break;
		case 3:
			name = "Zefin";
			lives = 17;
			defText = Textures.PLAYER0;
			break;
		case 4:
			name = "Marble";
			lives = 19;
			defText = Textures.PLAYER0;
			break;
		case 5:
			name = "Sashen";
			lives = 17;
			defText = Textures.PLAYER0;
			break;
		case 6:
			name = "Blint";
			lives = 19;
			defText = Textures.PLAYER0;
			break;
		case 7:
			name = "Vivian";
			lives = 22;
			defText = Textures.PLAYER0;
			break;
		case 8:
			name = "TDK";
			lives = 25;
			defText = Textures.PLAYER1;
			break;
		}
	}
}
