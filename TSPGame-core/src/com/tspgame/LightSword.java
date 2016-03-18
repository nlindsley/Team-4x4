package com.tspgame;

public class LightSword extends EquipableItem {
	
	LightSword(){
		changeDamage(14);
	}
	
	public void checkDK(Enemy e){
		if(e.name.equals("TDK")){
			changeDamage(1);
		}
	}
}
