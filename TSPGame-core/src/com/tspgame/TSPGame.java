package com.tspgame;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
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
	String[]		levels;		// contains all levels for the game
	String[][]		rooms		= new String[8][8];				// contains all rooms for the level
	List<Boss>		bosses		= new ArrayList<Boss>();
	List<Enemy>		enemies		= new ArrayList<Enemy>();
	List<Bullet>	bullets		= new ArrayList<Bullet>(); 		// of type character because they have the same traits
	List<Block>		blockArr	= new ArrayList<Block>();		// an array list allows for multiple on screen
	List<Background>bgArr		= new ArrayList<Background>();
	List<Item>		items		= new ArrayList<Item>();		// list of active items
	Listener		keyBoardListener;
	BitmapFont 		font;	// pre-made font for libgdx

	private State state = State.RUN;
	
	 private int enemiesKilled = 0;
	
	boolean deadState = false;
	boolean miniKilled = false;
	boolean bossKilled = false;
	
	int[] startRoom = new int[2];	// startRoom[0] = x coord, startRoom[1] = y coord
	int[] startSpot = new int[2];	// startSport[0] = x coord of startRoom[0]
	int screenHeight;
	int screenWidth;
	int levelNum = 1;
	int ammo = 10;

	/** Initialize all variables when game starts. */
	@Override
	public void create () {		// default screen size= (640,480) change in respective platform project
		levels = new String[] {"", "level1maps", "level2maps", "level3maps", "level4maps", "level5maps", "level6maps", "level7maps", "level8maps"};
		loadLevel("level1maps/level1.txt");
		
		player.isKnight = true;	// TEMPORARY: will eventually have selection screen

		batch	= new SpriteBatch();
		font	= new BitmapFont();		// default 15pt arial from libgdx JAR file
		keyBoardListener = new Listener();
		Gdx.input.setInputProcessor(keyBoardListener);
	}
	
	/** loads the level based on a file input. */
	public void loadLevel(String levelx) {
		miniKilled = false;
		bossKilled = false;
		System.out.println("level " + levelNum);
		System.out.println("Starting room: " + startRoom[0] + " " + startRoom[1]);
		
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
					if(rooms[i][j].contains("r1.txt")) {
						startRoom[0] = i;
						startRoom[1] = j;
					}
				}
				j += 1;
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("CUSTOM ERROR: NEEDS A LEVEL FILE");
		}
		
		loadRoom(levels[levelNum] + "/l" + levelNum + "r1.txt");
	}
	
	/** loads the room based on a file input. */
	public void loadRoom(String lxrx) {
		System.out.println(lxrx);
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
			for(int i = 0; i < bosses.size(); i += 1)	{ bosses.remove(i);		i -= 1; }
			
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] levelGrid = line.split(" ");	// puts everything in-between white-spaces into an array spot

				for(int i = 0; i < levelGrid.length; i += 1) {
					Background grass = new Background(this, i*32, blockHeight*32, levelNum);
					bgArr.add(grass);
					if(levelGrid[i].equals("1")) {
						Block block = new Block(this, i*32, blockHeight*32, levelNum);
						if(i == 0 || i == levelGrid.length-1 || blockHeight == 0 || blockHeight == levelGrid.length-1) {
							block.unbreakable = true;
						}
						blockArr.add(block);
					}
					if(levelGrid[i].equals("p")) {
						if(player == null) {
							player	= new Player(this,i*32,blockHeight*32);
						}
						startSpot[0] = i*32;
						startSpot[1] = blockHeight*32;
						player.currentRoomX = startRoom[0];	// set player tracking to first room on map
						player.currentRoomY = startRoom[1];
					}
					if(levelGrid[i].equals("e")) {
						enemies.add(0, new Enemy(this,i*32,blockHeight*32, true));
						enemies.get(0).setXVelocity(0);
						enemies.get(0).defText = Textures.ENEMY1;
					}
					if(levelGrid[i].equals("x")) {
						enemies.add(0, new Enemy(this,i*32,blockHeight*32,true));
						enemies.get(0).setXVelocity(1);
						//enemies.get(0).xPatrol = true;
					}
					if(levelGrid[i].equals("y")) {
						enemies.add(0, new Enemy(this,i*32,blockHeight*32,false));
						enemies.get(0).setYVelocity(1);
						//enemies.get(0).xPatrol = false;
					}
					if(levelGrid[i].equals("mb")) {
						if(miniKilled) { continue; }
						bosses.add(0, new Boss(this,i*32,blockHeight*32));
						bosses.get(0).mini = true;
					}
					if(levelGrid[i].equals("b")) {
						if(bossKilled) { continue; }
						bosses.add(0, new Boss(this,i*32,blockHeight*32));
						bosses.get(0).mini = false;
					}
					if(levelGrid[i].equals("i")) {
						//items.add(new Item(this, i*32,blockHeight*32));
						
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
		System.out.println("player in: " + player.currentRoomX + " " + player.currentRoomY);
	}

	/** gets called hundreds of times per second. Similar to tick or frames. */
	@Override
	public void render() {
		switch(state){
		
		case RUN:	// ----------------------------------------------------------------------------------------------------------------
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
			//X key now controls the inventory. One push advances through the inventory one item at a time.
			if(keyBoardListener.keysPressed[Keys.X]){
				keyBoardListener.keysPressed[Keys.X] = false;
				player.accessInventory();
			}
			if(keyBoardListener.keysPressed[Keys.A]){
				keyBoardListener.keysPressed[Keys.A] = false;
				state = State.PAUSE;
				break;
			}
			if(keyBoardListener.keysPressed[Keys.ALT_LEFT]) {	// developer only
				for(Enemy e : enemies)	{ e.lives = 0; }		// developer only
				for(Boss b : bosses)	{ b.lives = 0; }		// developer only
			}													// developer only
		}
		
		// Everything that is drawn to the screen should be between ".begin" and ".end"
		batch.begin();

		for(Background b: bgArr)	{	b.draw(batch); }
		for(Bullet b	: bullets)	{	b.draw(batch); }
		for(Block b		: blockArr)	{	b.draw(batch); }
		for(Enemy e		: enemies)	{	e.draw(batch); }
		for(Item i		: items)	{	i.draw(batch); }
		for(Boss b		: bosses)	{	b.draw(batch); } 
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
				enemies.get(i).dropItem();
				enemies.remove(i);
				i -= 1;
				enemiesKilled++;
			}
		}
		// boss management
		for(int i = 0; i < bosses.size(); i += 1) {
			if(bosses.get(i).lives <= 0) {
				if(bosses.get(i).mini) { miniKilled = true; }
				else { bossKilled = true; }
				bosses.remove(i);

				if(bossKilled && miniKilled) {
					player.lives = 100;
					loadLevel(levels[++levelNum] + "/level" + levelNum + ".txt");
					player.x = startSpot[0];
					player.y = startSpot[1];
				}
			}
		}
		// Inventory management
		if(keyBoardListener.keysPressed[Keys.Z]) {
			batch.draw(player.getSelectedInventory().defText, (float)player.x, (float)player.y);
		}
		// HUD management
		batch.draw(Textures.HUD,  0, (screenHeight*32));	// must go last, has to display over everything else
		font.draw(batch,  "Your lives: " + player.lives,  10, (screenHeight*32)+48);
		for(int i = 0; i < ammo; i += 1) {
			batch.draw(Textures.BULLET,  i*7,  (screenHeight*32));
		}

		if(keyBoardListener.keysPressed[Keys.Z])		{
			keyBoardListener.keysPressed[Keys.Z] = false;	// fires once per press
			player.attack();
			batch.draw(player.getSelectedInventory().defText, (float)player.getSelectedInventory().x, (float)player.getSelectedInventory().y);
		}
		
		if(deadState) {
			//DBHookUp db = new DBHookUp();
			//db.updateDB(enemiesKilled);
			//font.draw(batch, "You have died...\nEnjoy the afterlife", screenWidth*16, screenHeight*20);
			//font.draw(batch, "kill me to restart", screenWidth*4, screenHeight*14);
			//font.draw(batch, "kill me to exit", screenWidth*24, screenHeight*14);
			Gdx.app.exit();
			
		}
		batch.end();
		break;
		// Everything that is drawn to the screen should be between ".begin" and ".end"
		case PAUSE:	// ----------------------------------------------------------------------------------------------------------------
			if(keyBoardListener.keysPressed[Keys.A]){
				keyBoardListener.keysPressed[Keys.A] = false;
				state = State.RUN;
				break;
			}
			// Everything that is drawn to the screen should be between ".begin" and ".end"
			batch.begin();

			for(Background b: bgArr)	{	b.draw(batch); }
			for(Bullet b	: bullets)	{	b.draw(batch); }
			for(Block b		: blockArr)	{	b.draw(batch); }
			for(Enemy e		: enemies)	{	e.draw(batch); }
			for(Item i		: items)	{	i.draw(batch); }
			for(Boss b		: bosses)	{	b.draw(batch); } 
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
					enemies.get(i).dropItem();
					enemies.remove(i);
					i -= 1;
				}
			}
			// boss management
			for(int i = 0; i < bosses.size(); i += 1) {
				if(bosses.get(i).lives <= 0) {
					if(bosses.get(i).mini) { miniKilled = true; }
					else { bossKilled = true; }
					bosses.remove(i);

					if(bossKilled && miniKilled) {
						player = null;	// nulling the player makes respawning in the next floor easier
						loadLevel(levels[++levelNum] + "/level" + levelNum + ".txt");
					}
				}
			}
			// HUD management
			batch.draw(Textures.HUD,  0, (screenHeight*32));	// must go last, has to display over everything else
			font.draw(batch,  "Your lives: " + player.lives,  10, (screenHeight*32)+48);
			if(state == State.PAUSE) {
				font.setColor(Color.ORANGE);
				font.getData().setScale(3,3);
				font.draw(batch, "Paused", screenWidth*11, screenHeight*19);
				font.setColor(Color.WHITE);
				font.getData().setScale(1,1);
			}
			for(int i = 0; i < ammo; i += 1) {
				batch.draw(Textures.BULLET,  i*7,  (screenHeight*32));
			}
			
			if(deadState) {
				font.setColor(Color.ORANGE);
				font.draw(batch, "You have died...\nEnjoy the afterlife", (float)player.x-16, (float)player.y+96);
				font.draw(batch, "kill me to restart", screenWidth*4, screenHeight*14);
				font.draw(batch, "kill me to exit", screenWidth*24, screenHeight*14);
				font.setColor(Color.WHITE);
			}
			batch.end();
			break;
		case STOPPED:
			break;
		case RESUME:
			break;
		}
	}
	public int getScore(){
		return enemiesKilled;
	}
}
