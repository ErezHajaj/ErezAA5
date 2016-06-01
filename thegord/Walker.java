package com.erez.thegord;

import java.util.LinkedList;

public class Walker extends Enemy{
	
	LinkedList<Pair<Integer, Integer>> ways = null;

	public Walker(float x, float y, float size, Game parentGame) {
		super(x, y, size, parentGame);
	}
	
	public Walker(float x, float y, float size, Game parentGame, int damage, int value, int health, float speed) {
		super(x, y, size, parentGame, damage, value, health, speed);
	}
	
	public Walker(float size, Game parentGame, int damage, int value, int health, float speed) {
		super(size, parentGame, damage, value, health, speed);
	}
	
	public Enemy newEnemy() {
		return new Walker(size, parentGame, damage, value, health, speed);
	}
	
	public void followPath() {
		if(ways == null) {
			ways = parentGame.gBoard.path(new LinkedList<Pair<Integer, Integer>>(), (int)(xPos), (int)(yPos));
		}
		
		if (0 < ways.size()) {
			goToBlock(ways.getFirst().getSecond(), ways.getFirst().getFirst());
			if(parentGame.gBoard.getCol((int) xPos + parentGame.gBoard.squareWidth/2) == ways.getFirst().getSecond() && parentGame.gBoard.getRow((int) yPos + parentGame.gBoard.squareHeight/2) == ways.getFirst().getFirst()) {
					ways.removeFirst();
			}
		} else {
			xVel = 0;
			yVel = speed;
		}

	}
	
	public void update() {
		followPath();
		xPos += xVel;
		yPos += yVel;
	}

}
