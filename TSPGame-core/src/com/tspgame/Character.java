package com.tspgame;

import com.badlogic.gdx.math.Rectangle;

public class Character {
	TSPGame game;	// reference to the "game" itself, allows for reference from any point
	double x;
	double y;
	double yVelocity;
	double xVelocity;
	double gravity;
	boolean alive = true;
	boolean isBullet;
	
	public Character(TSPGame game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
		
		yVelocity = 0.0;
		gravity = 0.15;
	}

	void setXVelocity(double v) { xVelocity = v; }
	
	/** Part of collision handling. */
	public void move(int amount) {
		x += amount;	// allows movement (left-right)
		
		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(this.isCollidingWith(game.blockArr.get(i))) {
				x -= amount;	// move back if touching a block
				return;
			}
		}
	}
	
	/** Called hundreds of times per second, updates movement speed. */
	public void update() {
		yVelocity -= gravity;
		y += yVelocity;
		x += xVelocity;
		
		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(game.blockArr.get(i).isCollidingWith(this)) {
				y -= yVelocity;
				yVelocity = 0;
				
				// removes the bullet when it hits a block
				if(isBullet) {
					alive = false;
					game.blockArr.get(i).alive = false;
				}
			}
		}
		if(y <= 0) {	// stops us from falling through the floor
			y = 0;
			yVelocity = 0.0;
			// velocity = -velocity; // causes the player to bounce
		}
	}
	
	boolean isCollidingWith(Character other) {
		// Create a bounding rectangle over each character
		Rectangle thisCharacter = new Rectangle((int)x, (int)y, 32, 32);
		Rectangle otherCharacter = new Rectangle((int)other.x, (int)other.y, 32, 32);
		
		return thisCharacter.overlaps(otherCharacter);
	}
}
