package com.tspgame;

public class Sword extends EquipableItem {
	Sword(){
		this.changeAcquired();

		name = "Sword";
		defText = Textures.SWORD;
		width = 96;
		height = 16;
	}
}
