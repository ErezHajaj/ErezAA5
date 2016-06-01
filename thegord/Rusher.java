package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rusher extends Walker{
	
	protected int originalHealth;
	protected float originalSpeed;

	public Rusher(float x, float y, float size, Game parentGame) {
		super(x, y, size, parentGame);
		originalHealth = health;
		originalSpeed = speed;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy4/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Rusher(float x, float y, float size, Game parentGame, int damage, int value, int health, float speed) {
		super(x, y, size, parentGame, damage, value, health, speed);
		originalHealth = health;
		originalSpeed = speed;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy4/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Rusher(float size, Game parentGame, int damage, int value, int health, float speed) {
		super(size, parentGame, damage, value, health, speed);
		originalHealth = health;
		originalSpeed = speed;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy4/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Enemy newEnemy() {
		return new Rusher(size, parentGame, damage, value, health, speed);
	}
	
	public void update() {
		followPath();
		xPos += xVel;
		yPos += yVel;
		speed = (float) (originalSpeed*((5*originalHealth)/(originalHealth + health)));
	}
	
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || health <= 0) {
			parentGame.money += value;
			return true;
		} 
		else if(yPos > parentGame.gBoard.height) {
			parentGame.gordHealth -= damage;
			return true;
		} else {
			return false;
		}
	}
	

}
