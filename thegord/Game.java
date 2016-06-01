package com.erez.thegord;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import com.erez.thegord.input.Keyboard;
import com.erez.thegord.input.Mouse;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L; //by convention
	
	public static int width = 1200;
	public static int height = width/16 * 9;

	public static String title = "The Gord";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Mouse mouse;
	
	public Building mouseBuilding;
	private boolean running;
	
	public GameBoard gBoard;
	public Panel gPanel;
	public Screen introScreen;
	public Screen victoryScreen;
	public Screen lossScreen;
	
	public boolean battleMode;
	public int displayScreen = 0;
	public int money;
	public int gordHealth;
	public int gameLevel;
	public 	int enemyTick;
	
	public List<Component> components = new LinkedList<Component>();
	public List<Entity> entities = new LinkedList<Entity>();
	public List<Entity> toAdd = new LinkedList<Entity>();
    
	Enemy[][] levelEnemies;
	
	int pos;
	
	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
	
		frame = new JFrame();
		
		key = new Keyboard(this);
		addKeyListener(key);
		mouse = new Mouse(this);
		addMouseListener(mouse);	
		addMouseMotionListener(mouse);
		
		gBoard = new GameBoard(3*width/4, height, this);
		gPanel = new Panel(width/4, height, this);
		running = false;
		battleMode = false;
		money = 2000;
		gordHealth = 1000;
		gameLevel = 0;
		enemyTick = 0;
		pos = 0;
		introScreen = new Screen(this,0);
		victoryScreen = new Screen(this,1);
		lossScreen = new Screen(this,2);
		
		
		levelEnemies = new Enemy[][]{
				generateEnemies(10, new  Walker (30, this, 50, 50, 100, 2)), //size, this, damage, value, health, speed
				generateEnemies(15, new  Walker (30, this, 50, 75, 150, 2)),
				generateEnemies(20, new  Walker (30, this, 50, 75, 200, 2)),
				generateEnemies(15, new Splitter (30, this, 1, 50, 100, 150, 2)),
				generateEnemies(30, new Walker (30, this, 50, 75, 200, 2)),
				generateEnemies(10, new Flyer (30, this, 50, 120, 150, 2)),
				generateEnemies(3, new Walker (60, this, 200, 500, 2000, 2)),
				generateEnemies(10, new Rusher (30, this, 50, 120, 150, 2)),
				generateEnemies(15, new Flyer (30, this, 50, 150, 200, 2)),
				generateEnemies(50, new  Walker (40, this, 50, 50, 200, 3)),
				generateEnemies(5, new Splitter (60, this, 2, 200, 500, 2000, 2)),
				generateEnemies(20, new Flyer (30, this, 50, 100, 220, 2),  new Splitter (30, this, 1, 50, 100, 220, 2)),
				generateEnemies(30, new Rusher (30, this, 50, 100, 300, 3),  new Splitter (30, this, 1, 50, 100, 300, 3)),
				generateEnemies(20, new Rusher (30, this, 50, 100, 250, 3),  new Splitter(60, this, 2, 200, 500, 2000, 2)),
				generateEnemies(2, new Splitter (100, this, 3, 300, 1000, 5000, 2)),
				generateEnemies(40, new Rusher (30, this, 50, 100, 250, 3),  new Splitter (30, this, 1, 50, 100, 250, 3)),
				generateEnemies(50, new Rusher (30, this, 50, 100, 350, 3),  new Splitter (30, this, 1, 50, 100, 350, 3)),
				generateEnemies(60, new Rusher (30, this, 50, 100, 450, 3),  new Splitter (30, this, 1, 50, 100, 450, 3)),
				generateEnemies(4, new Rusher (90, this, 300, 1000, 8000, 2)),
				generateEnemies(4, new Flyer (90, this, 300, 1000, 4000, 2)),
				generateEnemies(6, new Rusher (90, this, 300, 1000, 10000, 2), new Flyer (90, this, 300, 1000, 6000, 2)),
				generateEnemies(100, new  Walker (50, this, 50, 120, 1000, 3)),
				generateEnemies(50, new  Rusher (50, this, 50, 120, 1200, 3)),
				generateEnemies(50, new  Rusher (50, this, 50, 120, 1400, 3), new  Flyer (50, this, 50, 120, 800, 2)),
				generateEnemies(50, new  Rusher (50, this, 50, 120, 1200, 5)),
				generateEnemies(45, new  Flyer (50, this, 50, 120, 1000, 4)),
				{new  Flyer (gBoard.width/2, -5, 150, this, 300, 4000, 13000, 1)}
		};
		
		for (GameSquare[] i : gBoard.board) {
			for (GameSquare j : i) {
				components.add(j);
			}
	    }
		for (GameButton button : gPanel.buttons) {
			components.add(button);
	    }

		for (GameButton button : introScreen.buttons) {
			components.add(button);
	    }
		components.add(gPanel.modeB);
		components.add(gPanel.sButton);
		components.add(gPanel.uButton);
	}
	
	public boolean enemyAlive() {
		for(Entity e : entities) {
			if (e instanceof Enemy) {
				return true;
			}
		}
		return false;
	}
	
	public void battleStart(){
		//gBoard.path();
	}
	
	public synchronized void start() { //starts game
		createBufferStrategy(3);
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() { //stops game
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() { //The big loop of the game
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double fps = 60.0; //the fps of the update method
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(); //logic 60 fps
				updates++;
				delta--;
			}
			render(); //display unlimited fps
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void  update() {
		switch(displayScreen) {
			case 0:
				introScreen.update();
				break;
			case 1:
				if (gordHealth <= 0) {
					loss();
				}
				if(gameLevel>levelEnemies.length){
					victory();
				}
				updateGame();
				break;
			case 2:
				victoryScreen.update();
				break;
			case 3:
				lossScreen.update();
		}		
	}
		
	public void updateGame(){
		++enemyTick;
		gBoard.update();
		gPanel.update();	
		for(Entity e : entities) {
			e.update();
		}
		List<Entity> toRemove = new LinkedList<Entity>();

	    for(Entity e : entities) {
	    	if(e.getRemoved()) {
	    		toRemove.add(e);
	    	}
	    }
	    
	    for (Entity e: toRemove) {
	    	entities.remove(e);
	    }
	    for (Entity e: toAdd) {
	    	entities.add(e);
	    }
	    toAdd.clear();
	    
	    if (battleMode) {
	    	if(gameLevel > levelEnemies.length) {
		    		victory();
		    	
			} else if (enemyTick%15 == 0 && pos < levelEnemies[gameLevel - 1].length) {
				entities.add(levelEnemies[gameLevel - 1][pos]);
				enemyTick = 0;
				++pos;
	    	} else if (pos == levelEnemies[gameLevel - 1].length && !enemyAlive()) {
				battleMode = false;
				entities.clear();
				pos = 0;
			}
	    }
	    	
		}			
	
	private void victory() {
		displayScreen = 2;
	}
	private void loss() {
		displayScreen = 3;
	}
	
	public Enemy[] generateEnemies(int amount, Enemy e) {
		Enemy[] level = new Enemy[amount];
		
		for(int i = 0; i < amount; ++i) {
			Enemy a = e.newEnemy();
			level[i] = a;
		}
		return level;
	}
	
	public Enemy[] generateEnemies(int amount, Enemy e, Enemy f) {
		Enemy[] level = new Enemy[amount];
		
		for(int i = 0; i < amount; i += 2) {
			Enemy a = e.newEnemy();
			Enemy b = f.newEnemy();
			level[i] = a;
			level[i+1] = b;
		}
		return level;
	}


	public void render() {
		BufferStrategy bs = getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		switch(displayScreen) {
			case 0:
				introScreen.render(g);
				break;
			case 1:
				gBoard.render(g);
				gPanel.render(g);
				for(Entity e : entities) {
					e.render(g);
				}
				break;
		}
			
		//Graphics above
		g.dispose(); //destroys current graphics
		bs.show();
	}
	
	public void setMouseBuilding(Building b) {
		mouseBuilding = b;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game); //game is subclass of canvas
		game.frame.pack(); //sets size of window to that of the dimensions
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); //sets window to center of screen
		game.frame.setVisible(true);
		game.start();
	}
}
