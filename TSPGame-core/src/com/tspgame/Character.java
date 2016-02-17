package com.tspgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	int lives = 3;
	Texture defText = Textures.DEFAULT;

	public Character(TSPGame game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;

		yVelocity = 0.0;
	//	gravity = 0.15;
	}

	void setXVelocity(double v) { xVelocity = v; }	// mainly for bullets

	/** Part of collision handling. */
	public void xMove(int amount) {
		x += amount;	// allows movement (left-right)

		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(this.isCollidingWith(game.blockArr.get(i))) {
				x -= amount;	// move back if touching a block
				return;
			}
		}
	}
	
	/** Part of collision handling. */
	public void yMove(int amount) {
		y += amount;	// allows movement (left-right)

		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(this.isCollidingWith(game.blockArr.get(i))) {
				y -= amount;	// move back if touching a block
				return;
			}
		}
	}

	/** Called hundreds of times per second, updates movement speed. */
	public void update() {
		x += xVelocity;
		yVelocity -= gravity;
		y += yVelocity;

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
		if(y <= 0 && (!isBullet)) {	// stops us from falling through the floor, prevents bullets from respawning
			if(lives <= 1) {	// kill the player if player touches the ground with 1 life left
				lives = 0;
				alive = false;
			} else {
				lives -= 1;
				x = 100;	// move the player off the ground if touched
				y = 200;

				yVelocity = 0.0;
				// velocity = -velocity; // causes the player to bounce
			}
		}

		for(int i = 0; i < game.items.size(); i += 1) {
			if(this.isCollidingWith(game.items.get(i)) && !isBullet) {
				if(game.ammo+5 > 14) {	// prevent break ammo capacity
					game.ammo = 14;
				} else {
					game.ammo += 5;
				}
				game.items.get(i).alive = false;
			}
		}
	}

	boolean isCollidingWith(Character other) {
		// Create a bounding rectangle over each character
		Rectangle thisCharacter = new Rectangle((int)x, (int)y, 32, 32);
		Rectangle otherCharacter = new Rectangle((int)other.x, (int)other.y, 32, 32);

		return thisCharacter.overlaps(otherCharacter);
	}

	/** draw all the textures. */
	public void draw(SpriteBatch batch) {
		batch.draw(defText, (int)x, (int)y);
	}
}
