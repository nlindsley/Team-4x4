package com.tspgame;

public class Lantern extends EquipableItem {
	Lantern(){
		addInteraction(new SnowBlock);
		changeDamage(0);
	}
}
