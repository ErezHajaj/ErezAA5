package com.erez.thegord;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Splitter extends Walker{
	
	public int notSplit;
	public int oHealth;
	
	public Splitter(float x, float y, float size, Game parentGame) {
		super(x, y, size, parentGame);
		notSplit = 1;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy3/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Splitter(float x, float y, float size, Game parentGame, int notSplit, int damage, int value, int health, float speed) {
		super(x, y, size, parentGame, damage, value, health, speed);
		this.notSplit = notSplit;
		oHealth = health;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy3/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Splitter(float size, Game parentGame, int notSplit, int damage, int value, int health, float speed) {
		super(size, parentGame, damage, value, health, speed);
		this.notSplit = notSplit;
		oHealth = health;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy3/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Splitter(float size, Game parentGame, int damage, int value, int health, float speed) {
		super(size, parentGame, damage, value, health, speed);
		this.notSplit = 1;
		oHealth = health;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy3/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Splitter(float x, float y, float size, Game parentGame, int notSplit, int damage, int value, int health, float speed, LinkedList<Pair<Integer, Integer>> path) {
		super(x, y, size, parentGame, damage, value, health, speed);
		this.notSplit = notSplit;
		oHealth = health;
		ways = path;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy3/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Enemy newEnemy() {
		return new Splitter(size, parentGame, notSplit, damage, value, health, speed);
	}
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || health <= 0) {
			parentGame.money += value;
			if(notSplit > 0) split();
			return true;
		} 
		else if(yPos > parentGame.gBoard.height) {
			parentGame.gordHealth -= damage;
			return true;
		} else {
			return false;
		}
	}
	
	public void split() {
		parentGame.toAdd.add(new Splitter(this.xPos + size/4, this.yPos, this.size/2, this.parentGame, notSplit - 1, damage/2, value/2, oHealth/2, speed, ways));
		parentGame.toAdd.add(new Splitter(this.xPos - size/4, this.yPos, this.size/2, this.parentGame, notSplit - 1, damage/2, value/2, oHealth/2, speed, ways));
	}

}
