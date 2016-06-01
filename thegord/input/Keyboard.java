package com.erez.thegord.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.erez.thegord.Game;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[120]; //or 65536
	public boolean up, down, left, right;
	
	@SuppressWarnings("unused")
	private final Game parentGame;
	
	public Keyboard(Game parentGame) {
		super();
		this.parentGame = parentGame;
	}
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	
		for (int i = 0; i < keys.length; i++) {
			if (keys[i]) {
				System.out.println("Key: " + i);
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;	
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}
}
