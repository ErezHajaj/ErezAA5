package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {
	protected float xVel;
	protected float yVel;
	protected float size;
	protected float xPos;
	protected float yPos;
	protected float angle;
	protected float speed;
	protected float magnitude;
	protected Image bImage;
	public Game parentGame;
	
	protected boolean mouseOver;
	public int health = 100;

	
	public Entity(float x, float y, float angle2, float shotSpeed, float size, Game parentGame) {
		xPos = x;
		yPos = y;
		this.speed = shotSpeed;
		this.angle = angle2;
		xVel = (float)(Math.cos(angle2)*shotSpeed);
		yVel = (float)(shotSpeed*Math.sin(angle2));
		this.size = size;
		this.parentGame = parentGame;
			
		try {
			bImage = ImageIO.read(new File("src/rsc/backgrounds/bgtiles/Square.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g) {
		angle = getRotation();
		AffineTransform at = AffineTransform.getScaleInstance((double) size / bImage.getWidth(null),
                (double) size / bImage.getHeight(null));
		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		at.rotate(-angle, (size/2)/at.getScaleX(), (size/2)/at.getScaleY());
		g.drawImage(bImage, at, null);
	}
	
	public boolean getRemoved() {
		return false;
	}
	
	
	public void update() {
		xPos += xVel;
		yPos += yVel;
	}
	
	public GameSquare getSquare(int x, int y) {
		if (parentGame.gBoard.getCol(x) < parentGame.gBoard.rowSquares && parentGame.gBoard.getRow(y) < parentGame.gBoard.colSquares) {
			return parentGame.gBoard.board[parentGame.gBoard.getCol(x)][parentGame.gBoard.getRow(y)];
		} else {
			return null;
		}
	}
	
	private final boolean isOver(int x, int y) {
		return x >=  xPos - size/2 && y >= yPos - size/2 && x - xPos - size/2 < size && y - yPos - size/2 < size;
	}
	
	public final void onClick(int x, int y) {
		mouseOver = isOver(x, y);
		if (mouseOver) {
			handleClick();
	   	}
	}
	
	protected void handleClick() {
		
	}
	
	public float getX(){
		return xPos;
	}
	
	public float getY(){
		return yPos;
	}
	
	public float getRotation(){
		return (float)Math.atan2(xVel, yVel);
	}
	
}
