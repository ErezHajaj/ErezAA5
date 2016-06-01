package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flyer extends Enemy{

	public Flyer(float x, float y, float size, Game parentGame) {
		super(x, y, size, parentGame);
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy2/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		xVel = 0;
		yVel = speed;
	}
	
	public Flyer(float x, float y, float size, Game parentGame, int damage, int value, int health, float speed) {
		super(x, y, size, parentGame, damage, value, health, speed);
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy2/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		xVel = 0;
		yVel = speed;
	}
	
	public Flyer(float size, Game parentGame, int damage, int value, int health, float speed) {
		super(size, parentGame, damage, value, health, speed);
		
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy2/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		xVel = 0;
		yVel = speed;
	}
	
	public Enemy newEnemy() {
		return new Flyer(size, parentGame, damage, value, health, speed);
	}

}
