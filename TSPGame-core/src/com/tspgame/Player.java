package com.tspgame;

import java.util.ArrayList;

/**
 * Class which creates the player character
 *
 */

public class Player extends Character {
	private int spot = 0;
	private ArrayList<EquipableItem> inventory = new ArrayList<EquipableItem>();
	private EquipableItem current;
	public Player(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor

		defText = Textures.PLAYER1;	// Overwrites player texture
		lives = 30;
		lastFacing = 1;
		isPlayer = true;
		width = 57;
		height = 60;
		
		fillInventory();
		current = inventory.get(spot);
	}
	
	int currentRoomX;
	int currentRoomY;
	
	public void accessInventory(){
		spot++;
		if(spot>6){
			spot = 0;
		}
		else{
			boolean access = inventory.get(spot).getAcquired();
			if(access == false){
				spot = 0;
			}
		}
		current = inventory.get(spot);
	}
	private void fillInventory(){
		inventory.add(new Sword());
		inventory.add(new Scythe());
		inventory.add(new Lantern());
		inventory.add(new Bombs());
		inventory.add(new MegaShield());
		inventory.add(new Bow());
		inventory.add(new LightSword());
	}
}
