package com.tspgame;

public class Bow extends EquipableItem {

	private int speed = 3;
	
	Bow() {
		defText = Textures.DEFAULT;
	}
	
	public int getSpeed(){
		return speed;
	}
}
