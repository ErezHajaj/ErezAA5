package com.erez.thegord;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UpgradeButton extends GameButton{
	
	public Building uBuilding = null; 
	//public String abilities = "";

	public UpgradeButton(int x, int y, int w, int h, Game game, Building b) {
		super(x, y, w, h, game);
		
		try {
			imageNotClicked = ImageIO.read(new File("src/rsc/buttons/upgrade.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		uBuilding = b;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(imageNotClicked, xPos, yPos, width, height, null);
		g.setFont(new Font("Helvetica", Font.PLAIN, 30));
		
		if (parentGame.money >= uBuilding.cost) {
			g.setColor(new Color(50, 100 ,0));
		} else {
			g.setColor(Color.RED);
		}
		g.drawString("Cost: " + uBuilding.cost, xPos, yPos + height + 60);
		g.setColor(new Color(0, 0 ,0));
		g.drawString("Level: " + uBuilding.level, xPos, yPos + height + 30);
		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		g.drawString("Damage: " + uBuilding.damage, xPos, yPos + height + 90);
		g.drawString("Fire Rate: " + uBuilding.fireRate, xPos, yPos + height + 110);
		g.drawString("Range: " + uBuilding.range, xPos, yPos + height + 130);
		g.drawString("Shot Speed: " + uBuilding.shotSpeed, xPos, yPos + height + 150);
		g.drawString("Size: " + uBuilding.size, xPos, yPos + height + 170);
		g.drawString("Piercing: " + (uBuilding.piercing - 1), xPos, yPos + height + 190);
		g.drawString("Homing: " + uBuilding.homing, xPos, yPos + height + 210);
		g.drawString("Splash: " + uBuilding.splash, xPos, yPos + height + 230);
		g.drawString("Lob: " + uBuilding.lob, xPos, yPos + height + 250);
		
		
		
	}
	
	@Override
	protected void handleClick() {
		if (parentGame.gPanel.upgradeBuilding && !parentGame.battleMode) {
			if(uBuilding.cost <= parentGame.money) {
				parentGame.money -= uBuilding.cost;
				uBuilding.upgrade();
			}
		}
	}

}
