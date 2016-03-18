package com.tspgame;

public class Bombs extends EquipableItem {
	Bombs(){
		addInteraction(new CrackedWall);
		changeDamage(2);
	}
}
