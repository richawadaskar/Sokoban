package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Model {
	private double myLevel;
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	private int ROWS = 10;
	private int COLS = 10;
	private boolean playMode;
	private boolean newGame;
	private boolean darkness;
	private boolean firstStage;
	private String message = "";
	private boolean spoken;
	private boolean transition;

	static Scanner in;
	final int[][] INNER_PERIMETER = {{-1,0,1,-1,1,-1,0,1}, {-1,-1,-1,0,0,1,1,1}};
	final int[][] OUTER_PERIMETER = {{-2,-1,0,1,2,-2,2,-2,2,-2,2,-2,-1,0,1,2,}, 
									{-2,-2,-2,-2,-2,-1,-1,0,0,1,1,2,2,2,2,2}};
	
	Tile[][] board;
	Movable player;
	
	public Model(){
		board = new Tile[ROWS][COLS];
	}
	
	public void setLevel(double level){
		if (level >= 0) {
			myLevel = level;
		}
		
		if (level == 0){
			//Basic level for user to practice with
			loadLevel("levels/trial.txt");
		} else if (level > 0 && level % 1 == 0){
			//Loads levels
			loadLevel("levels/level" + (int)level + ".txt");
			firstStage = true;
		} else if (level > 0 && level % 1 != 0){
			//load rescue levels
			transition = false;
			loadLevel("levels/rescue" + (int)level + ".txt");
		} else if (level == -1) {
			transition = true;
			loadLevel("levels/antechamber.txt");
			setMessage((int)myLevel);
			firstStage = false;
		}
	}
	
	/**
	 * This method returns true if the player wins the level.
	 */
	public boolean levelCompleted(){
		if (!isTransition()) {
			for (int i = 0; i < ROWS; i++){
				for (int j = 0; j < COLS; j++){
					if ((board[i][j].getType() == Tile.getBlock())){
						Movable block = (Movable)(board[i][j]);
						if (block.getCovering().getType() != Tile.getTarget()){
							return false;
						}
					}
				}
			}
			return true;
		} else {
			return spoken;
		}
	}
	
	public boolean getRescueStage(){
		return !transition && (myLevel % 1.0 == 0.5);
	}
	
	public boolean isTransition(){
		return transition;
	}
	
	private void setMessage(int level){
		try {
			in = new Scanner(new File("levels/messages.txt"));
			for (int i = 0; i < level; i++){
				if (in.hasNext()){
					String line = in.nextLine();
					message = line;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getMessage(){
		spoken = true;
		return message;
	}
	
	//Return true if user wins the entire game!! :)
	public boolean gameWon(){
		//Test code
		if(myLevel == 6.5 && levelCompleted()){
			return true;
		}
		return false;
	}
	
	private void loadLevel(String file){
		try {
			in = new Scanner(new File(file));
			for (int i = 0; i < ROWS; i++){
				for (int j = 0; j < COLS; j++){
					if (in.hasNext()){
						
						int type = in.nextInt();
						
						if (type == Tile.getCharacter()) {
							player = new Movable(i, j, UP, new Tile(i, j, Tile.getFloor()));
							board[i][j] = player;
						} else if (type == Tile.getBlock()){
							Movable block = new Movable(i, j, Tile.getBlock(), new Tile(i, j, Tile.getFloor()));
							board[i][j] = block;
						} else {
							Tile tile = new Tile(i, j, type);
							board[i][j] = tile;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setDarkness(boolean dark) {
		darkness = dark;
		updateDarkness();
	}
	
	public void movePlayer(int direction){
		
		int newRow = player.getRow();
		int newCol = player.getCol();;
		
		if(direction == UP){
			newRow--;
		} else if(direction == DOWN){
			newRow++;
		} else if(direction == LEFT){
			newCol--;
		} else if(direction == RIGHT){
			newCol++;
		}
		
		if (withinBounds(newRow, newCol)){
			if (board[newRow][newCol].getWalkable()){
				moveBlock(player, player.getRow(), player.getCol(), newRow, newCol);
			} else if (board[newRow][newCol].getType() == Tile.getBlock()) {
				if (pushBlock(direction, newRow, newCol)) {
					moveBlock(player, player.getRow(), player.getCol(), newRow, newCol);
				}
			}
		}
		
		if (darkness) {
			updateDarkness();
		}
	}
	
	public void moveBlock(Movable block, int row, int col, int newRow, int newCol){
		board[row][col] = block.getCovering();
		block.setPosition(newRow, newCol);
		block.setCovering(board[newRow][newCol]);
		board[newRow][newCol] = block;
	}
	
	public boolean pushBlock(int direction, int row, int col){
		
		int newRow = row;
		int newCol = col;
		
		if(direction == UP){
			newRow--;
		} else if(direction == DOWN){
			newRow++;
		} else if(direction == LEFT){
			newCol--;
		} else if(direction == RIGHT){
			newCol++;
		}
		
		if (withinBounds(newRow, newCol) && board[newRow][newCol].getWalkable()){
			moveBlock((Movable)(board[row][col]), row, col, newRow, newCol);
			return true;
		}
		return false;
	}
	
	public boolean withinBounds(int r, int c) {
		return (r >= 0 && r < ROWS) && (c >= 0 && c < COLS);
	}
	
	private void updateDarkness(){
		if (darkness) {
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++){
					board[i][j].setDarkness(Tile.getDark());
				}
			}
			
			player.setDarkness(Tile.getLight());
			int r = player.getRow();
			int c = player.getCol();
			
			for (int i = 0; i < INNER_PERIMETER[0].length; i++) {
				if (withinBounds(r + INNER_PERIMETER[0][i], c + INNER_PERIMETER[1][i])) {
					board[r + INNER_PERIMETER[0][i]][c + INNER_PERIMETER[1][i]].setDarkness(Tile.getLight());
				}
			}
			
			for (int i = 0; i < OUTER_PERIMETER[0].length; i++) {
				if (withinBounds(r + OUTER_PERIMETER[0][i], c + OUTER_PERIMETER[1][i])) {
					board[r + OUTER_PERIMETER[0][i]][c + OUTER_PERIMETER[1][i]].setDarkness(Tile.getSemiDark());
				}
			}
			
		} else {
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++){
					board[i][j].setDarkness(Tile.getLight());
				}
			}
		}
	}
	
	public Tile getTile(int r, int c){
		return board[r][c];
	}
	
	public Tile[][] getBoard() {
		return board;
	}
	
	public void drawBoard(){
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLS; j++){
				if (darkness) {
					if (board[i][j].getDarkness() == Tile.getSemiDark() || board[i][j].getDarkness() == Tile.getDark()){
						System.out.print(board[i][j].getDarkness());
					} else {
						System.out.print(board[i][j].getType());
					}
				} else {
					System.out.print(board[i][j].getType());
				}
			}
			System.out.println();
		}
	}
	
	public double getLevel() {
		return myLevel;
	}

	//getters for controller when moving character
	public int getUp() {
		return UP;
	}

	public int getDown() {
		return DOWN;
	}

	public int getLeft() {
		return LEFT;
	}

	public int getRight() {
		return RIGHT;
	}
	public boolean isPlayMode() {
		return playMode;
	}

	public void setPlayMode(boolean playMode) {
		this.playMode = playMode;
	}
	
	public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}
	
	public int getRow(){
		return ROWS;
	}
	
	public int getCol(){
		return COLS;
	}
}
