package com.tspgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class that creates the textures for the game.
 * 
 * @author Charles Heckel
 */
public class Textures {
	// image should be size 2^n
	static Texture DEFAULT	= new Texture("default.png");
	static Texture BLOCK	= new Texture("block.png");
	static Texture PLAYER	= new Texture("Knight Front.png");
	static Texture BULLET	= new Texture("bullet.png");
	static Texture ENEMY	= new Texture("player.png");
	static Texture HUD		= new Texture("overlay.png");
	static Texture AMMO		= new Texture("ammo.png");
	static Texture BG		= new Texture("Grass.png");
}
