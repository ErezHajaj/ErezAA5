package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.imageio.ImageIO;

public class GameBoard {
	public int rowSquares;
	public int colSquares;
	public int width;
	public int height;
	public int squareWidth;
	public int squareHeight;
	public int boardHeight;
	public GameSquare[][] board;
	//LinkedList<Pair<Integer, Integer>> waypoints = new LinkedList<Pair<Integer, Integer>>();
	protected Image background;
	
	private Game parentGame;

	public GameBoard(int width, int height, Game parentGame) {
		this(20, 16, width, height, parentGame);
		
		try {
			background = ImageIO.read(new File("src/rsc/backgrounds/bglevelone.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GameBoard(int r, int c, int w, int h, Game parentGame) {
		rowSquares = r;
		colSquares = c;
		width = w;
		height = h;
		squareWidth = width / rowSquares;
		squareHeight = height / colSquares;
		this.parentGame = parentGame;
		boardHeight = 4 + parentGame.gameLevel;
		loadBoard();
		//path();
	}
	
	public void loadBoard() {
		board = new GameSquare[rowSquares][colSquares];
		for(int i = 0; i < rowSquares; i++) {
			for (int j = 0; j < boardHeight; j++) {
				board[i][j] = new GameSquare(i*squareWidth, j*squareHeight, squareWidth, squareHeight, parentGame, null, false);
			}
			for (int j = boardHeight; j < colSquares; j++) {
				board[i][j] = new GameSquare(i*squareWidth, j*squareHeight, squareWidth, squareHeight, parentGame, null, true);
			}
			//board[(rowSquares - 1)/2][colSquares - 1] = new GameSquare(i*squareWidth, (colSquares - 1)*squareHeight, squareWidth, squareHeight, parentGame, null, false);
		}
	}
	
	public boolean isDefenseBlocking() {		
		Queue<Pair<Integer, Integer>> q = new ArrayDeque<Pair<Integer, Integer>>();		
		Set<Pair<Integer, Integer>> reached = new HashSet<Pair<Integer, Integer>>(); 
		
		for(int i = boardHeight; i < colSquares; ++i) {										//add starting sources to q and reached. 
			Pair<Integer, Integer> source = new Pair<Integer, Integer>(i, -1); 
			reached.add(source);
			q.offer(source);
		}
		
		while (!q.isEmpty()) {
			Pair<Integer, Integer> node = q.poll();
			if (node.getSecond() == rowSquares - 1) {											//checks to see if the last node is in the last column.
				System.out.println("Blocking");
			  return true;
			}
			for (int ii = node.getFirst() - 1; ii <= node.getFirst() + 1; ++ii) {				//goes through the col above, at, and below
				if (boardHeight <= ii && ii < colSquares) {									//checks to see if it is in the board
					for (int jj = node.getSecond() - 1; jj <= node.getSecond() + 1; ++jj) { 	//goes through the row to left, at, and to right
						if (0 <= jj && jj < rowSquares) {										//checks to see if it is in the board
						  if (board[jj][ii].building != null) {									//checks to see if it has building
							  Pair<Integer, Integer> neigh = new Pair<Integer, Integer>(ii, jj);//creates a new pair at each point that is 1 block away from current spot. Includes diagonals.
							  if (!reached.contains(neigh)) {									//checks if already reached
								  q.offer(neigh);												//adds it to q and to reached
								  reached.add(neigh);
							  }
						  }
						}
					}
				}
			}
		}
		//path();
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Pair<Integer, Integer>> path(LinkedList<Pair<Integer, Integer>> waypoints, int xPos, int yPos) {
		//waypoints.clear();
		Queue<Pair<Integer, Integer>> q = new ArrayDeque<Pair<Integer, Integer>>();		
		Set<Pair<Integer, Integer>> reached = new HashSet<Pair<Integer, Integer>>(); 
		
		for(int i = (rowSquares)/2; i < rowSquares; ++i) {
			Pair<Integer, Integer> source = new Pair<Integer, Integer>(colSquares - 1, i);
			reached.add(source);
			q.offer(source);
		}
		for(int i = rowSquares/2 - 1; i >= 0; --i) {
			Pair<Integer, Integer> source = new Pair<Integer, Integer>(colSquares - 1, i);
			reached.add(source);
			q.offer(source);
		}

		
		while (!q.isEmpty()) {
			Pair<Integer, Integer> node = q.poll();
			if (node.getFirst() == getRow(yPos) && node.getSecond() == getCol(xPos)) {
				//System.out.println("Path Found");
				while(node.parent != null) {
					waypoints.add(node);
					node = (Pair<Integer, Integer>) node.parent;
				}
				break;
			}
			if(board[node.getSecond()][node.getFirst()].building == null) {
				Pair<Integer, Integer> left = new Pair<Integer, Integer>(node.getFirst(), node.getSecond() - 1, node);
				Pair<Integer, Integer> right = new Pair<Integer, Integer>(node.getFirst(), node.getSecond() + 1, node);
				Pair<Integer, Integer> up = new Pair<Integer, Integer>(node.getFirst() - 1, node.getSecond(), node);
				Pair<Integer, Integer> down = new Pair<Integer, Integer>(node.getFirst() + 1, node.getSecond(), node);
				
				  if (left.getSecond() >= 0 && !reached.contains(left) && board[left.getSecond()][left.getFirst()].building == null) {		
					  q.offer(left);								
					  reached.add(left);
				  }
				  if (right.getSecond() < rowSquares && !reached.contains(right) && board[right.getSecond()][right.getFirst()].building == null) {
					  q.offer(right);												
					  reached.add(right);
				  }
				  if (up.getFirst() >= 0 && !reached.contains(up) && board[up.getSecond()][up.getFirst()].building == null) {		
					  q.offer(up);											
					  reached.add(up);
				  }
				  if (down.getFirst() < colSquares && !reached.contains(down) && board[down.getSecond()][down.getFirst()].building == null) {		
					  q.offer(down);											
					  reached.add(down);
				  }
			}
		}
		return waypoints;
	}
	
	public void update() {
			for (GameSquare[] i : board) {
				for (GameSquare j : i) {
				        j.update();
				}
		    }
	}

	
	public void render(Graphics2D g) {
		if (parentGame.battleMode) {
			g.drawImage(background, 0, 0, width, height, null);
		}
		for (GameSquare[] i : board) {
			for (GameSquare j : i) {
			        j.render(g);
			}
	    }	
	}
	
	public GameSquare squareSelected(int x, int y) {
		return board[getRow(y)][getCol(x)];
	}
	
	public int getCol(int x) {
		return x/(width/rowSquares); 
	}
	
	public int getRow(int y) {
		return y/(height/colSquares); 
	}
}
