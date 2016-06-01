package com.erez.thegord.input;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import com.erez.thegord.Component;
import com.erez.thegord.Entity;
import com.erez.thegord.Game;

public class Mouse extends MouseInputAdapter {

	private final Game parentGame;
	
	public Mouse(Game parentGame) {
		super();
		this.parentGame = parentGame;
	}
	
	public void update() {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for(Component component : parentGame.components) {
			component.onMouseMove(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(Component component : parentGame.components) {
			component.onClick(e.getX(), e.getY());
		}
		
		for(Entity entity : parentGame.entities) {
			entity.onClick(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
	

}
