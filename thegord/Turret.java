package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Turret extends Building{
	
	private int tick = 0;
	private float angle = (float) (Math.PI/2);

	protected Entity targetEntity;

	public Turret(GameSquare parent) {
		super(parent);
		cost = 100;
		shotSpeed = 6;
		damage = 25;
		range = 200;
		fireRate = 50;
		homing = 0;
		splash = 0;
		size = 10;
		piercing = 1;
		lob = 1;
		
		try {
			bImage = ImageIO.read(new File("src/rsc/towers/tower1/t1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upgrade() {
		damage += 5;
		range += 20;
		size *= 1.1;
		fireRate *= .94;
		if (fireRate < 4) {
			fireRate = 4;
		}
		cost *= 1.5;
		++level;
		if (level >= 6) {
			++lob;
		}
		if (level >= 3 && level %3 == 0) {
			++piercing;
			shotSpeed += 1;
		}
		if (level >= 6 && level%2 == 0) {
			++homing;
		}
		if (level >= 12 && level%3 == 0) {
			++splash;
		}
	}
	
	public void update(){
		tick++;
		rotate();
		if(tick%fireRate==0 && targetEntity != null) {
			fireBullet();
			tick = 1;
		}
	}
	
	public void render(Graphics2D g, int xPos, int yPos, int width, int height) {
		AffineTransform at = AffineTransform.getScaleInstance((double) width / bImage.getWidth(null),
                (double) height / bImage.getHeight(null));
		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		if (parentSquare.parentGame.battleMode) {
			at.rotate(angle - Math.PI/2, (width/2)/at.getScaleX(), (height/2)/at.getScaleY());
		}
		g.drawImage(bImage, at, null);
	}
	
	public void rotate(){
	   angle = getRotation();
	}
	
	public float getRotation(){
		targetEntity = findNearestEnemy();
		if(targetEntity != null){
			double dx = parentSquare.xPos + parentSquare.width/2 - targetEntity.getX();
			double dy = parentSquare.yPos + parentSquare.height/2 - targetEntity.getY();
		
			return (float) Math.atan2(dy, dx);
		}
		return (float) (Math.PI/2);
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
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 - parentSquare.width*Math.cos(angle)), 
			(float) (parentSquare.yPos + parentSquare.height/2 - parentSquare.width*Math.sin(angle)), (float)angle, (float) -shotSpeed, size, parentSquare.parentGame, damage, piercing, lob, homing, splash));
		}
	}
	
	
	@Override
	public Building newBuilding() {
		return new Turret(parentSquare);
	}
}
