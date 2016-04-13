package com.tspgame;

import com.badlogic.gdx.graphics.Texture;

public class Sword extends EquipableItem {
	Sword(){
		this.changeAcquired();

		name = "Sword";
		defText = Textures.SWORD1;
		width = 96;
		height = 16;
	}
}
