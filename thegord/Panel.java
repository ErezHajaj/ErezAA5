package com.erez.thegord;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Panel {

	public final int width;
	public final int height;
	public final int xPos;
	public final int yPos;
	
	public ArrayList<GameButton> buttons = new ArrayList<GameButton>();
	public ArrayList<BuildingButton> buildingButtons = new ArrayList<BuildingButton>();
	public ModeButton modeB;
	public boolean upgradeBuilding = false;
	
	public UpgradeButton uButton;
	public SellButton sButton;
	private Image panel = null;

	private Game parentGame;
	
	public Panel(int width, int height, Game parentGame) {
		
		this.width = width;
		this.height = height;
		this.parentGame = parentGame;
		xPos = Game.width - width;
		yPos = 0;
		
		try {
			panel = ImageIO.read(new File("src/rsc/panels/panmenu.png"));
			
			BuildingButton regB = new BuildingButton(xPos + width/8, height/2, 50, 50, parentGame, new Sniper(null));
			BuildingButton ammoB = new BuildingButton(xPos + width/8, height/2 + 100, 50, 50, parentGame, new Wall(null));
			BuildingButton turret = new BuildingButton(xPos + width/8 + 100, height/2, 50, 50, parentGame, new Turret(null));
			BuildingButton poofer = new BuildingButton(xPos + width/8 + 100, height/2 + 100, 50, 50, parentGame, new Poofer(null));
			
			modeB = new ModeButton(xPos + width/8, 3*height/4, 200, 100, parentGame);

			buildingButtons.add(regB);
			buildingButtons.add(ammoB);
			buildingButtons.add(turret);
			buildingButtons.add(poofer);
			buttons.add(regB);
			buttons.add(ammoB);
			buttons.add(turret);
			buttons.add(poofer);
			uButton= new UpgradeButton(xPos + width/8, height/6, 50, 50, parentGame, null);
			sButton = new SellButton(xPos + width/8 + 100, height/6, 50, 50, parentGame, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		modeB.update();
		if (!parentGame.battleMode && !upgradeBuilding) {
			for (GameButton button : buttons) {
		        button.update();
		    }
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(panel, xPos, yPos, width, height, null);
		Font moneyFont = new Font("Helvetica", Font.PLAIN, 30);
		g.setFont(moneyFont);
		g.drawString("Coins: " + parentGame.money, xPos, yPos + 30);
		g.drawString("Health: " + parentGame.gordHealth, xPos, yPos + 70);
		g.drawString("Level: " + parentGame.gameLevel, xPos, yPos + 110);
		
		modeB.render(g);
		if (!parentGame.battleMode && !upgradeBuilding) {
			for (GameButton button : buttons) {
				button.render(g);
	    	}
		} else if (!parentGame.battleMode && upgradeBuilding){
		    uButton.render(g);
		    sButton.render(g);
		}
		if(upgradeBuilding) {
			
			g.drawOval(uButton.uBuilding.parentSquare.xPos + uButton.uBuilding.parentSquare.width/2 - uButton.uBuilding.range,
					uButton.uBuilding.parentSquare.yPos + uButton.uBuilding.parentSquare.height/2 - uButton.uBuilding.range, uButton.uBuilding.range*2, uButton.uBuilding.range*2);
		}
	}
	
	public void resetBuildingButtons() {
		for (BuildingButton bButton : buildingButtons) {
	        bButton.setPressed(false);
	    }
	}
	
	public void displayHealth() {
		
		
	}
	
	public void displayCoins() {
		
		
	}
	
	public void displayBuildings() {
		
	}
}
