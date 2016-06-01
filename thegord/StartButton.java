package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StartButton extends GameButton {
	
	protected Image outline;

	public StartButton(int x, int y, int w, int h, Game game) {
		super(x, y, w, h, game);
		
		try {
			imageNotClicked = ImageIO.read(new File("src/rsc/buttons/readyButton.png"));
			outline = ImageIO.read(new File("src/rsc/tiles/Outline.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (mouseOver) {
			g.drawImage(outline, xPos - 5, yPos - 5, width + 10, height + 10, null);
		}
		g.drawImage(imageNotClicked, xPos, yPos, width, height, null);
		
	}
	
	@Override
	protected void handleClick() {
		parentGame.displayScreen = 1;
	}

}
