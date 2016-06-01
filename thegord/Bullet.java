package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Bullet extends Entity{
	
	int damage = 20;
	int piercing = 1;
	int lob = 0;
	int homing = 0;
	int splash = 0;
	protected Image bImage2;
	protected Image bImage3;
	public LinkedList<Entity> hitEnemies = new LinkedList<Entity>();
	public LinkedList<GameSquare> hitSquares = new LinkedList<GameSquare>();
	

	public Bullet(float x, float y, float angle, float shotSpeed, float size, Game parentGame, int damage, int piercing, int lob, int homing, int splash) {
		super(x, y, angle, shotSpeed, size, parentGame);
		this.damage = damage;
		this.piercing = piercing;
		this.lob = lob;
		this.homing = homing;
		this.splash = splash;
		
		try {
				bImage = ImageIO.read(new File("src/rsc/bullets/b2.png"));
				bImage2 = ImageIO.read(new File("src/rsc/bullets/b3.png"));
				bImage3 = ImageIO.read(new File("src/rsc/bullets/b4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || yPos > parentGame.gBoard.height || yPos < 0 || piercing <= 0 || lob <= 0 || size <= 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public void render(Graphics2D g) {
		angle = getRotation();
		AffineTransform at;
		if(splash > 0) {
			at = AffineTransform.getScaleInstance((double) size / bImage3.getWidth(null),
	                (double) size / bImage3.getHeight(null));
		} else if(piercing > 1){
			at = AffineTransform.getScaleInstance((double) size / bImage2.getWidth(null),
	                (double) size / bImage2.getHeight(null));
		} else {
			at = AffineTransform.getScaleInstance((double) size / bImage.getWidth(null),
	                (double) size / bImage.getHeight(null));
		}

		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		at.rotate(angle + Math.PI/2, (size/2)/at.getScaleX(), (size/2)/at.getScaleY());
		if(splash > 0) {
			g.drawImage(bImage3, at, null);
		} else if(piercing > 1){
			g.drawImage(bImage2, at, null);
		} else {
			g.drawImage(bImage, at, null);
		}

	}
	
	public void update() {
		checkAllEncounters();
		xPos += xVel;
		yPos += yVel;
		if(homing > 0) {
			rotate();
			xVel = (float)(Math.cos(angle)*speed);
			yVel = (float)(speed*Math.sin(angle));
		}
	}
	
	public void rotate(){
		angle = getRotation();
	}
		
	public float getRotation(){
		Entity targetEntity = findNearestEnemy();
		if(targetEntity != null){
			double dx = xPos - targetEntity.getX();
			double dy = yPos - targetEntity.getY();
		
			return (float) Math.atan2(dy, dx);
		}
		return angle;
	}
	
	public Entity findNearestEnemy(){
		Entity e1 = null;
		double closestDistance = 50*homing;
		double currentDistance;
		for(Entity e: parentGame.entities) {
			if (e instanceof Enemy && hitEnemies.indexOf(e) == -1) {
			   currentDistance = Math.sqrt((xPos - e.getX())*(xPos - e.getX())+(yPos - e.getY())*(yPos - e.getY()));
			   if(currentDistance < closestDistance){
				   e1 = e;
				   closestDistance = currentDistance;
			   }
			}
		}
		return e1;
	}
	
	public void splash() {
		parentGame.toAdd.add(new Bullet(xPos, yPos, (float) Math.PI*1/4, speed, size/2, parentGame, splash*damage/(splash + 3), piercing/2, lob/2, homing/2, splash/2)); //size/2
		parentGame.toAdd.add(new Bullet(xPos, yPos, (float) Math.PI*3/4, speed, size/2, parentGame, splash*damage/(splash + 3), piercing/2, lob/2, homing/2, splash/2)); //size/2
		parentGame.toAdd.add(new Bullet(xPos, yPos, (float) Math.PI*5/4, speed, size/2, parentGame, splash*damage/(splash + 3), piercing/2, lob/2, homing/2, splash/2)); //size/2
		parentGame.toAdd.add(new Bullet(xPos, yPos, (float) Math.PI*7/4, speed, size/2, parentGame, splash*damage/(splash + 3), piercing/2, lob/2, homing/2, splash/2)); //size/2
	}
	
  public boolean checkEncounter(Entity enemy) {
      return (size/2+enemy.size/2)*(size/2+enemy.size/2) >= (xPos - enemy.xPos)*(xPos - enemy.xPos)+(yPos - enemy.yPos)*(yPos - enemy.yPos);
  }
  
   public void checkAllEncounters() {
        for (Entity e : parentGame.entities) {
	        	if (e instanceof Enemy && checkEncounter(e) && hitEnemies.indexOf(e) == -1) {
	        		hitEnemies.offer(e);
	        		e.health -= damage;
	        		if(splash > 0) {
	        			splash();
	        			--splash;
	        		}
	        		if(piercing > 0) {
		            	--piercing;
		            	speed /= 2;
		            	size /= 1.1;
		            	--homing;
	        		}
	        	}
        }
        if (lob > 0 && getSquare((int)xPos, (int)yPos) != null && getSquare((int)xPos, (int)yPos).building != null && hitSquares.indexOf(getSquare((int)xPos, (int)yPos)) == -1) {
        	hitSquares.add(getSquare((int)xPos, (int)yPos));
        	--lob;
        }
    }
}
