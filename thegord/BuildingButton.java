package com.erez.thegord;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BuildingButton extends GameButton{
	
	Building buttonBuilding;
	protected Image outline;

	public BuildingButton(int x, int y, int w, int h, Game game, Building b) {
		super(x, y, w, h, game);
		
		
		try {
			outline = ImageIO.read(new File("src/rsc/tiles/Outline.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		buttonBuilding = b;
		imageNotClicked = buttonBuilding.bImage;
		
	}
	
	public void setBuilding(Building b) {
		parentGame.setMouseBuilding(b);
	}
	
	public void setPressed(boolean p) {
		pressed = p;
	}
	
	@Override
	public void render(Graphics2D g) {
		
		if (pressed) {
			g.drawImage(outline, xPos - 5, yPos - 5, width + 10, height + 10, null);
		}
		g.drawImage(imageNotClicked, xPos, yPos, width, height, null);
		Font moneyFont = new Font("Helvetica", Font.PLAIN, 10);
		g.setFont(moneyFont);
		g.drawString("" + buttonBuilding.cost, xPos + 15, yPos - 10);
		
	}

	
	@Override
	protected void handleClick() {
		if (!pressed) {
			parentGame.gPanel.resetBuildingButtons();
		}
		parentGame.setMouseBuilding(null);
		pressed = !pressed;
		if (pressed) {
			setBuilding(buttonBuilding);
		}
		
	}
}
