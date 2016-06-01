package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building {
	
	protected int cost = 100;
	
	public int damage = 25;
	public float shotSpeed = 10;
	public int range = 200;
	public int fireRate = 50;
	public boolean remove = false;
	public int level = 1;
	public int piercing = 1;
	public int homing = 0;
	public int splash = 0;
	public int size = 20;
	public int lob = 0;
	
	//protected Image[] pictures = new Image[5];
	
	protected Image bImage;
	protected GameSquare parentSquare;
	
	public Building(GameSquare parent) {
		parentSquare = parent;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/wall/wall.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {

	}
	
	public void upgrade() {
		cost *= 1.5;
		++level;
	}
	
	public void delete() {
		remove = true;
	}

	public void render(Graphics2D g, int xPos, int yPos, int width, int height) {
		g.drawImage(bImage, xPos, yPos, width, height, null);
		
	}
	
	public Building newBuilding() {
		return new Building(parentSquare);
	}
}
