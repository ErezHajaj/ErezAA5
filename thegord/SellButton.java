package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SellButton extends GameButton{
	
	public Building sBuilding = null; 

	public SellButton(int x, int y, int w, int h, Game game, Building b) {
		super(x, y, w, h, game);
		
		try {
			imageNotClicked = ImageIO.read(new File("src/rsc/buttons/sell.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		sBuilding = b;
	}

	@Override
	protected void handleClick() {
		if (parentGame.gPanel.upgradeBuilding && !parentGame.battleMode) {
			sBuilding.delete();
			parentGame.money += (int) (.5 * sBuilding.cost);
			parentGame.gPanel.uButton.uBuilding = null;
			sBuilding = null;
			parentGame.gPanel.upgradeBuilding = false;
		}
	}
}
