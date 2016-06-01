package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Poofer extends Building{
	
	private int tick = 0;

	protected Entity targetEntity;

	public Poofer(GameSquare parent) {
		super(parent);
		cost = 300;
		shotSpeed = 6;
		damage = 50;
		range = 100;
		fireRate = 90;
		homing = 0;
		splash = 0;
		size = 10;
		piercing = 1;
		lob = 1;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/towers/tower3/t1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upgrade() {
		damage += 12;
		range += 15;
		size *= 1.1;
		fireRate *= .92;
		if (fireRate < 5) {
			fireRate = 5;
		}
		cost *= 1.5;
		++level;
		if (level >= 6 && level %2 == 0) {
			++lob;
		}
		if (level >= 3 && level %2 == 0) {
			++piercing;
			shotSpeed += 1;
		}
		if (level >= 6 && level%2 == 0) {
			++homing;
		}
		if (level >= 12 && level%4 == 0) {
			++splash;
		}
	}
	
	public void update(){
		tick++;
		targetEntity = findNearestEnemy();
		if(tick%fireRate==0 && targetEntity != null) {
			fireBullet();
			tick = 1;
		}
	}
	
	public void render(Graphics2D g, int xPos, int yPos, int width, int height) {
		AffineTransform at = AffineTransform.getScaleInstance((double) width / bImage.getWidth(null),
                (double) height / bImage.getHeight(null));
		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		g.drawImage(bImage, at, null);
	}
	
	
	public Entity findNearestEnemy(){
		Entity e1 = null;
		double closestDistance = range;
		double currentDistance;
		for(Entity e: parentSquare.parentGame.entities) {
			if (e instanceof Enemy) {
			   currentDistance = Math.sqrt((parentSquare.xPos + parentSquare.width/2 - e.getX())*(parentSquare.xPos + parentSquare.width/2 - e.getX())+(parentSquare.yPos + parentSquare.height/2 - e.getY())*(parentSquare.yPos + parentSquare.height/2 - e.getY()));
			   if(currentDistance < closestDistance){
				   e1 = e;
				   closestDistance = currentDistance;
			   }
			}
		}
		return e1;
	}
	
	public void fireBullet(){
		if (parentSquare != null) {
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 - parentSquare.width), 
			(float) (parentSquare.yPos + parentSquare.height/2), 0, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 + parentSquare.width), 
					(float) (parentSquare.yPos + parentSquare.height/2), (float) Math.PI, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 - parentSquare.width/2), 
					(float) (parentSquare.yPos + parentSquare.height/2 - parentSquare.width*.866), (float) Math.PI/3, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 + parentSquare.width/2), 
					(float) (parentSquare.yPos + parentSquare.height/2 - parentSquare.width*.866), (float) Math.PI*2/3, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 + parentSquare.width/2), 
					(float) (parentSquare.yPos + parentSquare.height/2 + parentSquare.width*.866), (float) Math.PI*4/3, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
			
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 - parentSquare.width/2), 
					(float) (parentSquare.yPos + parentSquare.height/2 + parentSquare.width*.866), (float) Math.PI*5/3, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
		}
	}
	
	
	@Override
	public Building newBuilding() {
		return new Poofer(parentSquare);
	}
}
