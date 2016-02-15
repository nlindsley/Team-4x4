package com.tspgame;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TSPGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerSprite;
	Texture bullet;
	Texture block;
	Texture enemySprite;
	Character player;
	Character enemy;
	List<Character> bullets = new ArrayList<Character>(); 	// of type character because they have the same traits
	List<Character> blockArr = new ArrayList<Character>();	// an array list allows for multiple on screen
	Listener keyBoardListener;
	int ammo = 10;

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
						Character block = new Character(this, i*32, blockHeight*32);
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
		
		batch = new SpriteBatch();
		playerSprite = new Texture("player.png");	// image should be size 2^n
		enemySprite = new Texture("player.png");
		bullet = new Texture("bullet.png");
		block = new Texture("block.png");
		
		player = new Character(this,100,200);	// default screen size= (640,480)
		enemy = new Character(this,540,200);
		
		keyBoardListener = new Listener();
		Gdx.input.setInputProcessor(keyBoardListener);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);	// r,g,b,alpha (values: 0-1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update();
		enemy.update();
		
		if(keyBoardListener.keysPressed[Keys.LEFT]) 	{ player.move(-5); }
		if(keyBoardListener.keysPressed[Keys.RIGHT]) 	{ player.move(5); }
		if(keyBoardListener.keysPressed[Keys.UP]) 		{ player.y += 5; }
//		if(keyBoardListener.keysPressed[Keys.DOWN]) 	{ player.y -= 5; }
		if(keyBoardListener.keysPressed[Keys.SPACE])	{
			if(ammo > 0) {
				ammo -= 1;
				Character bullet = new Character(this,(int)player.x, (int)player.y);
				bullet.setXVelocity(7);
				bullet.isBullet = true;
				bullets.add(bullet);
			}
		}

		// Everything that is drawn to the screen should be between ".begin" and ".end"
		batch.begin();
		
		batch.draw(playerSprite, (int)player.x, (int)player.y);
		batch.draw(enemySprite, (int)enemy.x, (int)enemy.y);
		
		for(int i = 0; i < bullets.size(); i += 1) {
			bullets.get(i).update();
			batch.draw(bullet, (int)bullets.get(i).x, (int)bullets.get(i).y);
			if(bullets.get(i).isCollidingWith(enemy)) {
				enemy.yVelocity = 5;
			}
		}

		for(int i = 0; i < bullets.size(); i += 1) {	// for each bullet
			if(!bullets.get(i).alive) {					// ... check if 'dead'
				bullets.remove(i);						// ... remove if 'dead'
				i -= 1;			// prevents the loop from skipping the next bullet
			}
		}
		for(int i = 0; i < blockArr.size(); i += 1) {	// for each bullet
			if(!blockArr.get(i).alive) {					// ... check if 'dead'
				blockArr.remove(i);						// ... remove if 'dead'
				i -= 1;			// prevents the loop from skipping the next bullet
			}
		}
		
		for(int i = 0; i < blockArr.size(); i += 1) {
			batch.draw(block, (int)blockArr.get(i).x, (int)blockArr.get(i).y);
		}
		
		batch.end();
		// Everything that is drawn to the screen should be between ".begin" and ".end"
	}
}
