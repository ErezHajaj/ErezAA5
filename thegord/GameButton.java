package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameButton extends Component{

	protected Image imageNotClicked;
	
	public GameButton(int x, int y, int w, int h, Game game) {
		super(x, y, w, h, game);
		
		try {
			imageNotClicked = ImageIO.read(new File("src/rsc/wall/wall.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(imageNotClicked, xPos, yPos, width, height, null);
	}
	
	@Override
	protected void handleClick() {
		pressed = !pressed;
	}
}
