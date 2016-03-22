package com.tspgame;

import java.util.ArrayList;

/** Class which creates the player character */
public class Player extends Character {
	
	private int spot = 0;
	private ArrayList<EquipableItem> inventory = new ArrayList<EquipableItem>();
	private EquipableItem current;
	
	public Player(TSPGame game, int x, int y) {
		super(game, x, y);	// Uses Character constructor

		defText = Textures.PLAYER1;	// Overwrites player texture
		lives = 100;
		lastFacing = 1;
		isPlayer = true;
		width = 57;
		height = 57;
		
		fillInventory();
		current = inventory.get(spot);
	}

	boolean isKnight	= false;
	boolean isMage		= false;
	boolean isArcher	= false;
	int currentRoomX;
	int currentRoomY;

	/**
	 * Uses the selected class's attack ability.
	 */
	public void attack() {
		if(this.isKnight) {
			EquipableItem sword = inventory.get(0);
			for(Enemy e : game.enemies) {
				if(sword.isCollidingWith(e)) {
					e.lives -= sword.getDamage();
				}
			}
		} else if(this.isMage) {
			if(game.ammo > 0) {
				game.ammo -= 1;
				Bullet bullet = new Bullet(game,(int)this.x, (int)this.y+8);
				if(this.lastFacing == 3)	{ bullet.setYVelocity(15); }
				if(this.lastFacing == 1) 	{ bullet.setYVelocity(-15); }
				if(this.lastFacing == 0) 	{ bullet.setXVelocity(-15); }
				if(this.lastFacing == 2) 	{ bullet.setXVelocity(15); }
				bullet.isBullet = true;
				game.bullets.add(bullet);
			}
		} else if(this.isArcher) {

		}
	}
	
	/**
	 * Creates and initializes an array of inventory items.
	 */
	public void accessInventory() {
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
	
	/**
	 * Adds new items the the players' inventory.
	 */
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
