package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends Entity{

	protected int damage = 300;
	protected int value = 250;
	
	public Enemy(float x, float y, float size, Game parentGame) {
		super(x, y, 0, 2, size, parentGame);
		this.health = 100*(parentGame.gameLevel + 1);
		this.value = 100*(parentGame.gameLevel + 1);
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy1/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	public Enemy(float x, float y, float size, Game parentGame, int damage, int value, int health, float speed) {
		super(x, y, 0, speed, size, parentGame);
		this.damage = damage;
		this.value = value;
		this.health = health;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy1/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	public Enemy(float size, Game parentGame, int damage, int value, int health, float speed) {
		super((float) (parentGame.gBoard.width*Math.random()), -5, 0, speed, size, parentGame);
		this.damage = damage;
		this.value = value;
		this.health = health;
		try {
			bImage = ImageIO.read(new File("src/rsc/enemies/enemy1/e1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || health <= 0) {
			parentGame.money += value;
			return true;
		} 
		else if(yPos > parentGame.gBoard.height) {
			System.out.println(getClass() + ", " + health);
			parentGame.gordHealth -= damage;
			return true;
		} else {
			return false;
		}
	}
	
	public void update() {
		xPos += xVel;
		yPos += yVel;
	}
	
	public void render(Graphics2D g) {
		angle = getRotation();
		AffineTransform at = AffineTransform.getScaleInstance((double) size / bImage.getWidth(null),
                (double) size / bImage.getHeight(null));
		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		at.rotate(-angle, (size/2)/at.getScaleX(), (size/2)/at.getScaleY());
		g.drawImage(bImage, at, null);
		//g.drawOval((int)xPos - 12, (int) yPos - 12, 50, 50);
	}
	
	public GameSquare getSquare(int x, int y) {
			return parentGame.gBoard.board[parentGame.gBoard.getCol(x)][parentGame.gBoard.getRow(y)];
	}
		
	public void goToBlock(int x, int y) {
	   float x1 = (float)parentGame.gBoard.board[x][y].getX();
	   float y1 = (float)parentGame.gBoard.board[x][y].getY();
	   float magnitude = (float)Math.sqrt((this.getX()-x1)*(this.getX()-x1)+(this.getY()-y1)*(this.getY()-y1));
	   xVel = -((this.getX()-x1)/magnitude)*speed;
	   yVel = -((this.getY()-y1)/magnitude)*speed;
	}
	
	public Enemy newEnemy() {
		return new Enemy(size, parentGame, damage, value, health, speed);
	}
	
	protected void handleClick() {
	}
}
