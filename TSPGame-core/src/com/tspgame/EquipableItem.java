package com.tspgame;

import java.util.ArrayList;

public class EquipableItem implements EquipItem {
	
	private boolean acquired = false;
	private int damage = 1;
	private ArrayList<Block> inter = new ArrayList<Block>();

	@Override
	public boolean getAcquired() {
		return acquired;
	}

	@Override
	public int changeAcquired() {
		acquired = true;
		return 0;
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public int changeDamage(int dam) {
		damage = dam;
		return 0;
	}

	@Override
	public boolean getInteractions(Block b) {
		if(inter.contains(b)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void addInteraction(Block b) {
		inter.add(b);
		
	}
	
	public int attack(){
		return damage;
	}

}
