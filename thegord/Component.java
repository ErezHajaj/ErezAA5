package com.erez.thegord;

import java.awt.Graphics2D;

public abstract class Component {
	
	protected final int xPos;
	protected final int yPos;
	protected final int width;
	protected final int height;
	protected final Game parentGame;
	protected boolean pressed = false;
	
	protected boolean mouseOver;
	
	public Component(int x, int y, int w, int h, Game parentGame) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		this.parentGame = parentGame;
	}
	
	private final boolean isOver(int x, int y) {
		return x >=  xPos && y >= yPos && x - xPos < width && y - yPos < height;
	}
	
	public void render(Graphics2D g) { 
	}
	
	public void update() { 
	}
	
	protected void handleClick() {
	}

	protected void handleOver() {		
	}
	
	public final void onClick(int x, int y) {
		mouseOver = isOver(x, y);
		if (mouseOver) {
			handleClick();
	   	}
	}
	
	public final void onMouseMove(int x, int y) {
		mouseOver = isOver(x, y);
		if (mouseOver) {
			handleOver();
	   	}
	}
	
	public int getX(){
	   return xPos;
	}
	
	public int getY(){
	   return yPos;
	}
	
}
