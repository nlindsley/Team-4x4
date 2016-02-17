package com.tspgame;

import java.io.*;
import java.util.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** 
 * Team 4x4, CS3141-Team Software Project, Spring 2016, Video Game
 * 
 * @author Alexander Friebe
 * @author Charles Heckel
 * @author Nick Lindsley
 * @author Ben McWethy
 */
public class TSPGame extends ApplicationAdapter {
	SpriteBatch 	batch;
	Player			player;
	Player			enemy;
	List<Bullet>	bullets		= new ArrayList<Bullet>(); 	// of type character because they have the same traits
	List<Block>		blockArr	= new ArrayList<Block>();	// an array list allows for multiple on screen
	List<Item>		items		= new ArrayList<Item>();	// list of active items
	Listener		keyBoardListener;
	int ammo = 13;
	BitmapFont font;	// pre-made font for libgdx

	/** loads the level based on a file input of 0's (air), 1's (blocks), and x's(spawns). */
	public void loadLevel() {
		File loader;
		try {
			loader = new File("level00.txt");	// levelXX.txt is found in platform folder (-desktop/-android)
			Scanner in = new Scanner(loader);
			int blockHeight = 0;	// file is read in line-by-line, so we'll use a simple counter for height
									// level will be upside-down from txt file
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] levelGrid = line.split(" ");	// puts everything inbetween whitespaces into an array spot

				for(int i = 0; i < levelGrid.length; i += 1) {
					if(levelGrid[i].equals("1")) {
						Block block = new Block(this, i*32, blockHeight*32);
						blockArr.add(block);
					}
				}
				blockHeight += 1;
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("CUSTOM ERROR: NEEDS A LEVEL FILE");
			e.printStackTrace();
		}
	}

	/** Initialize all variables when game starts. */
	@Override
	public void create () {
		loadLevel();

		batch	= new SpriteBatch();
		font	= new BitmapFont();				// default 15pt arial from libgdx JAR file
		player	= new Player(this,100,200);		// default screen size= (640,480)
		enemy	= new Player(this,540,200);
		items.add(new Item(this, 500, 300));	// TEMPORARY: HARD CODED
		keyBoardListener = new Listener();
		Gdx.input.setInputProcessor(keyBoardListener);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);	// r,g,b,alpha (values: 0-1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update();
		enemy.update();

		if(keyBoardListener.keysPressed[Keys.LEFT]) 	{ player.xMove(-5); }
		if(keyBoardListener.keysPressed[Keys.RIGHT]) 	{ player.xMove(5); }
		if(keyBoardListener.keysPressed[Keys.UP]) 		{ player.yMove(5); }
		if(keyBoardListener.keysPressed[Keys.DOWN]) 	{ player.yMove(-5); }
		if(keyBoardListener.keysPressed[Keys.SPACE])	{
			keyBoardListener.keysPressed[Keys.SPACE] = false;	// fires once per press
			if(ammo > 0) {
				ammo -= 1;
				Bullet bullet = new Bullet(this,(int)player.x, (int)player.y);
				bullet.setXVelocity(35);
				bullet.isBullet = true;
				bullets.add(bullet);
			}
		}

		// Everything that is drawn to the screen should be between ".begin" and ".end"
		batch.begin();

		player.draw(batch);
		enemy.draw(batch);

		for(Bullet c : bullets) { c.draw(batch); }
		for(Block c : blockArr) { c.draw(batch); }
		for(Item c : items) { c.draw(batch); }

		// bullet management
		for(int i = 0; i < bullets.size(); i += 1) {
			bullets.get(i).update();

			if(bullets.get(i).isCollidingWith(enemy)) {
				enemy.yVelocity = 5;
			}
			if(!bullets.get(i).alive) {	// ... check if 'dead'
				bullets.remove(i);		// ... remove if 'dead'
				i -= 1;					// prevents the loop from skipping the next bullet
			}
		}
		// block management
		for(int i = 0; i < blockArr.size(); i += 1) {	// for each bullet
			if(!blockArr.get(i).alive) {	// ... check if 'dead'
				blockArr.remove(i);			// ... remove if 'dead'
				i -= 1;						// prevents the loop from skipping the next bullet
			}
		}
		// item management
		for(int i = 0; i < items.size(); i += 1) {
			if(!items.get(i).alive) {
				items.remove(i);
				i -= 1;
			}
		}

		// HUD management
		batch.draw(Textures.HUD,  0,  416);	// must go last, has to display over everything else
		font.draw(batch,  "Your lives: " + player.lives,  10, 470);
		for(int i = 0; i < ammo; i += 1) {
			batch.draw(Textures.BULLET,  i*7,  416);
		}

		batch.end();
		// Everything that is drawn to the screen should be between ".begin" and ".end"
	}
}
