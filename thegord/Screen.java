package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Screen {
	
	ArrayList<GameButton> buttons = new ArrayList<GameButton>();
	
	private Image display = null;
	private Image title = null;

	@SuppressWarnings("unused")
	private Game parentGame;
	
	private int screenType;
	
	public Screen(Game parentGame, int screenType) {
		
		this.parentGame = parentGame;
		if(screenType == 0){
			try {
				display = ImageIO.read(new File("src/rsc/backgrounds/bgmenu.png"));
				title = ImageIO.read(new File("src/rsc/backgrounds/bgmenutitle.png"));
				buttons.add(new StartButton(3*Game.width/8, Game.height/2, Game.width/4, Game.height/4, parentGame));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				display = ImageIO.read(new File("src/rsc/backgrounds/bgmenu.png"));
				buttons.add(new StartButton(3*Game.width/8, Game.height/2, Game.width/4, Game.height/4, parentGame));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		for (GameButton button : buttons) {
	        button.update();
	    }
	}
	
	public void render(Graphics2D g) {
		g.drawImage(display, 0, 0, Game.width, Game.height, null);
		if(screenType==0) g.drawImage(title, Game.width/4, Game.height/8, Game.width/2, Game.height/4, null);
		if(screenType==1)g.drawString("Winner, winner, chicken dinner! Your score is " + parentGame.gordHealth+parentGame.gameLevel*10, 400, 400);
		if(screenType==2)g.drawString("You let the gord die. Your score is " + parentGame.gordHealth+parentGame.gameLevel*10, 400, 400);
		
		for (GameButton button : buttons) {
	        button.render(g);
	    }
	}
}
