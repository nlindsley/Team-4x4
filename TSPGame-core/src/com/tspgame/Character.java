package com.tspgame;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/** This class is the super class for Background, Block, Bullet, Enemy, Item, and Player and defines attributes, movement, and collision. */
public class Character {
	TSPGame game;	// reference to the "game" itself, allows for reference from any point
	String name;
	double x;
	double y;
	double xVelocity;
	double yVelocity;
	boolean alive = true;
	boolean isBullet;
	boolean isPlayer;
	boolean isEnemy;
	boolean xPatrol;
	int width;
	int height;
	int lives;
	int lastFacing; // 0 is left, 1 is down, 2 is right, 3 is up
	Texture defText = Textures.DEFAULT;

	/**
	 * Constructor method for Character.
	 * @param game
	 * @param X coordinate the character is being spawned at
	 * @param Y coordinate the character is being spawned at
	 */
	public Character(TSPGame game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	/**
	 * Controls projectile speed on the X-axis
	 * @param Speed which the projectile is being set to
	 */
	void setXVelocity(double v) { xVelocity = v; }	// mainly for bullets

	/**
	 * Controls projectile speed on the Y-axis
	 * @param Speed which the projectile is being set to
	 */
	void setYVelocity(double v) { yVelocity = v; }	// mainly for bullets

	/** 
	 * Part of collision handling. Controls movement on the X-axis 
	 * @param The amount that the character is moving.
	 */
	public void xMove(double amount) {
		x += amount;	// allows movement (left-right)

		for(Enemy e : game.enemies) {
			if(this.isCollidingWith(e) && !isBullet) {
				x -= amount;
				game.keyBoardListener.keysPressed[Keys.LEFT] = false;
				game.keyBoardListener.keysPressed[Keys.RIGHT] = false;
			}
		}
		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(this.isCollidingWith(game.blockArr.get(i))) {
				x -= amount;	// move back if touching a block
				return;
			}
		}
	}

	/** 
	 * Part of collision handling. Controls movement on the Y-axis 
	 * @param The amount that the character is moving.
	 */
	public void yMove(double amount) {
		y += amount;	// allows movement (up-down)

		for(Enemy e : game.enemies) {
			if(this.isCollidingWith(e) && !isBullet) { 
				y -= amount;
				game.keyBoardListener.keysPressed[Keys.UP] = false;
				game.keyBoardListener.keysPressed[Keys.DOWN] = false;
			}
		}
		for(int i = 0; i < game.blockArr.size(); i += 1) {
			if(this.isCollidingWith(game.blockArr.get(i))) {
				y -= amount;	// move back if touching a block
				return;
			}
		}
	}

	/** 
	 * Updates all character positions, life, and collisions. 
	 */
	public void update() {
		if(game.player.lives <= 0) {	// kill the player if 0 lives remain
			lives = 0;
			alive = false;
			game.player = null;
			game.loadRoom("DeadState.txt");
			game.deadState = true;
		}
		
		x += xVelocity;
		y += yVelocity;

		for(Block b : game.blockArr) {		// for each block
			for(Enemy e : game.enemies) {
				if(e.isCollidingWith(b)) {	// if enemy touches it
					e.turnEnemy();			// turn enemy
				}
				if(game.player.isCollidingWith(e)) {	// if player touches enemy
					e.turnEnemy(); 						// turn enemy
					game.player.lives -= 5; 			// player loses health
				}
				for(Enemy f: game.enemies) {	// for every other enemy
					if(e.isCollidingWith(f) && (!e.equals(f))) {
						e.turnEnemy();			// turn both enemies
						f.turnEnemy();
					}
				}
			}
			if(b.isCollidingWith(this) && isBullet) {	// if bullet touches it
				this.alive = false;						// kill bullet
				if(!b.unbreakable) {					// if block is breakable
					b.alive = false;					// kill block
				}
			}
		}
		
		for(Boss b : game.bosses) {
			// X movement
			if(b.x - game.player.x >= 10) { // move closer to player
				if(b.mini) { b.xMove(-1.5); }
				else { b.xMove(-3); }
			} else if(b.x - game.player.x <= -10){ // 'else-if' to prevent boss from twitching on approach
				if(b.mini) { b.xMove(1.5); }
				else { b.xMove(3); }
			}
			// Y movement
			if(b.y - game.player.y >= 10) { // move closer to player
				if(b.mini) { b.yMove(-1.5); }
				else { b.yMove(-3); }
			} else if(b.y - game.player.y <= -10){ // 'else-if' to prevent boss from twitching on approach
				if(b.mini) { b.yMove(1.5); }
				else { b.yMove(3); }
			}
			
			if(b.isCollidingWith(game.player)) {
				game.player.lives -= 1;
			}
		}
		
		
		if(isPlayer) {	// handles room transitions
			if(y <= 0) {
				game.loadRoom(game.rooms[game.player.currentRoomX][++game.player.currentRoomY]);
				y = 480;	// 512 - 32
			}
			if(y >= 512) {
				game.loadRoom(game.rooms[game.player.currentRoomX][--game.player.currentRoomY]);
				y = 32;
			}
			if(x <= 0) {
				game.loadRoom(game.rooms[--game.player.currentRoomX][game.player.currentRoomY]);
				x = 480;	// 512 - 32
			}
			if(x >= 512) {
				game.loadRoom(game.rooms[++game.player.currentRoomX][game.player.currentRoomY]);
				x = 32;
			}
		}

		for(int i = 0; i < game.items.size(); i += 1) {
			if(game.player.isCollidingWith(game.items.get(i))) {
				if(game.ammo == 14) {
					continue;
				} else if(game.ammo+5 > 14) {	// prevent break ammo capacity
					game.ammo = 14;
				} else if( game.ammo+5 <= 14) {
					game.ammo += 5;
				}
				game.items.get(i).alive = false;
			}
		}
	}

	/**
	 * This class checks if two characters are colliding and returns a boolean value.
	 * @param The unit possibly being collided with.
	 * @return True if they have collided; false otherwise.
	 */
	boolean isCollidingWith(Character other) {
		// Create a bounding rectangle over each character
		Rectangle thisCharacter = new Rectangle((int)x, (int)y, width, height);
		Rectangle otherCharacter = new Rectangle((int)other.x, (int)other.y, other.width, other.height);

		return thisCharacter.overlaps(otherCharacter);
	}

	/** 
	 * Draw all the textures. 
	 * @param The batch used to draw.
	 */
	public void draw(SpriteBatch batch) {
		batch.draw(defText, (int)x, (int)y);
	}
}
