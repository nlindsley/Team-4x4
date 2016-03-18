package com.tspgame;

public class Boss extends Enemy {

	public Boss(TSPGame game, int x, int y, int world) {
		super(game, x, y);
		switch (world){
		case 1:
			name = "Salae";
			lives = 13;
			break;
		case 2:
			name = "Keza";
			lives = 15;
			break;
		case 3:
			name = "Zefin";
			lives = 17;
			break;
		case 4:
			name = "Marble";
			lives = 19;
			break;
		case 5:
			name = "Sashen";
			lives = 17;
			break;
		case 6:
			name = "Blint";
			lives = 19;
			break;
		case 7:
			name = "Vivian";
			lives = 22;
			break;
		case 8:
			name = "TDK";
			lives = 25;
			break;
		}
	}

}
