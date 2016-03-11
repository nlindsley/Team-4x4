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
	List<Enemy>		enemies		= new ArrayList<Enemy>();
	List<Bullet>	bullets		= new ArrayList<Bullet>(); 		// of type character because they have the same traits
	List<Block>		blockArr	= new ArrayList<Block>();		// an array list allows for multiple on screen
	List<Background>bgArr		= new ArrayList<Background>();
	List<Item>		items		= new ArrayList<Item>();		// list of active items
	List<String[][]>levels		= new ArrayList<String[][]>();	// contains all levels for the game
	String[][]		rooms		= new String[8][8];				// contains all rooms for the level
	Listener		keyBoardListener;
	int ammo = 10;
	BitmapFont font;	// pre-made font for libgdx
	int screenHeight;
	int screenWidth;

	/** Initialize all variables when game starts. */
	@Override
	public void create () {		// default screen size= (640,480) change in respective platform project
		loadLevel("level1.txt");
		loadRoom("l1r1.txt");
		player.currentRoomX = 0;	// set player tracking to first room on map (bottom-left corner)
		player.currentRoomY = 4;

		batch	= new SpriteBatch();
		font	= new BitmapFont();				// default 15pt arial from libgdx JAR file
		keyBoardListener = new Listener();
		Gdx.input.setInputProcessor(keyBoardListener);
	}
	
	/** loads the level based on a file input. */
	public void loadLevel(String levelx) {
		File loader;
		try {
			loader = new File(levelx);
			Scanner in = new Scanner(loader);

			int j = 0;
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] levelGrid = line.split("	");	// separated by tabs, not spaces
				
				for(int i = 0; i < levelGrid.length; i += 1) {
					if(levelGrid[i].equals("0")) { continue; }
					else {
						rooms[i][j] = levelGrid[i];
					}
				}
				j += 1;
			}
		} catch (FileNotFoundException e) {
			System.out.println("CUSTOM ERROR: NEEDS A LEVEL FILE");
		}
	}
	
	/** loads the room based on a file input. */
	public void loadRoom(String lxrx) {
		File loader;
		try {
			loader = new File(lxrx);// levelXX.txt is found in platform folder (-desktop/-android)
			Scanner in = new Scanner(loader);
			int blockHeight = 0;	// file is read in line-by-line, so we'll use a simple counter for height
									// level will be upside-down from txt file

			// remove everything from the previous level before adding next elements
			for(int i = 0; i < bgArr.size(); i += 1)	{ bgArr.remove(i);		i -= 1;	}
			for(int i = 0; i < bullets.size(); i += 1)	{ bullets.remove(i);	i -= 1;	}
			for(int i = 0; i < blockArr.size(); i += 1)	{ blockArr.remove(i);	i -= 1;	}
			for(int i = 0; i < enemies.size(); i += 1)	{ enemies.remove(i);	i -= 1;	}
			for(int i = 0; i < items.size(); i += 1)	{ items.remove(i);		i -= 1;	}
			
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] levelGrid = line.split(" ");	// puts everything in-between white-spaces into an array spot

				for(int i = 0; i < levelGrid.length; i += 1) {
					Background grass = new Background(this, i*32, blockHeight*32);
					bgArr.add(grass);
					if(levelGrid[i].equals("1")) {
						Block block = new Block(this, i*32, blockHeight*32);
						if(i == 0 || i == levelGrid.length-1 || blockHeight == 0 || blockHeight == levelGrid.length-1) {
							block.unbreakable = true;
						}
						blockArr.add(block);
					}
					if(levelGrid[i].equals("p")) {
						player	= new Player(this,i*32,blockHeight*32);
					}
					if(levelGrid[i].equals("x")) {
						enemies.add(0, new Enemy(this,i*32,blockHeight*32));
						enemies.get(0).setXVelocity(1);
						enemies.get(0).xPatrol = true;
					}
					if(levelGrid[i].equals("y")) {
						enemies.add(0, new Enemy(this,i*32,blockHeight*32));
						enemies.get(0).setYVelocity(1);
						enemies.get(0).xPatrol = false;
					}
					if(levelGrid[i].equals("i")) {
						items.add(new Item(this, i*32,blockHeight*32));
					}
				}
				blockHeight += 1;
				screenWidth = levelGrid.length;
			}
			screenHeight = blockHeight;
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("CUSTOM ERROR: NEEDS A ROOM FILE");
		}
	}

	/** gets called hundreds of times per second. Similar to tick or frames. */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);	// r,g,b,alpha (values: 0-1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update();
		for(Enemy e : enemies) { e.update(); }

		// Player Management
		if(player.alive) {
			if(keyBoardListener.keysPressed[Keys.LEFT])		{ player.xMove(-5);	player.lastFacing = 0; player.defText = Textures.PLAYER0; }
			if(keyBoardListener.keysPressed[Keys.RIGHT])	{ player.xMove(5);	player.lastFacing = 2; player.defText = Textures.PLAYER2; }
			if(keyBoardListener.keysPressed[Keys.UP])		{ player.yMove(5);	player.lastFacing = 3; player.defText = Textures.PLAYER3; }
			if(keyBoardListener.keysPressed[Keys.DOWN])		{ player.yMove(-5);	player.lastFacing = 1; player.defText = Textures.PLAYER1; }
			if(keyBoardListener.keysPressed[Keys.Z])		{
				keyBoardListener.keysPressed[Keys.Z] = false;	// fires once per press
				if(ammo > 0) {
					ammo -= 1;
					Bullet bullet = new Bullet(this,(int)player.x, (int)player.y+8);
					if(keyBoardListener.keysPressed[Keys.UP]	||	player.lastFacing == 3)		{ bullet.setYVelocity(15); }
					if(keyBoardListener.keysPressed[Keys.DOWN]	||	player.lastFacing == 1) 	{ bullet.setYVelocity(-15); }
					if(keyBoardListener.keysPressed[Keys.LEFT]	||	player.lastFacing == 0) 	{ bullet.setXVelocity(-15); }
					if(keyBoardListener.keysPressed[Keys.RIGHT]	||	player.lastFacing == 2) 	{ bullet.setXVelocity(15); }
					bullet.isBullet = true;
					bullets.add(bullet);
				}
			}
		}
		
		// Everything that is drawn to the screen should be between ".begin" and ".end"
		batch.begin();

		for(Background b: bgArr)	{	b.draw(batch); }
		for(Bullet b	: bullets)	{	b.draw(batch); }
		for(Block b		: blockArr)	{	b.draw(batch); }
		for(Enemy e		: enemies)	{	e.draw(batch); }
		for(Item i		: items)	{	i.draw(batch); }
		player.draw(batch);

		// bullet management
		for(int i = 0; i < bullets.size(); i += 1) {
			bullets.get(i).update();
			
			for(Enemy e : enemies) {
				if(bullets.get(i).isCollidingWith(e)) {
					e.lives -= 1;
					bullets.get(i).alive = false;
				}
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
		// enemy management
		for(int i = 0; i < enemies.size(); i += 1) {
			if(enemies.get(i).lives <= 0) {
				enemies.remove(i);
				i -= 1;
			}
		}
		// HUD management
		batch.draw(Textures.HUD,  0, (screenHeight*32));	// must go last, has to display over everything else
		font.draw(batch,  "Your lives: " + player.lives,  10, (screenHeight*32)+48);
		for(int i = 0; i < ammo; i += 1) {
			batch.draw(Textures.BULLET,  i*7,  (screenHeight*32));
		}

		batch.end();
		// Everything that is drawn to the screen should be between ".begin" and ".end"
	}
}
