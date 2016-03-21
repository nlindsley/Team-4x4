package com.tspgame;

public class LightSword extends EquipableItem {
	
	LightSword(){
		changeDamage(14);
		
		defText = Textures.DEFAULT;
	}
	
	public void checkDK(Enemy e){
		if(e.name.equals("TDK")){
			changeDamage(1);
		}
	}
}
